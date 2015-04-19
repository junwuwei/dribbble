package com.huben.designtaste.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils
{
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "share_dribbble_data";
	
	private static SharedPreferences sp ;
	private static SharedPreferences.Editor editor;
	
	public static void init(Context context) {
		sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public static void putString(String key, String value) {
		editor.putString(key, value).commit();
	}
	
	public static void putInt(String key, int value) {
		editor.putInt(key, value).commit();
	}
	
	public static void putBoolean(String key, Boolean value) {
		editor.putBoolean(key, value).commit();
	}
	
	public static String getString(String key) {
		return sp.getString(key, "");
	}

	public static int getInt(String key) {
		return sp.getInt(key, 0);
	}
	
	public static boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}

}
