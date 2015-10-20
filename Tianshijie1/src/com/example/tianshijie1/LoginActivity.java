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
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.text.method.TextKeyListener.Capitalize;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class LoginActivity extends Activity {
	public static final String PHONE = "phone";
	public static String UID = "uid";
	public static String USERNAME = "username";
	public static String PASSWORD = "password";

	public SharedPreferences sharedPrefrences;
	public Editor editor;
	private EditText et_password, et_phonenumber;
	private Button btn_login;
	private TextView tv_zhuce, tv_forgetpassword;
	private RelativeLayout rl_xuanzhong;
	int i = 0;
	String info;
	Handler handler;
	private ImageView iv_backlogin;

	String result, realname, invest_type, avatar, unreadmess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_login);
		sharedPrefrences = this.getSharedPreferences(PHONE,
				Activity.MODE_PRIVATE);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(LoginActivity.this, info, Toast.LENGTH_LONG)
							.show();
					if (info.equals("登录成功")) {
						USERNAME = et_phonenumber.getText().toString();
						PASSWORD = et_password.getText().toString();
						editor = getSharedPreferences(PHONE,
								Activity.MODE_PRIVATE).edit();
						editor.putString("realname", realname);
						editor.putString("invest_type", invest_type);
						editor.putString("username", USERNAME);
						editor.putString("password", PASSWORD);
						editor.putString("avatar", avatar);
						editor.putString("unreadmess", unreadmess);
						editor.putString("id", UID);
						editor.commit();
						Intent intent2 = new Intent();
						intent2.setAction("guandenglu");
						sendBroadcast(intent2);
						Intent intent = new Intent();
						intent.setClass(LoginActivity.this, MainActivity.class);
						intent.putExtra("realname", realname);
						intent.putExtra("invest_type", invest_type);
						intent.putExtra("avatar", avatar);
						intent.putExtra("unreadmess", unreadmess);
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
		et_password = (EditText) findViewById(R.id.et_password);
		et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
		et_phonenumber
				.setKeyListener(new TextKeyListener(Capitalize.NONE, true) {

					@Override
					public int getInputType() {
						// 0无键盘 1英文键盘 2模拟键盘 3数字键盘
						return InputType.TYPE_CLASS_NUMBER;
					}

				});
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_zhuce = (TextView) findViewById(R.id.tv_zhuce);
		tv_forgetpassword = (TextView) findViewById(R.id.tv_forgetpassword);
		rl_xuanzhong = (RelativeLayout) findViewById(R.id.rl_xuanzhong);
		iv_backlogin = (ImageView) findViewById(R.id.iv_backlogin);
		iv_backlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, TfirstActivity.class);
				startActivity(intent);
				finish();
			}
		});
		tv_zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, ZhuceActivity.class);
				startActivity(intent);
				finish();
			}
		});
		tv_forgetpassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this,
						Forgetpassword1Activity.class);
				startActivity(intent);
				finish();
			}
		});
		/**
		 * 对edittext 的输入进行监听 如果edittext 有内容 那么 可以点击 登录
		 */
		et_password.addTextChangedListener(new TextWatcher() {

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
				if (!et_password.getText().toString().equals("")
						&& !et_phonenumber.getText().toString().equals("")) {
					btn_login.setBackgroundResource(R.drawable.shape4);
					rl_xuanzhong.setBackgroundResource(R.drawable.shape1);
					i = 1;
				} else {
					btn_login.setBackgroundResource(R.drawable.shape5);
					rl_xuanzhong.setBackgroundColor(Color.parseColor("#cccccc"));
					i = 0;
				}
			}
		});
		et_phonenumber.addTextChangedListener(new TextWatcher() {

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
				if (!et_password.getText().toString().equals("")
						&& !et_phonenumber.getText().toString().equals("")) {

					btn_login.setBackgroundResource(R.drawable.shape);
					rl_xuanzhong.setBackgroundResource(R.drawable.shape1);
					i = 1;

				} else {
					btn_login.setBackgroundResource(R.drawable.shape5);
					rl_xuanzhong.setBackgroundColor(Color.parseColor("#f5f5f5"));
					i = 0;
				}
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i == 1) {
					new Thread() {
						public void run() {
							NameValuePair pair1 = new BasicNameValuePair(
									"username", et_phonenumber.getText()
											.toString());
							NameValuePair pair2 = new BasicNameValuePair(
									"password", et_password.getText()
											.toString());
							final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
							pairList.add(pair1);
							pairList.add(pair2);
							PostUtil postUtil = new PostUtil();
							String url1 = "http://wap.tianshijie.com.cn/appuser/login";
							result = postUtil.DoPostNew(pairList, url1);
							Log.v("url", "1" + result);

							try {
								JSONObject jsonObject = new JSONObject(result);
								info = jsonObject.getString("info");
								if (info.equals("登录成功")) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");
									JSONObject jsonObject2 = jsonArray
											.getJSONObject(0);
									String uid = jsonObject2.getString("uid");
									UID = uid;
									realname = jsonObject2
											.getString("realname");
									unreadmess = jsonObject2
											.getString("unreadmess");
									invest_type = jsonObject2
											.getString("invest_type");
									avatar = jsonObject2.getString("avatar");
								}

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
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something...
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, TfirstActivity.class);

			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
