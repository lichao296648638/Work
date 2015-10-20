package com.example.tianshijie1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.tianshijie1.application.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class TfirstActivity extends Activity {
	private Button btn_zhuce, btn_denglu;
	private RelativeLayout rl_xuanzhong2, rl_xuanzhong1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_tfirst);
		MyApplication mApp = (MyApplication) getApplication();
		mApp.setExit(false);
		registerBoradcastReceiver();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		btn_zhuce = (Button) findViewById(R.id.btn_zhuce);
		btn_denglu = (Button) findViewById(R.id.btn_denglu);
		rl_xuanzhong1 = (RelativeLayout) findViewById(R.id.rl_xuanzhong1);
		rl_xuanzhong2 = (RelativeLayout) findViewById(R.id.rl_xuanzhong2);
		btn_zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_zhuce.setBackgroundResource(R.drawable.shape);
				btn_zhuce.setTextColor(Color.parseColor("#ffffff"));
				rl_xuanzhong2.setBackgroundResource(R.drawable.shape1);
				Intent intent = new Intent();
				intent.setClass(TfirstActivity.this, ZhuceActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btn_denglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btn_denglu.setBackgroundResource(R.drawable.shape);
				btn_denglu.setTextColor(Color.parseColor("#ffffff"));
				rl_xuanzhong1.setBackgroundResource(R.drawable.shape1);
				Intent intent = new Intent();
				intent.setClass(TfirstActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		btn_denglu.setBackgroundResource(R.drawable.shape1);
		btn_denglu.setTextColor(Color.parseColor("#bc1a21"));
		rl_xuanzhong1.setBackgroundColor(Color.parseColor("#bc1a21"));
		btn_zhuce.setBackgroundResource(R.drawable.shape1);
		btn_zhuce.setTextColor(Color.parseColor("#bc1a21"));
		rl_xuanzhong2.setBackgroundColor(Color.parseColor("#bc1a21"));
		MobclickAgent.onResume(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MyApplication mApp = (MyApplication) getApplication();

			mApp.setExit(true);

			finish();
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tfirst, menu);
		return true;
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("guandenglu");
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("guandenglu")) {
				finish();
			}
		}

	};
}
