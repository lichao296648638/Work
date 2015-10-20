package com.example.tianshijie1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.clean.DataCleanManager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MineShezhiActivity extends Activity {
	private RelativeLayout rl_xiugaimima, rl_yijianfankui, rl_guanyuwomen,
			rl_huancun, rl_gengxin;
	private TextView tv_huancun;
	private ImageView iv_backshezhi;
	private Context mContext = this;
	private Button btn_tuichu;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_mine_shezhi);
		sharedPreferences = getSharedPreferences(LoginActivity.PHONE,
				Context.MODE_PRIVATE);
		init();
	}

	private void OnNotice(String string) {
		// TODO Auto-generated method stub
		Toast.makeText(MineShezhiActivity.this, string, Toast.LENGTH_SHORT)
				.show();
	}

	private void init() {
		// TODO Auto-generated method stub
		rl_xiugaimima = (RelativeLayout) findViewById(R.id.rl_xiugaimima);
		rl_yijianfankui = (RelativeLayout) findViewById(R.id.rl_yijianfankui);
		rl_guanyuwomen = (RelativeLayout) findViewById(R.id.rl_guanyuwomen);
		rl_huancun = (RelativeLayout) findViewById(R.id.rl_huancun);
		rl_gengxin = (RelativeLayout) findViewById(R.id.rl_gengxin);
		tv_huancun = (TextView) findViewById(R.id.tv_huancun);
		iv_backshezhi = (ImageView) findViewById(R.id.iv_backshezhi);
		btn_tuichu = (Button) findViewById(R.id.btn_tuichu);
		btn_tuichu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences.Editor editor = null;
				editor = sharedPreferences.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent();
				intent.setClass(MineShezhiActivity.this, TfirstActivity.class);
				startActivity(intent);
				finish();
			}
		});
		iv_backshezhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rl_huancun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DataCleanManager.clearAllCache(MineShezhiActivity.this);
				try {
					tv_huancun.setText(DataCleanManager
							.getTotalCacheSize(MineShezhiActivity.this));
					Toast.makeText(MineShezhiActivity.this, "缓存已清空",
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		try {
			tv_huancun.setText(DataCleanManager.getTotalCacheSize(this));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rl_xiugaimima.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MineShezhiActivity.this,
						MinePasswordChangeActivity.class);
				startActivity(intent);
			}
		});
		rl_yijianfankui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MineShezhiActivity.this, MineYjfkActivity.class);
				startActivity(intent);
			}
		});
		rl_guanyuwomen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MineShezhiActivity.this, AboutusActivity.class);
				startActivity(intent);
			}
		});
		rl_gengxin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/** 开始调用自动更新函数 **/
				UmengUpdateAgent.setDefault();
				UmengUpdateAgent.setUpdateOnlyWifi(false); // 是否在只在wifi下提示更新，默认为
															// true
				UmengUpdateAgent.setUpdateAutoPopup(true); // 是否自动弹出更新对话框
				UmengUpdateAgent.setDownloadListener(null); // 下载新版本过程事件监听，可设为
															// null
				UmengUpdateAgent.setDialogListener(null); // 用户点击更新对话框按钮的回调事件，直接
															// null
				// 从服务器获取更新信息的回调函数
				UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
					@Override
					public void onUpdateReturned(int updateStatus,
							UpdateResponse updateInfo) {
						switch (updateStatus) {
						case 0: // 有更新
							UmengUpdateAgent.showUpdateDialog(mContext,
									updateInfo);
							break;
						case 1: // 无更新
							OnNotice("当前已是最新版.");
							break;
						case 2: // 如果设置为wifi下更新且wifi无法打开时调用
							break;
						case 3: // 连接超时
							OnNotice("连接超时，请稍候重试");
							break;
						}
					}
				});
				UmengUpdateAgent.forceUpdate(mContext); // 先前用户可能忽略更新，“强制”弹出更新对话款
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_shezhi, menu);
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
