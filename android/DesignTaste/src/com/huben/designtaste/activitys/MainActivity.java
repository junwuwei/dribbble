package com.huben.designtaste.activitys;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huben.designtaste.R;
import com.huben.designtaste.models.DribbbleModel;
import com.huben.designtaste.utils.ApiConnecter;
import com.huben.designtaste.utils.JsonUitls;
import com.huben.designtaste.utils.ToastUtils;
import com.huben.designtaste.widget.XListView;
import com.huben.designtaste.widget.XListView.IXListViewListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.analytics.MobclickAgent;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@ContentView(R.layout.activity_main)
public class MainActivity extends Activity implements IXListViewListener,
		OnItemClickListener {

	@ViewInject(R.id.main_progress)
	private ProgressBar progressBar;

	@ViewInject(R.id.main_listview)
	private XListView listView;
	private BaseAdapter adapter;
	private List<DribbbleModel> list = new ArrayList<DribbbleModel>();
	public BitmapUtils bitmapUtil;
	private int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		bitmapUtil = new BitmapUtils(MainActivity.this);
		ApiConnecter.shareInstance().getShots(0, 20, callback);
		initListView();
	}

	private void initListView() {
		listView.setPullRefreshEnable(true);
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View view, ViewGroup arg2) {
				ViewHolder holder = null;
				if (view == null) {
					holder = new ViewHolder();
					view = getLayoutInflater().inflate(
							R.layout.item_main_listview, null);
					ViewUtils.inject(holder, view);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}
				final DribbbleModel model = (DribbbleModel) getItem(position);
				String des = "null".equals(Html.fromHtml(model.description)
						.toString()) ? "" : Html.fromHtml(model.description)
						.toString();
				holder.title.setText("<<" + model.title + ">>    " + des);
				holder.text_comment_count.setText(model.comments_count + "");
				holder.text_like_count.setText(model.likes_count + "");
				holder.text_view_count.setText(model.views_count + "");
				holder.time.setText(model.updated_at);
				holder.userName.setText(model.user.username);
				bitmapUtil.display(holder.image, model.images.teaser);
				bitmapUtil.display(holder.avatar, model.user.avatar_url);
				holder.image.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						String imgUrl = TextUtils.isEmpty(model.images.hidpi) ? model.images.hidpi
								: model.images.normal;
						startActivity(new Intent(MainActivity.this,
								HidpiViewActivity.class).putExtra(
								HidpiViewActivity.IMG_HIDPI_URL, imgUrl)
								.putExtra(
										HidpiViewActivity.HIDPI_DRIBBBLE_MODEL,
										model));
					}
				});
				return view;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				return list.get(position);
			}

			@Override
			public int getCount() {
				return list.size();
			}
		};
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		if (item.getItemId() == R.id.action_settings) {
			startActivity(new Intent(MainActivity.this, SettingActivity.class));
		}
		return true;
	};

	RequestCallBack<String> callback = new RequestCallBack<String>() {

		@Override
		public void onStart() {
			LogUtils.d("网络请求开始");
			progressBar.setVisibility(View.VISIBLE);
		};

		@Override
		public void onSuccess(ResponseInfo<String> response) {

			LogUtils.d(response.result);
			progressBar.setVisibility(View.GONE);
			try {
				JSONArray jsonArray = new JSONArray(response.result);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArray.get(i);
					list.add(JsonUitls.jsonToDribbbleModel(jsonObj));
				}
				LogUtils.d("条数" + list.size());
				adapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			listView.setRefreshTime(DateFormat.format("yyyy-MM-dd hh:mm:ss",
					System.currentTimeMillis()).toString());
			listView.stopRefresh();
			listView.stopLoadMore();
		}

		@Override
		public void onFailure(HttpException e, String msg) {
			e.printStackTrace();
			ToastUtils.show("数据加载失败,稍后再试");
			progressBar.setVisibility(View.GONE);
			listView.stopRefresh();
			listView.stopLoadMore();
		}
	};

	public class ViewHolder {
		@ViewInject(R.id.image)
		public ImageView image;
		@ViewInject(R.id.avatar)
		public ImageView avatar;
		@ViewInject(R.id.text_view_count)
		public TextView text_view_count;
		@ViewInject(R.id.text_comment_count)
		public TextView text_comment_count;
		@ViewInject(R.id.text_like_count)
		public TextView text_like_count;
		@ViewInject(R.id.title)
		public TextView title;
		@ViewInject(R.id.userName)
		public TextView userName;
		@ViewInject(R.id.time)
		public TextView time;
	}

	@Override
	public void onRefresh() {
		LogUtils.d("下拉刷新。。。");
		list.clear();
		ApiConnecter.shareInstance().getShots(0, 20, callback);
	}

	@Override
	public void onLoadMore() {
		LogUtils.d("加载更多。。。");
		// ToastUtils.show("暂不支持上拉加载更多数据");
		currentPage++;
		ApiConnecter.shareInstance().getShots(currentPage++, 20, callback);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		startActivity(new Intent(MainActivity.this, DetailActivity.class)
				.putExtra(DetailActivity.DRIBBBLE_MODEL,
						(DribbbleModel) adapter.getItem(position - 1)));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(MainActivity.this).setTitle("Quit")
					.setMessage("Are you sure quit ?")
					.setNegativeButton("NO", null)
					.setPositiveButton("YES", new Dialog.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							MainActivity.this.finish();
							MobclickAgent.onKillProcess(MainActivity.this);
							android.os.Process.killProcess(android.os.Process
									.myPid());
						}
					}).create().show();

			return true;
		} else
			// 如果不是back键正常响应
			return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
