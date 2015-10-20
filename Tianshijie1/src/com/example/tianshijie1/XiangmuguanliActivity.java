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
import com.example.tianshijie1.fragement.FragmentRengou;
import com.example.tianshijie1.fragement.FragmentYuetan;
import com.example.tianshijie1.fragement.FragmentYuyue;
import com.umeng.analytics.MobclickAgent;

public class XiangmuguanliActivity extends FragmentActivity {
	private ImageView iv_backxiangmuguanli;
	private TextView tv_xiangmuming, tv_touzi, tv_yugou, tv_yuetan;
	private ViewPager m_vp;
	private FragmentYuyue fragmentYuyue;
	private FragmentYuetan fragmentYuetan;
	private FragmentRengou fragmentRengou;
	private int currIndex = 0;// 当前页卡编号
	// 页面列表
	private ArrayList<Fragment> fragmentList;
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	private PopupWindow popupWindow;
	private View view;
	String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏

		setContentView(R.layout.activity_xiangmuguanli);
		init();
		InitViewPager();

	}

	private void init() {
		// TODO Auto-generated method stub

		tv_xiangmuming = (TextView) findViewById(R.id.tv_xiangmuming);
		tv_yugou = (TextView) findViewById(R.id.tv_yugou);
		tv_touzi = (TextView) findViewById(R.id.tv_touzi);
		tv_yuetan = (TextView) findViewById(R.id.tv_yuetan);
		m_vp = (ViewPager) findViewById(R.id.viewpager);

		iv_backxiangmuguanli = (ImageView) findViewById(R.id.iv_backxiangmuguanli);
		iv_backxiangmuguanli.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		tv_yugou.setOnClickListener(new MyOnClickListener(0));
		tv_touzi.setOnClickListener(new MyOnClickListener(1));
		tv_yuetan.setOnClickListener(new MyOnClickListener(2));
	}

	// /**
	// * 显示行业的popwindow
	// *
	// * @param parent
	// */
	// private void showWindow(View parent) {
	//
	// if (popupWindow == null) {
	// LayoutInflater layoutInflater = (LayoutInflater)
	// getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//
	// view = layoutInflater.inflate(R.layout.popw, null);
	//
	// // 创建一个PopuWidow对象
	// popupWindow = new PopupWindow(view, 300, 350);
	// }
	//
	// // 使其聚集
	// popupWindow.setFocusable(true);
	// // 设置允许在外点击消失
	// popupWindow.setOutsideTouchable(true);
	//
	// // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
	// popupWindow.setBackgroundDrawable(new BitmapDrawable());
	// WindowManager windowManager = (WindowManager)
	// getSystemService(Context.WINDOW_SERVICE);
	// // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
	// int xPos = windowManager.getDefaultDisplay().getWidth() / 2
	// - popupWindow.getWidth() / 2;
	// Log.i("coder", "xPos:" + xPos);
	//
	// popupWindow.showAsDropDown(parent, xPos, 0);
	//
	// }

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		fragmentYuyue = new FragmentYuyue();
		fragmentYuetan = new FragmentYuetan();
		fragmentRengou = new FragmentRengou();

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(fragmentYuyue);
		fragmentList.add(fragmentRengou);
		fragmentList.add(fragmentYuetan);

		m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		m_vp.setCurrentItem(0);
		String yema = getIntent().getStringExtra("yema");
		if (yema != null) {
			m_vp.setCurrentItem(1);
		}
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
				tv_yugou.setTextColor(Color.parseColor("#cf0606"));
				tv_yuetan.setTextColor(Color.parseColor("#666666"));
				tv_touzi.setTextColor(Color.parseColor("#666666"));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);

				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);

				}
				break;
			case 1:
				tv_touzi.setTextColor(Color.parseColor("#cf0606"));
				tv_yuetan.setTextColor(Color.parseColor("#666666"));
				tv_yugou.setTextColor(Color.parseColor("#666666"));

				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);

				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);

				}
				break;
			case 2:
				tv_yuetan.setTextColor(Color.parseColor("#cf0606"));
				tv_yugou.setTextColor(Color.parseColor("#666666"));
				tv_touzi.setTextColor(Color.parseColor("#666666"));
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
		getMenuInflater().inflate(R.menu.xiangmu_list, menu);
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
