package com.huben.designtaste.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class NonScrollListView extends ListView {

	public NonScrollListView(Context context) {
		super(context);
	}
	
	public NonScrollListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NonScrollListView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}
}
