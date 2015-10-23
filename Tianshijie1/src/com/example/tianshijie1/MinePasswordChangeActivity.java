package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class MinePasswordChangeActivity extends Activity {
	private EditText et_oldpassword, et_newpassword, et_newpassword2;
	private ImageView iv_backpasswordchange;
	private Button btn_login;
	Handler handler;
	String info, result, status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_mine_password_change);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(MinePasswordChangeActivity.this, info,
							Toast.LENGTH_LONG).show();
					if (status.equals("0")) {
						Intent intent = new Intent();
						intent.setClass(MinePasswordChangeActivity.this,
								LoginActivity.class);
						startActivity(intent);
						finish();
					}

					break;

				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		et_oldpassword = (EditText) findViewById(R.id.et_oldpassword);
		et_newpassword = (EditText) findViewById(R.id.et_newpassword);
		et_newpassword2 = (EditText) findViewById(R.id.et_newpassword2);
		iv_backpasswordchange = (ImageView) findViewById(R.id.iv_backpasswordchange);
		btn_login = (Button) findViewById(R.id.btn_login);
		iv_backpasswordchange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_oldpassword.getText().toString().length() >=6) {
					if (et_newpassword.getText().toString().length() >= 6) {
						if (et_newpassword2.getText().toString().length() >= 6) {
							if (et_newpassword
									.getText()
									.toString()
									.equals(et_newpassword2.getText()
											.toString())) {
								new Thread() {
									public void run() {
										NameValuePair pair1 = new BasicNameValuePair(
												"uid", LoginActivity.UID);
										NameValuePair pair2 = new BasicNameValuePair(
												"password", et_newpassword
														.getText().toString());
										NameValuePair pair3 = new BasicNameValuePair(
												"oldpassword", et_oldpassword
														.getText().toString());
										NameValuePair pair4 = new BasicNameValuePair(
												"pwdconfirm", et_newpassword2
														.getText().toString());
										final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
										pairList.add(pair1);
										pairList.add(pair2);
										pairList.add(pair3);
										pairList.add(pair4);
										PostUtil postUtil = new PostUtil();
										String url1 = "http://wap.tianshijie.com.cn/appuser/updatepassword";
										result = postUtil.DoPostNew(pairList,
												url1);
										/**
										 * BugStart
										 * Bug编号：BUG4
										 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
										 * 修复人：李超
										 * 修复日期：2015-10-23
										 */
										if(result == null){
											CToast.makeText(MinePasswordChangeActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
											return;
										}
										//BugEnd
										Log.v("url", "1" + result);

										try {
											JSONObject jsonObject = new JSONObject(
													result);
											info = jsonObject.getString("info");
											status = jsonObject
													.getString("status");
											Message message = new Message();
											message.what = 1;
											handler.sendMessage(message);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}.start();

							} else {
								Toast.makeText(MinePasswordChangeActivity.this,
										"密码不一致", Toast.LENGTH_LONG).show();
							}
						} else {
							Toast.makeText(MinePasswordChangeActivity.this,
									"请确认新密码", Toast.LENGTH_LONG).show();
						}
					} else {
						Toast.makeText(MinePasswordChangeActivity.this,
								"请填写新密码", Toast.LENGTH_LONG).show();
					}
				} else {
					Toast.makeText(MinePasswordChangeActivity.this, "请填写旧密码",
							Toast.LENGTH_LONG).show();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_password_change, menu);
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
