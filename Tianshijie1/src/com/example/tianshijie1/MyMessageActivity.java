package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.example.tianshijie1.adapter.MessageAdapter;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.bean.MyMessage;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class MyMessageActivity extends Activity implements
		OnHeaderRefreshListener, OnFooterRefreshListener {
	String result, conut;
	Handler handler;
	MyMessage myMessage;
	List<MyMessage> listMyMessages;
	MessageAdapter messageAdapter;
	PullToRefreshView mPullToRefreshView;// 下拉
	private TextView tv_xiangmuming, tv_yidu;
	private ImageView iv_backmymessage;
	private ListView lv_xiaoxi;
	int msgcount;
	private TextView tv_meiyoumessage;
	private LinearLayout ll_meiyoumessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_my_message);
		// 注册广播
		registerBoradcastReceiver();
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 1:
					ll_meiyoumessage.setVisibility(View.VISIBLE);

					tv_meiyoumessage.setVisibility(View.GONE);
					tv_xiangmuming.setText("未读消息" + "(" + conut + ")");
					msgcount = Integer.parseInt(conut);

					messageAdapter = new MessageAdapter(MyMessageActivity.this,
							listMyMessages);
					lv_xiaoxi.setAdapter(messageAdapter);
					if (messageAdapter.getCount() <= 20) {
						lv_xiaoxi.setSelection(messageAdapter.getCount() - 20);
					} else {
						lv_xiaoxi.setSelection(messageAdapter.getCount() - 20);
					}

					break;
				case 2:
					if (listMyMessages.size() == 0) {
						ll_meiyoumessage.setVisibility(View.GONE);
						tv_meiyoumessage.setVisibility(View.VISIBLE);

					}
					Toast.makeText(MyMessageActivity.this, "没有更多消息",
							Toast.LENGTH_SHORT).show();
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub

		tv_xiangmuming = (TextView) findViewById(R.id.tv_xiangmuming);
		tv_yidu = (TextView) findViewById(R.id.tv_yidu);
		iv_backmymessage = (ImageView) findViewById(R.id.iv_backmymessage);
		lv_xiaoxi = (ListView) findViewById(R.id.lv_xiaoxi);
		ll_meiyoumessage = (LinearLayout) findViewById(R.id.ll_meiyoumessage);
		tv_meiyoumessage = (TextView) findViewById(R.id.tv_meiyoumessage);
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		listMyMessages = new ArrayList<MyMessage>();
		tv_yidu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				mypost2();
				listMyMessages.clear();
				mypost("0");

			}
		});
		iv_backmymessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mypost("0");

	}

	private void mypost(final String a) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				NameValuePair pair1 = new BasicNameValuePair("start", a);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				pairList.add(pair2);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appuser/mymessage";
				result = postUtil.DoPostNew(pairList, url1);
				Log.v("url", result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					conut = jsonObject2.getString("conut");
					Log.v("conut", conut);
					if (jsonObject2.length() == 1) {
						Message message = new Message();
						message.what = 2;
						handler.sendMessage(message);
					}
					for (int i = 0; i < jsonObject2.length() - 1; i++) {
						JSONObject jsonObject3 = jsonObject2.getJSONObject(i
								+ "");
						myMessage = new MyMessage();
						myMessage.setMsgfrom(jsonObject3.getString("msgfrom"));
						myMessage.setIsread(jsonObject3.getString("isread"));
						myMessage
								.setDateline(jsonObject3.getString("dateline"));
						myMessage.setPmid(jsonObject3.getString("pmid"));
						myMessage.setSubject(jsonObject3.getString("subject"));
						listMyMessages.add(myMessage);

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

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction("messagenum");
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("messagenum")) {
				String shuliang = intent.getStringExtra("shuliang");
				if (shuliang.equals("jianyi")) {
					msgcount--;
					tv_xiangmuming.setText("未读消息" + "(" + msgcount + ")");

				}
			}
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_message, menu);
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

	int i = 0;

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
		i = i + 20;
		mypost(i + "");
		Log.v("i", i + "");
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				// 设置更新时间
				// mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
				mPullToRefreshView.onHeaderRefreshComplete();
			}
		}, 1000);

	}

	private void mypost2() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair2);

				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appuser/sendmymessage";
				String result = postUtil.DoPostNew(pairList, url1);
				Log.v("url", result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					String info = jsonObject.getString("info");
					if (info.equals("读取成功")) {

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
	}
}
