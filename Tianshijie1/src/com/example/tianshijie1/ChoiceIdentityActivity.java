package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class ChoiceIdentityActivity extends Activity {
	private CheckBox cb_geren, cb_jigou, cb_chuangyezhe;
	private Button btn_queren;
	public static String SHENFENZHI;
	public SharedPreferences sharedPrefrences;
	Handler handler;
	String info, data, status;
	public Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_choice_identity);
		sharedPrefrences = this.getSharedPreferences(LoginActivity.PHONE,
				Activity.MODE_PRIVATE);

		data = getIntent().getStringExtra("data");

		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(ChoiceIdentityActivity.this, info,
							Toast.LENGTH_LONG).show();

					if (status.equals("0")) {
						editor = getSharedPreferences(LoginActivity.PHONE,
								Activity.MODE_PRIVATE).edit();
						editor.putString("id", data);
						editor.putString("invest_type", SHENFENZHI);
						editor.putString("username", getIntent()
								.getStringExtra("et_phonenumber"));
						editor.putString("password", getIntent()
								.getStringExtra("et_password"));
						editor.commit();
						Intent intent = new Intent();
						intent.setClass(ChoiceIdentityActivity.this,
								MainActivity.class);
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
		cb_geren = (CheckBox) findViewById(R.id.cb_geren);
		cb_jigou = (CheckBox) findViewById(R.id.cb_jigou);
		cb_chuangyezhe = (CheckBox) findViewById(R.id.cb_chuangyezhe);
		btn_queren = (Button) findViewById(R.id.btn_queren);
		cb_chuangyezhe
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (cb_chuangyezhe.isChecked()) {
							cb_jigou.setChecked(false);
							cb_geren.setChecked(false);
							SHENFENZHI = "0";
						}
					}
				});
		cb_geren.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (cb_geren.isChecked()) {
					cb_jigou.setChecked(false);
					cb_chuangyezhe.setChecked(false);
					SHENFENZHI = "1";
				}
			}
		});
		cb_jigou.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (cb_jigou.isChecked()) {
					cb_chuangyezhe.setChecked(false);
					cb_geren.setChecked(false);
					SHENFENZHI = "2";
				}
			}
		});
		btn_queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					public void run() {
						NameValuePair pair1 = new BasicNameValuePair("uid",
								data);
						NameValuePair pair2 = new BasicNameValuePair(
								"invest_type", SHENFENZHI);
						final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
						pairList.add(pair1);
						pairList.add(pair2);
						PostUtil postUtil = new PostUtil();
						String url1 = "http://wap.tianshijie.com.cn/appuser/change_invest_type";
						String result = postUtil.DoPostNew(pairList, url1);
						/**
						 * BugStart
						 * Bug编号：BUG4
						 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
						 * 修复人：李超
						 * 修复日期：2015-10-23
						 */
						if(result == null){
							CToast.makeText(ChoiceIdentityActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
							return;
						}
						//BugEnd
						Log.v("url", "1" + result);
						try {
							JSONObject jsonObject = new JSONObject(result);
							info = jsonObject.getString("info");
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
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choice_identity, menu);
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
