package com.example.tianshijie1.scv;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListViewNoScroll extends ListView {
	public ListViewNoScroll(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ListViewNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}