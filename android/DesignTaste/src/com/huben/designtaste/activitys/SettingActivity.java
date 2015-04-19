package com.huben.designtaste.activitys;

import java.io.File;

import com.huben.designtaste.R;
import com.huben.designtaste.constants.ConfigKey;
import com.huben.designtaste.utils.SPUtils;
import com.huben.designtaste.utils.ToastUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.HttpCache;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		getActionBar().setTitle("Setting");
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setLogo(R.drawable.back);
	}

	@OnClick(R.id.setting_shots)
	private void shotsClick(View v) {
		new AlertDialog.Builder(this)
				.setTitle("Shots")
				.setSingleChoiceItems(
						new String[] {"All", "Animated", "Attachments", "Debuts",
								"Playoffs", "Rebounds", "Teams" }, 0,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									SPUtils.putString(ConfigKey.SHOTS_LIST,"");
									break;
								case 1:
									SPUtils.putString(ConfigKey.SHOTS_LIST, ConfigKey.SHOTS_LIST_ANIMATED);
									break;
								case 2:
									SPUtils.putString(ConfigKey.SHOTS_LIST, ConfigKey.SHOTS_LIST_ATTACHMENTS);
									break;
								case 3:
									SPUtils.putString(ConfigKey.SHOTS_LIST, ConfigKey.SHOTS_LIST_DEBUTS);
									break;
								case 4:
									SPUtils.putString(ConfigKey.SHOTS_LIST, ConfigKey.SHOTS_LIST_PLAYOFFS);
									break;
								case 5:
									SPUtils.putString(ConfigKey.SHOTS_LIST, ConfigKey.SHOTS_LIST_REBOUNDS);
									break;
								case 6:
									SPUtils.putString(ConfigKey.SHOTS_LIST, ConfigKey.SHOTS_LIST_TEAMS);
									break;
								default:
									break;
								}
							}
						}).setNegativeButton("YES", new Dialog.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								new HttpCache().clear();
								
							}
						}).show();
	}

	@OnClick(R.id.setting_popular)
	private void popularClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("Popular")
		.setSingleChoiceItems(
				new String[] {"Popular", "Recent", "Most Viewed", "Most Commented"
						}, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						switch (which) {
						case 0:
							SPUtils.putString(ConfigKey.SHOTS_SORT, "");
							break;
						case 1:
							SPUtils.putString(ConfigKey.SHOTS_SORT, ConfigKey.SHOTS_SORT_RECENT);
							break;
						case 2:
							SPUtils.putString(ConfigKey.SHOTS_SORT, ConfigKey.SHOTS_SORT_VIEWS);
							break;
						case 3:
							SPUtils.putString(ConfigKey.SHOTS_SORT, ConfigKey.SHOTS_SORT_COMMENTS);
							break;
						default:
							break;
						}
					}
				}).setNegativeButton("YES", new Dialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						new HttpCache().clear();
						
					}
				}).show();
	}

	@OnClick(R.id.setting_now)
	private void nowClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("Now")
		.setSingleChoiceItems(
				new String[] { "All Time", "This Past Week", "This Past Month", "This Past Year"
						}, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						switch (which) {
						case 0:
							SPUtils.putString(ConfigKey.SHOTS_TIME_FRAME, ConfigKey.SHOTS_TIME_FRAME_EVER);
							break;
						case 1:
							SPUtils.putString(ConfigKey.SHOTS_TIME_FRAME, ConfigKey.SHOTS_TIME_FRAME_WEEK);
							break;
						case 2:
							SPUtils.putString(ConfigKey.SHOTS_TIME_FRAME, ConfigKey.SHOTS_TIME_FRAME_MONTH);
							break;
						case 3:
							SPUtils.putString(ConfigKey.SHOTS_TIME_FRAME, ConfigKey.SHOTS_TIME_FRAME_YEAR);
							break;
						default:
							break;
						}
					}
				}).setNegativeButton("YES", new Dialog.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						new HttpCache().clear();
						
					}
				}).show();
	}

	@OnClick(R.id.setting_clear)
	private void clearClick(View v) {
		BitmapUtils bitmapUtils = new BitmapUtils(SettingActivity.this);
		bitmapUtils.clearDiskCache();
		bitmapUtils.clearMemoryCache();
		File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",SPUtils.FILE_NAME+".xml");
		if(file.exists()){
			file.delete(); 
		}
		ToastUtils.show("Clear Cache Success");
	}

	@OnClick(R.id.setting_about_me)
	private void aboutMeClick(View v) {
		new AlertDialog.Builder(this)
				.setTitle("About me")
				.setItems(
						new String[] { "Gmail : itboygogogo@gmail.com",
								"qq:328472638" }, null)
				.setNegativeButton("Yes", new Dialog.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				}).show();
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
