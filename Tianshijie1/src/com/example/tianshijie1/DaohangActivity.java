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
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.shangla.JazzyViewPager.*;
import com.example.tianshijie1.shangla.JazzyViewPager;
import com.umeng.analytics.MobclickAgent;


/**
 * XQ4Start
 *需求编号:XQ4
 *需求描述：优化导航页特效，使用开源库JazzViewPager
 *修复人：李超
 *修复日期：2015-12-10
 */
public class DaohangActivity extends Activity {
	private JazzyViewPager mJazzy;
	private SharedPreferences sharedPreferences;
	//导航页组
	final ArrayList<View> views = new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_daohang);
		setupJazziness(TransitionEffect.Tablet);//设置导航样式
		mJazzy = (JazzyViewPager) findViewById(R.id.whatsnew_viewpager);

		// 将要分页显示的View装入数组中
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.show_1, null);
		View view2 = mLi.inflate(R.layout.show_2, null);
		View view3 = mLi.inflate(R.layout.show_3, null);

		// 每个页面的view数据

		views.add(view1);
		views.add(view2);
		views.add(view3);

		mJazzy.setAdapter(new jazzadapter());
	}

	//装载导航样式
	private void setupJazziness(TransitionEffect effect) {
		mJazzy = (JazzyViewPager) findViewById(R.id.whatsnew_viewpager);
		mJazzy.setTransitionEffect(effect);
		mJazzy.setFadeEnabled(true);
		mJazzy.setAdapter(new jazzadapter());
		mJazzy.setPageMargin(3);
	}

	// 跳转页面
	public void startbutton() {
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
	// 填充JazzyViewPager的数据适配器
	private class jazzadapter extends PagerAdapter{


		@Override
		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position,
								Object object)
		{
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{

			View currView = null;
//			LayoutInflater factory = LayoutInflater.from(this);
			currView = views.get(position);
			//获取控件内容并设定click侦听
			final Button btnStart = (Button) currView.findViewById(R.id.button1);
			if (btnStart != null) btnStart.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startbutton();
				}
			});
			container.addView(currView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			mJazzy.setObjectForPosition(currView, position);
			return currView;
		}

		@Override
		public int getCount()
		{
			return views.size();
		}
	}
}
//XQ4End