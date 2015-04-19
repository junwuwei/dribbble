package com.huben.designtaste.activitys;

import java.io.File;

import com.huben.designtaste.R;
import com.huben.designtaste.constants.Constants;
import com.huben.designtaste.models.DribbbleModel;
import com.huben.designtaste.utils.AndroidShare;
import com.huben.designtaste.utils.ToastUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;

@ContentView(R.layout.activity_share)
public class ShareActivity extends Activity {
	
	public static final String SHARE_DRIBBBLE_MODEL = "share_dribbble_model";
	private String title = "dribbble";
	private DribbbleModel model;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getActionBar().setTitle("分享");
		ViewUtils.inject(this);
		model = (DribbbleModel) getIntent()
				.getSerializableExtra(SHARE_DRIBBBLE_MODEL);
	}
	
	@OnClick(R.id.share_wechat)
	private void wechatClick(View v) {
		AndroidShare.share(ShareActivity.this, AndroidShare.SHARE_BY_WEIXIN, Html.fromHtml(model.title).toString(), Html.fromHtml(model.description+model.html_url).toString(), model.images.hidpi, 0);
		finish();
	}
	
	@OnClick(R.id.share_friends_groups)
	private void friendsGroupClick(View v) {
		new HttpUtils().download(model.images.hidpi,Constants.SAVE_FILE_PATH +"/"+ System.currentTimeMillis()+".png", new RequestCallBack<File>() {

			@Override
			public void onSuccess(ResponseInfo<File> response) {
				LogUtils.d("下载完成");
				AndroidShare.share(ShareActivity.this, AndroidShare.SHARE_BY_WEIXIN_MOMENTS, Html.fromHtml(model.title).toString(), Html.fromHtml(model.description+model.html_url).toString(),response.result.getAbsolutePath() , 0);
				finish();
			}

			@Override
			public void onFailure(HttpException e, String arg1) {
				e.printStackTrace();
			}
		});
		
	}
	
	@OnClick(R.id.share_qq_zone)
	private void qqZoneClick(View v) {
		AndroidShare.share(ShareActivity.this, AndroidShare.SHARE_BY_QQ_QZONE, Html.fromHtml(model.title).toString(), Html.fromHtml(model.description+model.html_url).toString(), model.images.hidpi, 0);
		finish();
	}
	
	@OnClick(R.id.share_sina_weibo)
	private void sinaWeiboClick(View v) {
		AndroidShare.share(ShareActivity.this, AndroidShare.SHARE_BY_SINA_WEIBO, Html.fromHtml(model.title).toString(), Html.fromHtml(model.description+model.html_url).toString(), model.images.hidpi, 0);
		finish();
	}
	
	@OnClick(R.id.share_tencent_weibo)
	private void tencentWeiboClick(View v) {
		AndroidShare.share(ShareActivity.this, AndroidShare.SHARE_BY_TENCENT_WEIBO, Html.fromHtml(model.title).toString(), Html.fromHtml(model.description+model.html_url).toString(), model.images.hidpi, 0);
		finish();
	}
	
	@OnClick(R.id.share_msg)
	private void msgClick(View v) {
		AndroidShare.share(ShareActivity.this, AndroidShare.SHARE_BY_SMS, Html.fromHtml(model.title).toString(), Html.fromHtml(model.description+model.html_url).toString(), model.images.hidpi, 0);
		finish();
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
