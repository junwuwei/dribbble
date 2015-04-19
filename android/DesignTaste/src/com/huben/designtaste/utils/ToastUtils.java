package com.huben.designtaste.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	private static Context mContext;
	private ToastUtils() {
        throw new AssertionError();
    }

	public static void init(Context context) {
		mContext = context;
	}
	
    public static void show(int resId) {
        show(mContext.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(mContext.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text, int duration) {
        Toast.makeText(mContext, text, duration).show();
    }

    public static void show(int resId, Object... args) {
        show(String.format(mContext.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(mContext.getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }
}
