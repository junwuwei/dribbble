package com.huben.designtaste.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static String formatYMD(String string) {
		Date date = new Date(string);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//输出数据为2010-09-09
		return format.format(date);
	}
}
