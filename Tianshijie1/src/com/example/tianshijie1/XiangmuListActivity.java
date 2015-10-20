package com.example.tianshijie1;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.fragement.fragment1;
import com.example.tianshijie1.fragement.fragment2;
import com.example.tianshijie1.fragement.fragment3;
import com.umeng.analytics.MobclickAgent;

public class XiangmuListActivity extends FragmentActivity {
	private ImageView iv_backxiangmulist, iv_hang;
	private TextView tv_xiangmuming, tv_meikaishi, tv_kaizhene, tv_jieshule;
	private ViewPager m_vp;
	private fragment1 mfragment1;
	private fragment2 mfragment2;
	private fragment3 mfragment3;
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
		setContentView(R.layout.activity_xiangmu_list);
		title = getIntent().getStringExtra("title");
		init();
		InitViewPager();
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_xiangmuming = (TextView) findViewById(R.id.tv_xiangmuming);
		tv_meikaishi = (TextView) findViewById(R.id.tv_meikaishi);
		tv_kaizhene = (TextView) findViewById(R.id.tv_kaizhene);
		tv_jieshule = (TextView) findViewById(R.id.tv_jieshule);
		m_vp = (ViewPager) findViewById(R.id.viewpager);
		iv_hang = (ImageView) findViewById(R.id.iv_hang);
		iv_backxiangmulist = (ImageView) findViewById(R.id.iv_backxiangmulist);
		tv_xiangmuming.setText(title);
		iv_backxiangmulist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv_hang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog dlg = new AlertDialog.Builder(
						XiangmuListActivity.this).create();

				dlg.show();
				Window window = dlg.getWindow();
				// *** 主要就是在这里实现这种效果的.
				// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
				window.setContentView(R.layout.popw);
				final Button btn_btn1 = (Button) window
						.findViewById(R.id.btn_btn1);
				final Button btn_btn2 = (Button) window
						.findViewById(R.id.btn_btn2);
				final Button btn_btn3 = (Button) window
						.findViewById(R.id.btn_btn3);
				final Button btn_btn4 = (Button) window
						.findViewById(R.id.btn_btn4);
				final Button btn_btn5 = (Button) window
						.findViewById(R.id.btn_btn5);
				final Button btn_btn6 = (Button) window
						.findViewById(R.id.btn_btn6);
				final Button btn_btn7 = (Button) window
						.findViewById(R.id.btn_btn7);
				final Button btn_btn8 = (Button) window
						.findViewById(R.id.btn_btn8);
				final Button btn_btn9 = (Button) window
						.findViewById(R.id.btn_btn9);
				final Button btn_btn10 = (Button) window
						.findViewById(R.id.btn_btn10);
				final Button btn_btn11 = (Button) window
						.findViewById(R.id.btn_btn11);
				final LinearLayout ll_zuo = (LinearLayout) window
						.findViewById(R.id.ll_zuo);
				// 为确认按钮添加事件,执行退出应用操作
				if (tv_xiangmuming.getText().toString().equals("生活消费")) {
					btn_btn1.setBackgroundResource(R.drawable.shape4);
					btn_btn1.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("连锁")) {
					btn_btn2.setBackgroundResource(R.drawable.shape4);
					btn_btn2.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("社区")) {
					btn_btn3.setBackgroundResource(R.drawable.shape4);
					btn_btn3.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("城建")) {
					btn_btn4.setBackgroundResource(R.drawable.shape4);
					btn_btn4.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("房产")) {
					btn_btn5.setBackgroundResource(R.drawable.shape4);
					btn_btn5.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("科技金融")) {
					btn_btn6.setBackgroundResource(R.drawable.shape4);
					btn_btn6.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("科技")) {
					btn_btn7.setBackgroundResource(R.drawable.shape4);
					btn_btn7.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("移动互联网")) {
					btn_btn8.setBackgroundResource(R.drawable.shape4);
					btn_btn8.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("文化创意")) {
					btn_btn9.setBackgroundResource(R.drawable.shape4);
					btn_btn9.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("互联网金融")) {
					btn_btn10.setBackgroundResource(R.drawable.shape4);
					btn_btn10.setTextColor(Color.parseColor("#ffffff"));
				} else if (tv_xiangmuming.getText().toString().equals("新三板")) {
					btn_btn11.setBackgroundResource(R.drawable.shape4);
					btn_btn11.setTextColor(Color.parseColor("#ffffff"));
				}
				btn_btn1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("生活消费");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "27,24,23,25");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn2.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("连锁");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "27");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn3.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("社区");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "24");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn4.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("城建");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "25");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn5.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("房产");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "23");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("科技金融");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "26,4,2,22");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn7.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("科技");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "26");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn8.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("移动互联网");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "4");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn9.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("文化创意");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "2");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn10.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("互联网金融");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "22");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				btn_btn11.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv_xiangmuming.setText("新三板");
						Intent intent = new Intent("hangye");
						intent.putExtra("data", "1");
						LocalBroadcastManager.getInstance(
								XiangmuListActivity.this).sendBroadcast(intent);
						dlg.cancel();
					}
				});
				ll_zuo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlg.cancel();
					}
				});

			}
		});
		tv_meikaishi.setOnClickListener(new MyOnClickListener(0));
		tv_kaizhene.setOnClickListener(new MyOnClickListener(1));
		tv_jieshule.setOnClickListener(new MyOnClickListener(2));
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
		mfragment1 = new fragment1();
		mfragment2 = new fragment2();
		mfragment3 = new fragment3();

		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(mfragment1);
		fragmentList.add(mfragment2);
		fragmentList.add(mfragment3);

		m_vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		m_vp.setCurrentItem(1);
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
				tv_meikaishi.setTextColor(Color.parseColor("#cf0606"));
				tv_jieshule.setTextColor(Color.parseColor("#666666"));
				tv_kaizhene.setTextColor(Color.parseColor("#666666"));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);

				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);

				}
				break;
			case 1:
				tv_kaizhene.setTextColor(Color.parseColor("#cf0606"));
				tv_jieshule.setTextColor(Color.parseColor("#666666"));
				tv_meikaishi.setTextColor(Color.parseColor("#666666"));

				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);

				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);

				}
				break;
			case 2:
				tv_jieshule.setTextColor(Color.parseColor("#cf0606"));
				tv_kaizhene.setTextColor(Color.parseColor("#666666"));
				tv_meikaishi.setTextColor(Color.parseColor("#666666"));
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
