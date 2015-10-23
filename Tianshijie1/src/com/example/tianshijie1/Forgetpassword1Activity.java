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

import com.example.tianshijie1.ZhuceActivity.ThreadShow;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class Forgetpassword1Activity extends Activity {
	private EditText et_phonenumber, et_yanzhengma;
	private TextView tv_getyanzhengma;
	private Button btn_queren;
	private RelativeLayout rl_xuanzhong, rl_yzmxuanzhong;
	int i;
	Handler handler;
	String info, uid, status, phone, yanzhengdata;
	private int miao = 59;
	boolean stopThread = false;
	int yanzheng = 0;
	private ImageView iv_backfp1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_forgetpassword1);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:

					if (status.equals("0")) {
						Toast.makeText(Forgetpassword1Activity.this, info,
								Toast.LENGTH_LONG).show();
						Intent intent = new Intent();
						intent.setClass(Forgetpassword1Activity.this,
								ForgetPassword2Activity.class);
						intent.putExtra("phone", phone);
						intent.putExtra("uid", uid);
						startActivity(intent);
						finish();
					}
					break;
				case 2:
					if (miao == 0) {

						stopThread = true;
						tv_getyanzhengma.setTextSize(14);
						tv_getyanzhengma.setText("获取验证码");
						tv_getyanzhengma
								.setBackgroundResource(R.drawable.shape1);
						tv_getyanzhengma.setTextColor(Color
								.parseColor("#e62f17"));
						rl_yzmxuanzhong
								.setBackgroundResource(R.drawable.shape4);
						yanzheng = 0;
					} else {
						tv_getyanzhengma.setTextSize(12);
						tv_getyanzhengma.setText("(" + Integer.toString(miao--)
								+ ")" + "秒重新获取");
						System.out.println("receive....");
						miao = miao--;

					}
					break;
				case 3:
					if (info.equals("验证码已发送")) {
						miao = 59;
						tv_getyanzhengma
								.setBackgroundResource(R.drawable.shape5);
						rl_yzmxuanzhong.setBackgroundColor(Color
								.parseColor("#ffffff"));
						tv_getyanzhengma.setTextColor(Color
								.parseColor("#ffffff"));
						stopThread = false;
						new Thread(new ThreadShow()).start();
						yanzheng = 1;
					} else {
						Toast.makeText(Forgetpassword1Activity.this, info,
								Toast.LENGTH_SHORT).show();
					}
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		et_phonenumber = (EditText) findViewById(R.id.et_phonenumber);
		et_yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
		tv_getyanzhengma = (TextView) findViewById(R.id.tv_getyanzhengma);
		btn_queren = (Button) findViewById(R.id.btn_queren);
		iv_backfp1 = (ImageView) findViewById(R.id.iv_backfp1);
		iv_backfp1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Forgetpassword1Activity.this,
						TfirstActivity.class);
				startActivity(intent);
				finish();
			}
		});
		rl_xuanzhong = (RelativeLayout) findViewById(R.id.rl_xuanzhong);
		rl_yzmxuanzhong = (RelativeLayout) findViewById(R.id.rl_yzmxuanzhong);
		et_yanzhengma.addTextChangedListener(new TextWatcher() {

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
				if (!et_yanzhengma.getText().toString().equals("")
						&& !et_phonenumber.getText().toString().equals("")) {

					btn_queren.setBackgroundResource(R.drawable.shape);
					rl_xuanzhong.setBackgroundResource(R.drawable.shape1);
					i = 1;

				} else {

					btn_queren.setBackgroundResource(R.drawable.shape5);
					rl_xuanzhong.setBackgroundColor(Color.parseColor("#f5f5f5"));
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
				if (!et_yanzhengma.getText().toString().equals("")
						&& !et_phonenumber.getText().toString().equals("")) {
					btn_queren.setBackgroundResource(R.drawable.shape);
					rl_xuanzhong.setBackgroundResource(R.drawable.shape1);
					i = 1;

				} else {

					btn_queren.setBackgroundResource(R.drawable.shape5);
					rl_xuanzhong.setBackgroundColor(Color.parseColor("#f5f5f5"));
					i = 0;

				}
			}
		});
		btn_queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i == 1) {
					if (et_yanzhengma.getText().toString().equals(yanzhengdata)) {
						new Thread() {
							public void run() {
								phone = et_phonenumber.getText().toString();
								NameValuePair pair1 = new BasicNameValuePair(
										"mobile", phone);

								final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair1);

								PostUtil postUtil = new PostUtil();
								String url1 = "http://wap.tianshijie.com.cn/appuser/forgetpassword";
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
									CToast.makeText(Forgetpassword1Activity.this, getResources().getText(R.string.toast_error_network), 3000).show();
									return;
								}
								//BugEnd
								Log.v("url", "1" + result);
								try {
									JSONObject jsonObject = new JSONObject(
											result);
									info = jsonObject.getString("info");
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");
									JSONObject jsonObject2 = jsonArray
											.getJSONObject(0);
									uid = jsonObject2.getString("uid");
									status = jsonObject.getString("status");
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
						Toast.makeText(Forgetpassword1Activity.this,
								"请输入正确的验证码", Toast.LENGTH_SHORT).show();
					}

				}
			}
		});
		tv_getyanzhengma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (yanzheng == 0) {

					new Thread() {
						public void run() {
							NameValuePair pair1 = new BasicNameValuePair(
									"mobile", et_phonenumber.getText()
											.toString());
							final List<NameValuePair> pairList = new ArrayList<NameValuePair>();

							pairList.add(pair1);
							PostUtil postUtil = new PostUtil();
							String url1 = "http://wap.tianshijie.com.cn/appuser/ajaxmobilemessage";
							String result = postUtil.DoPostNew(pairList, url1);
							/**
							 * BugStart
							 * Bug编号：BUG4
							 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
							 * 修复人：李超
							 * 修复日期：2015-10-23
							 */
							if(result == null){
								CToast.makeText(Forgetpassword1Activity.this, getResources().getText(R.string.toast_error_network), 3000).show();
								return;
							}
							//BugEnd
							Log.v("result", result);
							try {
								JSONObject jsonObject = new JSONObject(result);
								info = jsonObject.getString("info");
								yanzhengdata = jsonObject.getString("data");
								Message message = new Message();
								message.what = 3;
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
		getMenuInflater().inflate(R.menu.forgetpassword1, menu);
		return true;
	}

	// 线程类
	class ThreadShow implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!stopThread) {
				try {

					Thread.sleep(1000);
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		}
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
			intent.setClass(Forgetpassword1Activity.this, TfirstActivity.class);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
