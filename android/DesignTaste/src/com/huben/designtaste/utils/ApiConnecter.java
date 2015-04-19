package com.huben.designtaste.utils;

import com.huben.designtaste.constants.ConfigKey;
import com.huben.designtaste.constants.Constants;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class ApiConnecter {
	
	private static ApiConnecter mInstance;
	private HttpUtils mHttpUtils;
	
	private ApiConnecter() {
		if(mHttpUtils == null) {
			mHttpUtils = new HttpUtils();
		}
	}
	
	public static ApiConnecter shareInstance() {
		if(mInstance == null) {
			synchronized (ApiConnecter.class) {
				if(mInstance == null) {
					mInstance = new ApiConnecter();
				}
			}
		}
		return mInstance;
	}
	
	@SuppressWarnings("unchecked")
	public void getShots(int page, int per_page, RequestCallBack<String> callback) {
		RequestParams params = new RequestParams();
        params.addBodyParameter("access_token", Constants.ACCESS_TOKEN);
        params.addBodyParameter(ConfigKey.SHOTS_LIST, SPUtils.getString(ConfigKey.SHOTS_LIST));
        params.addBodyParameter(ConfigKey.SHOTS_TIME_FRAME, SPUtils.getString(ConfigKey.SHOTS_TIME_FRAME));
        params.addBodyParameter(ConfigKey.SHOTS_SORT, SPUtils.getString(ConfigKey.SHOTS_SORT));
        mHttpUtils.send(HttpMethod.GET, Constants.SHOTS_URL+"?page="+page+"&per_page="+per_page, params, callback);
	}
	
	public void getComments(String url, RequestCallBack<String> callback) {
		RequestParams params = new RequestParams();
        params.addBodyParameter("access_token", Constants.ACCESS_TOKEN);
        mHttpUtils.send(HttpMethod.GET, url, params, callback);
	}

}
