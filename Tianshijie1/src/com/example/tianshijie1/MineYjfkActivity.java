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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class MineYjfkActivity extends Activity {
	private ImageView iv_backyjfk;
	private TextView tv_send;
	private EditText et_yijian;
	Handler handler;
	String info, result, status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_mine_yjfk);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					Toast.makeText(MineYjfkActivity.this, info,
							Toast.LENGTH_LONG).show();
					if (status.equals("0")) {
						Intent intent = new Intent();
						intent.setClass(MineYjfkActivity.this,
								MineShezhiActivity.class);
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
		iv_backyjfk = (ImageView) findViewById(R.id.iv_backyjfk);
		tv_send = (TextView) findViewById(R.id.tv_send);
		et_yijian = (EditText) findViewById(R.id.et_yijian);
		iv_backyjfk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					public void run() {
						NameValuePair pair1 = new BasicNameValuePair("uid",
								LoginActivity.UID);
						NameValuePair pair2 = new BasicNameValuePair("content",
								et_yijian.getText().toString());

						final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
						pairList.add(pair1);
						pairList.add(pair2);
						PostUtil postUtil = new PostUtil();
						String url1 = "http://wap.tianshijie.com.cn/appuser/feedback";
						result = postUtil.DoPostNew(pairList, url1);
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
		getMenuInflater().inflate(R.menu.mine_yjfk, menu);
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
