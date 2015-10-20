package com.example.tianshijie1.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

public class WrapContentHeightViewPager extends ViewPager {

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the context
	 */
	public WrapContentHeightViewPager(Context context) {
		super(context);
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attribute set
	 */
	public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int height = 0;
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			child.measure(widthMeasureSpec,
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int h = child.getMeasuredHeight();

			height = h;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
