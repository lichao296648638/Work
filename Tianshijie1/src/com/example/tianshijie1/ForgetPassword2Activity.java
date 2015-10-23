package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class ForgetPassword2Activity extends Activity {
	private EditText et_renamepass1, et_renamepass2;
	private Button btn_ok;
	private RelativeLayout rl_xuanzhong;
	int i = 0;
	String info;
	Handler handler;
	String phone, result;
	String realname, invest_type, avatar, unreadmess;
	public SharedPreferences sharedPrefrences;
	public Editor editor;
	private ImageView iv_backfp2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_forget_password2);
		phone = getIntent().getStringExtra("phone");
		sharedPrefrences = this.getSharedPreferences(LoginActivity.PHONE,
				Activity.MODE_PRIVATE);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(ForgetPassword2Activity.this, info,
							Toast.LENGTH_SHORT).show();
					if (info.equals("密码重置成功！")) {

						mypost();
					}
					break;
				case 2:
					if (info.equals("登录成功")) {
						LoginActivity.USERNAME = phone;
						LoginActivity.PASSWORD = et_renamepass1.getText()
								.toString();
						editor = getSharedPreferences(LoginActivity.PHONE,
								Activity.MODE_PRIVATE).edit();
						editor.putString("realname", realname);
						editor.putString("invest_type", invest_type);
						editor.putString("username", LoginActivity.USERNAME);
						editor.putString("password", LoginActivity.PASSWORD);
						editor.putString("avatar", avatar);
						editor.putString("unreadmess", unreadmess);
						editor.putString("id", LoginActivity.UID);
						editor.commit();
						Intent intent = new Intent();
						intent.setClass(ForgetPassword2Activity.this,
								MainActivity.class);
						intent.putExtra("realname", realname);
						intent.putExtra("invest_type", invest_type);
						intent.putExtra("avatar", avatar);
						startActivity(intent);
						finish();
						break;

					}
					super.handleMessage(msg);
				}
			}
		};
	}

	private void mypost() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair1 = new BasicNameValuePair("username", phone);
				NameValuePair pair2 = new BasicNameValuePair("password",
						et_renamepass1.getText().toString());
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				pairList.add(pair2);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appuser/login";
				result = postUtil.DoPostNew(pairList, url1);
				/**
				 * BugStart
				 * Bug编号：BUG4
				 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
				 * 修复人：李超
				 * 修复日期：2015-10-23
				 */
				if(result == null){
					CToast.makeText(ForgetPassword2Activity.this, getResources().getText(R.string.toast_error_network), 3000).show();
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);

				try {
					JSONObject jsonObject = new JSONObject(result);
					info = jsonObject.getString("info");
					if (info.equals("登录成功")) {
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						JSONObject jsonObject2 = jsonArray.getJSONObject(0);
						String uid = jsonObject2.getString("uid");
						LoginActivity.UID = uid;
						realname = jsonObject2.getString("realname");
						invest_type = jsonObject2.getString("invest_type");
						avatar = jsonObject2.getString("avatar");
						unreadmess = jsonObject2.getString("unreadmess");
					}
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void init() {
		// TODO Auto-generated method stub
		et_renamepass1 = (EditText) findViewById(R.id.et_renamepass1);
		et_renamepass2 = (EditText) findViewById(R.id.et_renamepass2);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		iv_backfp2 = (ImageView) findViewById(R.id.iv_backfp2);
		iv_backfp2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		rl_xuanzhong = (RelativeLayout) findViewById(R.id.rl_xuanzhong);
		/**
		 * 对edittext 的输入进行监听 如果edittext 有内容 那么 可以点击 登录
		 */
		et_renamepass1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!et_renamepass1.getText().toString().equals("")
						&& !et_renamepass2.getText().toString().equals("")) {
					btn_ok.setBackgroundResource(R.drawable.shape4);
					rl_xuanzhong.setBackgroundResource(R.drawable.shape1);
					i = 1;
				} else {
					btn_ok.setBackgroundResource(R.drawable.shape5);
					rl_xuanzhong.setBackgroundColor(Color.parseColor("#f5f5f5"));
					i = 0;
				}
			}
		});
		/**
		 * 对edittext 的输入进行监听 如果edittext 有内容 那么 可以点击 登录
		 */
		et_renamepass2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!et_renamepass1.getText().toString().equals("")
						&& !et_renamepass2.getText().toString().equals("")) {
					btn_ok.setBackgroundResource(R.drawable.shape4);
					rl_xuanzhong.setBackgroundResource(R.drawable.shape1);
					i = 1;
				} else {
					btn_ok.setBackgroundResource(R.drawable.shape5);
					rl_xuanzhong.setBackgroundColor(Color.parseColor("#f5f5f5"));
					i = 0;
				}
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_renamepass1.getText().toString().length() >= 6
						&& et_renamepass2.getText().toString().length() >= 6) {
					if (i == 1) {
						new Thread() {
							public void run() {

								NameValuePair pair1 = new BasicNameValuePair(
										"mobile", phone);
								NameValuePair pair3 = new BasicNameValuePair(
										"password", et_renamepass1.getText()
												.toString());
								NameValuePair pair2 = new BasicNameValuePair(
										"pwdconfirm", et_renamepass2.getText()
												.toString());
								NameValuePair pair4 = new BasicNameValuePair(
										"uid", getIntent()
												.getStringExtra("uid"));
								final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair1);
								pairList.add(pair2);
								pairList.add(pair3);
								pairList.add(pair4);
								PostUtil postUtil = new PostUtil();
								String url1 = "http://wap.tianshijie.com.cn/appuser/resetpassword";
								String result = postUtil.DoPostNew(pairList,
										url1);
								/**
								 * BugStart
								 * Bug编号：BUG4
								 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
								 * 修复人：李超
								 * 修复日期：2015-10-23
								 */
								if(result == null){
									CToast.makeText(ForgetPassword2Activity.this, getResources().getText(R.string.toast_error_network), 3000).show();
									return;
								}
								//BugEnd
								Log.v("url", "1" + result);
								try {
									JSONObject jsonObject = new JSONObject(
											result);
									info = jsonObject.getString("info");

									Message message = new Message();
									message.what = 1;
									handler.sendMessage(message);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}.start();
					}
				} else {
					Toast.makeText(ForgetPassword2Activity.this, "密码不能少于6位",
							Toast.LENGTH_SHORT).show();
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_password2, menu);
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
