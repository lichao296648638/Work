package com.example.tianshijie1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import com.example.tianshijie1.application.MyApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class ShanpingActivity extends Activity {
	private long m_dwSplashTime = 3000;
	private boolean m_bPaused = false;
	private boolean m_bSplashActive = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_shanping);
		MyApplication mApp = (MyApplication) getApplication();
		mApp.setExit(false);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);
		MobclickAgent.updateOnlineConfig(this);
		// 从服务器获取更新信息的回调函数
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // 有更新
					UmengUpdateAgent.showUpdateDialog(ShanpingActivity.this,
							updateInfo);
					break;
				case 1: // 无更新

					break;
				case 2: // 如果设置为wifi下更新且wifi无法打开时调用
					break;
				case 3: // 连接超时

					break;
				}
			}
		});

		Thread splashTimer = new Thread() {
			public void run() {
				try {
					// wait loop
					long ms = 0;
					while (m_bSplashActive && ms < m_dwSplashTime) {
						sleep(50);

						if (!m_bPaused)
							ms += 100;
					}
					// Intent intent = new Intent();
					// intent.setClass(ShanpingActivity.this,
					// MainActivity.class);
					// startActivity(intent);
					SharedPreferences preferences = getSharedPreferences(
							"count", Context.MODE_PRIVATE);
					int count = preferences.getInt("count", 0);

					// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
					if (count == 0) {
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(),
								DaohangActivity.class);
						startActivity(intent);
						finish();
					} else {
						SharedPreferences sharedPreferences = getSharedPreferences(
								LoginActivity.PHONE, Context.MODE_PRIVATE);
						LoginActivity.UID = sharedPreferences.getString("id",
								"-1");
						ConnectivityManager mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
						TelephonyManager mTelephony = (TelephonyManager) ShanpingActivity.this
								.getSystemService(TELEPHONY_SERVICE);
						// 检查网络连接，如果无网络可用，就不需要进行连网操作等
						NetworkInfo info = mConnectivity.getActiveNetworkInfo();
						if (info == null
								|| !mConnectivity.getBackgroundDataSetting()) {
							Intent intent = new Intent();
							intent.setClass(getApplicationContext(),
									WuwangActivity.class);
							startActivity(intent);
							finish();
						} else {
							if (!LoginActivity.UID.equals("-1")) {
								Intent intent = new Intent();
								intent.setClass(getApplicationContext(),
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								Intent intent = new Intent();
								intent.setClass(getApplicationContext(),
										TfirstActivity.class);
								startActivity(intent);
								finish();
							}

						}

					}
					Editor editor = preferences.edit();
					// 存入数据
					editor.putInt("count", ++count);
					// 提交修改
					editor.commit();
				} catch (Exception ex) {
					Log.e("Splash", ex.getMessage());
				} finally {
					finish();
				}
			}
		};
		splashTimer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		m_bPaused = true;
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		m_bPaused = false;
		MobclickAgent.onResume(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:
			m_bSplashActive = false;
			break;
		case KeyEvent.KEYCODE_BACK:
			/* 两种退出方法 */
			/* System.exit(0); */
			/* android.os.Process.killProcess(android.os.Process.myPid()); */
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		default:
			break;
		}
		return true;
	}
}
