package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class IdcordChangeActivity extends Activity {
	private TextView tv_title, tv_save, tv_change;
	private EditText et_xiugai;
	private ImageView iv_cha, iv_backidcord;
	String change, changename, key;
	Handler handler;
	String info;
	String result;
	String s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_idcord_change);
		change = getIntent().getStringExtra("change");
		changename = getIntent().getStringExtra("changename");
		key = getIntent().getStringExtra("key");
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(IdcordChangeActivity.this, info,
							Toast.LENGTH_LONG).show();
					if (info.equals("身份证不合法")) {

					} else {
						Intent intent = new Intent();
						intent.setClass(IdcordChangeActivity.this,
								MineXinxiActivity.class);
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
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_save = (TextView) findViewById(R.id.tv_save);
		tv_change = (TextView) findViewById(R.id.tv_change);
		et_xiugai = (EditText) findViewById(R.id.et_xiugai);
		iv_cha = (ImageView) findViewById(R.id.iv_cha);
		iv_backidcord = (ImageView) findViewById(R.id.iv_backidcord);
		if (change.equals("0")) {
			tv_change.setText(changename + "一旦确定不可修改");
		} else {
			tv_change.setVisibility(View.INVISIBLE);
		}
		tv_title.setText(changename);
		et_xiugai.setHint(changename);
		iv_backidcord.setOnClickListener(new OnClickListener() {

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
				final AlertDialog dlg = new AlertDialog.Builder(
						IdcordChangeActivity.this).create();
				dlg.show();
				dlg.setCancelable(true);
				Window window = dlg.getWindow();
				// *** 主要就是在这里实现这种效果的.
				// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
				window.setContentView(R.layout.idqueren);
				// 为确认按钮添加事件,执行退出应用操作
				final TextView tv_quxiao = (TextView) window
						.findViewById(R.id.tv_quxiao);
				final TextView tv_queren = (TextView) window
						.findViewById(R.id.tv_queren);
				final TextView tv_idcord = (TextView) window
						.findViewById(R.id.tv_idcord);
				tv_idcord.setText(et_xiugai.getText().toString());
				tv_quxiao.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dlg.cancel();
					}
				});
				tv_queren.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						dlg.dismiss();
						s = et_xiugai.getText().toString();
						s = s.replace(" ", "");
						Log.v("s", s);
						Log.v("s.length()", s.length() + "");

						if (s.length() == 18) {
							new Thread() {
								public void run() {
									NameValuePair pair1 = new BasicNameValuePair(
											"uid", LoginActivity.UID);
									NameValuePair pair2 = new BasicNameValuePair(
											"filed", key);
									NameValuePair pair3 = new BasicNameValuePair(
											"value", s);
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
							dlg.dismiss();
							Toast.makeText(IdcordChangeActivity.this,
									"请填写正确的身份证号码", Toast.LENGTH_LONG).show();
						}
					}
				});
				dlg.setCanceledOnTouchOutside(true);

			}

		});
		et_xiugai.addTextChangedListener(new TextWatcher() {
			int beforeTextLength = 0;
			int onTextLength = 0;
			boolean isChanged = false;

			int location = 0;// 记录光标的位置
			private char[] tempChar;
			private StringBuffer buffer = new StringBuffer();
			int konggeNumberB = 0;

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				onTextLength = s.length();
				buffer.append(s.toString());
				if (onTextLength == beforeTextLength || onTextLength <= 3
						|| isChanged) {
					isChanged = false;
					return;
				}
				isChanged = true;
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				beforeTextLength = s.length();
				if (buffer.length() > 0) {
					buffer.delete(0, buffer.length());
				}
				konggeNumberB = 0;
				for (int i = 0; i < s.length(); i++) {
					if (s.charAt(i) == ' ') {
						konggeNumberB++;
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (isChanged) {
					location = et_xiugai.getSelectionEnd();
					int index = 0;
					while (index < buffer.length()) {
						if (buffer.charAt(index) == ' ') {
							buffer.deleteCharAt(index);
						} else {
							index++;
						}
					}

					index = 0;
					int konggeNumberC = 0;
					while (index < buffer.length()) {
						if (index % 5 == 4) {
							buffer.insert(index, ' ');
							konggeNumberC++;
						}
						index++;
					}

					if (konggeNumberC > konggeNumberB) {
						location += (konggeNumberC - konggeNumberB);
					}

					tempChar = new char[buffer.length()];
					buffer.getChars(0, buffer.length(), tempChar, 0);
					String str = buffer.toString();
					if (location > str.length()) {
						location = str.length();
					} else if (location < 0) {
						location = 0;
					}

					et_xiugai.setText(str);
					Editable etable = et_xiugai.getText();
					Selection.setSelection(etable, location);
					isChanged = false;
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
}