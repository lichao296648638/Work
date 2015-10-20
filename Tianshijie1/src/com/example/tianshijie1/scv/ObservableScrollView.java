/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.tianshijie1.scv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * A custom ScrollView that can accept a scroll listener.
 */
public class ObservableScrollView extends ScrollView {
	private Callbacks mCallbacks;
	private GestureDetector mGestureDetector;
	View.OnTouchListener mGestureListener;

	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context, new YScrollDetector());
		setFadingEdgeLength(0);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mCallbacks != null) {
			mCallbacks.onScrollChanged(t);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mCallbacks != null) {
			switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				mCallbacks.onDownMotionEvent();
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mCallbacks.onUpOrCancelMotionEvent();
				break;
			}
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public int computeVerticalScrollRange() {
		return super.computeVerticalScrollRange();
	}

	public void setCallbacks(Callbacks listener) {
		mCallbacks = listener;
	}

	public static interface Callbacks {
		public void onScrollChanged(int scrollY);

		public void onDownMotionEvent();

		public void onUpOrCancelMotionEvent();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		return super.onInterceptTouchEvent(ev)
				&& mGestureDetector.onTouchEvent(ev);
	}

	class YScrollDetector extends SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			/**
			 * 如果我们滚动更接近水平方向,返回false,让子视图来处理它
			 */
			if (distanceY != 0 && distanceX != 0) {

			}
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}

	}
}
