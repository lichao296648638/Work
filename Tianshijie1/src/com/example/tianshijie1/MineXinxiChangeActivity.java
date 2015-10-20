package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class MineXinxiChangeActivity extends Activity {
	private TextView tv_title, tv_save, tv_change;
	private EditText et_xiugai;
	private ImageView iv_cha, iv_backmyxinxichange;
	String change, changename, key;
	Handler handler;
	String info;
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_mine_xinxi_change);
		change = getIntent().getStringExtra("change");
		changename = getIntent().getStringExtra("changename");
		key = getIntent().getStringExtra("key");
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(MineXinxiChangeActivity.this,
							changename + info, Toast.LENGTH_LONG).show();
					Intent intent = new Intent();
					intent.setClass(MineXinxiChangeActivity.this,
							MineXinxiActivity.class);
					startActivity(intent);
					finish();
					break;

				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_change = (TextView) findViewById(R.id.tv_change);
		et_xiugai = (EditText) findViewById(R.id.et_xiugai);
		iv_cha = (ImageView) findViewById(R.id.iv_cha);
		iv_backmyxinxichange = (ImageView) findViewById(R.id.iv_backmyxinxichange);
		if (change.equals("0")) {
			tv_change.setText(changename + "一旦确定不可修改");
		} else {
			tv_change.setVisibility(View.INVISIBLE);
		}
		tv_title.setText(changename);
		et_xiugai.setHint(changename);
		iv_backmyxinxichange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv_cha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_xiugai.setText("");
				et_xiugai.setHint(changename);
			}
		});
		tv_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (changename.equals("邮箱")) {
					if (isEmail(et_xiugai.getText().toString())) {
						new Thread() {
							public void run() {
								NameValuePair pair1 = new BasicNameValuePair(
										"uid", LoginActivity.UID);
								NameValuePair pair2 = new BasicNameValuePair(
										"filed", key);
								NameValuePair pair3 = new BasicNameValuePair(
										"value", et_xiugai.getText().toString());
								final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair1);
								pairList.add(pair2);
								pairList.add(pair3);
								PostUtil postUtil = new PostUtil();
								String url1 = "http://wap.tianshijie.com.cn/appuser/update";
								result = postUtil.DoPostNew(pairList, url1);
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

					} else {
						Toast.makeText(MineXinxiChangeActivity.this,
								"请填写正确的邮箱", Toast.LENGTH_LONG).show();
					}
				} else {
					if (!et_xiugai.getText().toString().equals("")) {
						new Thread() {
							public void run() {
								NameValuePair pair1 = new BasicNameValuePair(
										"uid", LoginActivity.UID);
								NameValuePair pair2 = new BasicNameValuePair(
										"filed", key);
								NameValuePair pair3 = new BasicNameValuePair(
										"value", et_xiugai.getText().toString());
								final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair1);
								pairList.add(pair2);
								pairList.add(pair3);
								PostUtil postUtil = new PostUtil();
								String url1 = "http://wap.tianshijie.com.cn/appuser/update";
								result = postUtil.DoPostNew(pairList, url1);
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

					} else {
						Toast.makeText(MineXinxiChangeActivity.this,
								"请填写要修改的内容", Toast.LENGTH_LONG).show();
					}

				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mine_xinxi_change, menu);
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

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
}
