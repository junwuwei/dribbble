package com.huben.designtaste.activitys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import uk.co.senab.photoview.PhotoView;

import com.huben.designtaste.R;
import com.huben.designtaste.constants.Constants;
import com.huben.designtaste.models.DribbbleModel;
import com.huben.designtaste.utils.ToastUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
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
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;

@ContentView(R.layout.activity_hidpi_view)
public class HidpiViewActivity extends Activity {

	public static final String IMG_HIDPI_URL = "hidpi_img_url";
	public static final String HIDPI_DRIBBBLE_MODEL = "hidpi_dribbble_model";
	public DribbbleModel model;

	@ViewInject(R.id.photoview)
	private PhotoView photoView;
	@ViewInject(R.id.hidpi_progress)
	private ProgressBar progressBar;
	private String hidpiImgUrl;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setLogo(R.drawable.back);
		getActionBar().setTitle("详情");
		hidpiImgUrl = getIntent().getStringExtra(IMG_HIDPI_URL);
		model = (DribbbleModel) getIntent().getSerializableExtra(HIDPI_DRIBBBLE_MODEL);
		BitmapUtils bitmapUtil = new BitmapUtils(this);
		LogUtils.d(hidpiImgUrl);
		bitmapUtil.display(photoView, hidpiImgUrl);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@OnClick(R.id.hidpi_save)
	private void saveClick(View v) {
		LogUtils.d("图片保存");
		new HttpUtils().download(hidpiImgUrl,Constants.SAVE_FILE_PATH +"/"+ System.currentTimeMillis()+".png", new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> response) {
				LogUtils.d("下载完成");
				ToastUtils.show("文件保存成功,详情见Dribbble文件夹");
			}

			@Override
			public void onFailure(HttpException e, String arg1) {
				e.printStackTrace();
			}
		});
	}

	@OnClick(R.id.hidpi_share)
	private void shareClick(View v) {
		startActivity(new Intent(HidpiViewActivity.this, ShareActivity.class)
		.putExtra(ShareActivity.SHARE_DRIBBBLE_MODEL, model));
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
