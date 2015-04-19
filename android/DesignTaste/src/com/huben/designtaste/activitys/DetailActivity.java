package com.huben.designtaste.activitys;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.huben.designtaste.R;
import com.huben.designtaste.activitys.MainActivity.ViewHolder;
import com.huben.designtaste.models.Comments;
import com.huben.designtaste.models.DribbbleModel;
import com.huben.designtaste.utils.ApiConnecter;
import com.huben.designtaste.utils.JsonUitls;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

@ContentView(R.layout.activity_detail)
public class DetailActivity extends Activity {

	public static final String DRIBBBLE_MODEL = "dribbble_model";
	private DribbbleModel model;
	public BitmapUtils bitmapUtil;

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

	@ViewInject(R.id.comment_listview)
	public ListView commentListview;
	private List<Comments> list = new ArrayList<Comments>();
	private BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initView();
		initCommentsListView();
		initComments();
	}

	@SuppressLint("NewApi")
	private void initView() {
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle("Detail");
		getActionBar().setLogo(R.drawable.back);
		bitmapUtil = new BitmapUtils(this);
		model = (DribbbleModel) getIntent()
				.getSerializableExtra(DRIBBBLE_MODEL);
		LogUtils.d("" + model);
		String des = "null".equals(Html.fromHtml(model.description).toString()) ? ""
				: Html.fromHtml(model.description).toString();
		title.setText("<<" + model.title + ">>    " + des);
		text_comment_count.setText(model.comments_count + "");
		text_like_count.setText(model.likes_count + "");
		text_view_count.setText(model.views_count + "");
		time.setText(model.updated_at);
		userName.setText(model.user.username);
		bitmapUtil.display(image, model.images.normal);
		bitmapUtil.display(avatar, model.user.avatar_url);
	}

	private void initComments() {
		ApiConnecter.shareInstance().getComments(model.comments_url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> response) {
						LogUtils.d(response.result);

						try {
							JSONArray jsonArray = new JSONArray(response.result);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObj = (JSONObject) jsonArray
										.get(i);
								list.add(JsonUitls.jsonToComments(jsonObj));
							}
							adapter.notifyDataSetChanged();
							setListViewHeightBasedOnChildren(commentListview);
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException e, String arg1) {
						e.printStackTrace();
					}
				});
	}

	private void initCommentsListView() {
		adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View view, ViewGroup parent) {
				ViewHolder holder = null;
				if (view == null) {
					holder = new ViewHolder();
					view = getLayoutInflater().inflate(
							R.layout.item_comments_listview, null);
					ViewUtils.inject(holder, view);
					view.setTag(holder);
				} else {
					holder = (ViewHolder) view.getTag();
				}
				Comments comment = (Comments) getItem(position);
				holder.text_like_count.setText(comment.likes_count + "");
				holder.comment.setText(Html.fromHtml(comment.body));
				holder.time.setText(comment.updated_at);
				holder.userName.setText(comment.user.username);
				bitmapUtil.display(holder.avatar, comment.user.avatar_url);
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
		commentListview.setAdapter(adapter);
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 150;
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	@OnClick(R.id.image)
	public void imageClick(View v) {
		String imgUrl = TextUtils.isEmpty(model.images.hidpi) ? model.images.hidpi
				: model.images.normal;
		startActivity(new Intent(DetailActivity.this, HidpiViewActivity.class)
				.putExtra(HidpiViewActivity.IMG_HIDPI_URL, imgUrl)
				.putExtra(HidpiViewActivity.HIDPI_DRIBBBLE_MODEL, model));
	}
	
	@OnClick(R.id.detail_header)
	public void headerClick(View v) {
		startActivity(new Intent(DetailActivity.this, WebViewActivity.class)
		.putExtra(WebViewActivity.HTML_URL, model.html_url));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.detail, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.share:
			/*
			 * Intent shareIntent = new Intent();
			 * shareIntent.setAction(Intent.ACTION_SEND);
			 * shareIntent.putExtra(Intent.EXTRA_TEXT, model.html_url);
			 * shareIntent.setType("text/plain");
			 * shareIntent.putExtra(Intent.EXTRA_STREAM,
			 * R.drawable.ic_launcher); shareIntent.setType("image/jpeg");
			 * startActivity(Intent.createChooser(shareIntent,
			 * "Dribbble shot"));
			 */
			startActivity(new Intent(DetailActivity.this, ShareActivity.class)
					.putExtra(ShareActivity.SHARE_DRIBBBLE_MODEL, model));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public class ViewHolder {
		@ViewInject(R.id.avatar)
		public ImageView avatar;
		@ViewInject(R.id.text_like_count)
		public TextView text_like_count;
		@ViewInject(R.id.userName)
		public TextView userName;
		@ViewInject(R.id.time)
		public TextView time;
		@ViewInject(R.id.comment)
		public TextView comment;
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
