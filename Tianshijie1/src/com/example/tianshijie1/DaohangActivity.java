package com.example.tianshijie1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.example.tianshijie1.application.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class DaohangActivity extends Activity {
	private ViewPager mViewPager;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_daohang);
		mViewPager = (ViewPager) findViewById(R.id.whatsnew_viewpager);
		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.show_1, null);
		View view2 = mLi.inflate(R.layout.show_2, null);
		View view3 = mLi.inflate(R.layout.show_3, null);

		// 每个页面的view数据
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);

		// 填充ViewPager的数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));

				return views.get(position);

			}
		};

		mViewPager.setAdapter(mPagerAdapter);

	}

	// 跳转页面
	public void startbutton(View v) {
		Intent intent = new Intent();
		intent.setClass(DaohangActivity.this, TfirstActivity.class);
		startActivity(intent);
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	protected void onStart() { 
		 
		super.onStart(); 
		 
		MyApplication mApp = (MyApplication)getApplication(); 
		 
		if (mApp.isExit()) { 
		 
		finish(); 
		 
		} 
		 
		} 
}
