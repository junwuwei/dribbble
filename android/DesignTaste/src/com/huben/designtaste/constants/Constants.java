package com.huben.designtaste.constants;

import java.io.File;

import android.os.Environment;

public class Constants {

	public static final String ACCESS_TOKEN = "e505fc9e31dea35dd4e9989e58ced2dab71ab783ae864a6a75f33883d49bc863";
	
	public static final String BASE_URL = "https://api.dribbble.com/v1/";
	public static final String SHOTS_URL = "https://api.dribbble.com/v1/shots/";
	public static final String COMMENTS_URL = "https://api.dribbble.com/v1/shots/:shot/comments";
	
	public static final String SAVE_FILE_PATH = Environment.getExternalStorageDirectory() + File.separator + "Dribbble";
	
}
