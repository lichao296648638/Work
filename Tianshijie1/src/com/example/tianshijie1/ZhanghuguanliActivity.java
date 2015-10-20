package com.example.tianshijie1;

import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.fragement.FragmentFangkuan;
import com.example.tianshijie1.fragement.FragmentZhanghu;
import com.example.tianshijie1.fragement.FragmentZijin;
import com.umeng.analytics.MobclickAgent;

public class ZhanghuguanliActivity extends FragmentActivity {
	private ImageView iv_backzhanghu, iv_hang;
	private TextView tv_xiangmuming, tv_zhanghuziji, tv_zijinjilu,
			tv_fangkuanqueren;
	private ViewPager m_vp;
	private FragmentZhanghu fragmentZhanghu;
	private FragmentZijin fragmentZijin;
	private FragmentFangkuan fragmentFangkuan;
	private int currIndex = 0;// 当前页卡编号
	// 页面列表
	private ArrayList<Fragment> fragmentList;
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	private PopupWindow popupWindow;
	private View view;
	public String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_zhanghuguanli);
		init();
		InitViewPager();
	}

	private void init() {
		// TODO Auto-generated method stub

		tv_xiangmuming = (TextView) findViewById(R.id.tv_xiangmuming);
		tv_zhanghuziji = (TextView) findViewById(R.id.tv_zhanghuziji);
		tv_zijinjilu = (TextView) findViewById(R.id.tv_zijinjilu);
		tv_fangkuanqueren = (TextView) findViewById(R.id.tv_fangkuanqueren);
		m_vp = (ViewPager) findViewById(R.id.viewpager);
		iv_hang = (ImageView) findViewById(R.id.iv_hang);
		iv_backzhanghu = (ImageView) findViewById(R.id.iv_backzhanghu);
		iv_backzhanghu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_zhanghuziji.setOnClickListener(new MyOnClickListener(0));
		tv_zijinjilu.setOnClickListener(new MyOnClickListener(1));
		tv_fangkuanqueren.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		fragmentZhanghu = new FragmentZhanghu();
		fragmentZijin = new FragmentZijin();
		fragmentFangkuan = new FragmentFangkuan();

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(fragmentZhanghu);
		fragmentList.add(fragmentZijin);
		fragmentList.add(fragmentFangkuan);

		m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		m_vp.setCurrentItem(0);
		m_vp.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ViewPager适配器
	 */
	public class MyViewPagerAdapter extends FragmentPagerAdapter {
		public MyViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			return fragmentList.get(arg0);
		}

		@Override
		public int getCount() {
			return fragmentList.size();
		}

	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;

		}

		@Override
		public void onClick(View v) {
			m_vp.setCurrentItem(index);
		}
	};

	/**
	 * 页卡切换监听
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				tv_zhanghuziji.setTextColor(Color.parseColor("#ffffff"));
				tv_fangkuanqueren.setTextColor(Color.parseColor("#bc1b21"));
				tv_zijinjilu.setTextColor(Color.parseColor("#bc1b21"));
				tv_fangkuanqueren.setBackgroundColor(Color
						.parseColor("#ffffff"));
				tv_zijinjilu.setBackgroundColor(Color.parseColor("#ffffff"));
				tv_zhanghuziji.setBackgroundResource(R.drawable.shape4);
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);

				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);

				}
				break;
			case 1:
				tv_zijinjilu.setTextColor(Color.parseColor("#ffffff"));
				tv_fangkuanqueren.setTextColor(Color.parseColor("#bc1b21"));
				tv_zhanghuziji.setTextColor(Color.parseColor("#bc1b21"));
				tv_fangkuanqueren.setBackgroundColor(Color
						.parseColor("#ffffff"));
				tv_zijinjilu.setBackgroundColor(Color.parseColor("#e62f17"));
				tv_zhanghuziji.setBackgroundColor(Color.parseColor("#ffffff"));

				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);

				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);

				}
				break;
			case 2:
				tv_fangkuanqueren.setTextColor(Color.parseColor("#ffffff"));
				tv_zijinjilu.setTextColor(Color.parseColor("#bc1b21"));
				tv_zhanghuziji.setTextColor(Color.parseColor("#bc1b21"));
				tv_fangkuanqueren.setBackgroundResource(R.drawable.shape4);
				tv_zijinjilu.setBackgroundColor(Color.parseColor("#ffffff"));
				tv_zhanghuziji.setBackgroundColor(Color.parseColor("#ffffff"));
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);

				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);

				}
				break;
			}
			currIndex = arg0;

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhanghuguanli, menu);
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

		MyApplication mApp = (MyApplication) getApplication();

		if (mApp.isExit()) {

			finish();

		}

	}
}
