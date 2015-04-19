package com.huben.designtaste;

import com.huben.designtaste.utils.SPUtils;
import com.huben.designtaste.utils.ToastUtils;
import com.lidroid.xutils.http.HttpCache;
import com.lidroid.xutils.util.LogUtils;

import android.app.Application;

public class CApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		SPUtils.init(getApplicationContext());
		LogUtils.allowD = true;
		ToastUtils.init(getApplicationContext());
	}
	
}
