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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.FindActivity.ReceiveBroadCast;
import com.example.tianshijie1.adapter.MainlistAdapter;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.ListViewSwipeGesture;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class MineguanzhuActivity extends Activity {
	private ListView lv_xiangmu;
	Mingxingxiangmu muMingxingxiangmu;
	List<Mingxingxiangmu> listMingxingxiangmus;
	private MainlistAdapter mainlistAdapter;
	String result;
	Handler handler;
	private ImageView iv_backguanzhi;
	private TextView tv_add;
	private ReceiveBroadCast receiveBroadCast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_mineguanzhu);
		/** 注册广播 */
		receiveBroadCast = new ReceiveBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("shuaxin2"); // 只有持有相同的action的接受者才能接收此广播
		registerReceiver(receiveBroadCast, filter);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 1:
					mainlistAdapter = new MainlistAdapter(
							MineguanzhuActivity.this, listMingxingxiangmus, 1);
					lv_xiangmu.setAdapter(mainlistAdapter);

					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	class ReceiveBroadCast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// 得到广播中得到的数据，并显示出来
			Log.v("a", "a");
			listMingxingxiangmus.clear();
			new Thread() {
				public void run() {

					NameValuePair pair3 = new BasicNameValuePair("type", "1");
					NameValuePair pair5 = new BasicNameValuePair("uid",
							LoginActivity.UID);
					final List<NameValuePair> pairList2 = new ArrayList<NameValuePair>();
					pairList2.add(pair3);
					pairList2.add(pair5);
					PostUtil postUtil2 = new PostUtil();
					String url2 = "http://wap.tianshijie.com.cn/appuser/my_project";
					result = postUtil2.DoPostNew(pairList2, url2);
					/**
					 * BugStart
					 * Bug编号：BUG4
					 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
					 * 修复人：李超
					 * 修复日期：2015-10-23
					 */
					if(result == null){
						CToast.makeText(MineguanzhuActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
						return;
					}
					//BugEnd
					Log.v("url", "1" + result);
					try {
						JSONObject jsonObject = new JSONObject(result);
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("data");
						JSONArray jsonArray = jsonObject2
								.getJSONArray("project");
						for (int i = 0; i < jsonArray.length(); i++) {
							muMingxingxiangmu = new Mingxingxiangmu();
							JSONObject jsonObject3 = jsonArray.getJSONObject(i);
							muMingxingxiangmu.setImage(MainActivity.PJURl
									+ jsonObject3.getString("image"));
							muMingxingxiangmu.setCity_val(jsonObject3
									.getString("city_val"));
							muMingxingxiangmu.setCollect(jsonObject3
									.getString("collect"));
							muMingxingxiangmu.setCopy_price(jsonObject3
									.getString("copy_price"));
							muMingxingxiangmu.setSummary(jsonObject3
									.getString("summary"));
							muMingxingxiangmu
									.setId(jsonObject3.getString("id"));
							muMingxingxiangmu.setStatus_val(jsonObject3
									.getString("status_val"));
							muMingxingxiangmu.setName(jsonObject3
									.getString("name"));
							muMingxingxiangmu.setCity_val(jsonObject3
									.getString("city_val"));
							muMingxingxiangmu.setSy_time(jsonObject3
									.getString("sy_time"));
							muMingxingxiangmu.setCopy_number(jsonObject3
									.getString("copy_number"));
							muMingxingxiangmu.setLoan_amount(jsonObject3
									.getString("loan_amount"));
							muMingxingxiangmu.setJindu(jsonObject3
									.getString("jindu"));
							muMingxingxiangmu.setVersion(jsonObject3
									.getString("version"));
							listMingxingxiangmus.add(muMingxingxiangmu);
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}.start();
		}
	}

	private void init() {
		// TODO Auto-generated method stub
		listMingxingxiangmus = new ArrayList<Mingxingxiangmu>();
		lv_xiangmu = (ListView) findViewById(R.id.lv_xiangmu);
		iv_backguanzhi = (ImageView) findViewById(R.id.iv_backguanzhi);
		tv_add = (TextView) findViewById(R.id.tv_add);

		TextView emptyView = new TextView(MineguanzhuActivity.this);
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("您还没有关注任何项目，快去关注吧！");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_xiangmu.getParent()).addView(emptyView);
		lv_xiangmu.setEmptyView(emptyView);
		iv_backguanzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MineguanzhuActivity.this, MainActivity.class);
				intent.putExtra("realname", MainActivity.REALNAME);
				intent.putExtra("invest_type", MainActivity.INVEST_TYPEi);
				intent.putExtra("avatar", MainActivity.AVATAR);
				startActivity(intent);
			}
		});
		lv_xiangmu.setFocusable(false);
		final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(
				lv_xiangmu, swipeListener, this);
		touchListener.SwipeType = ListViewSwipeGesture.Dismiss; // Set two
																// options at
																// background of
																// list item
		lv_xiangmu.setOnTouchListener(touchListener);
       
		new Thread() {
			public void run() {

				NameValuePair pair3 = new BasicNameValuePair("type", "1");
				NameValuePair pair5 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				final List<NameValuePair> pairList2 = new ArrayList<NameValuePair>();
				pairList2.add(pair3);
				pairList2.add(pair5);
				PostUtil postUtil2 = new PostUtil();
				String url2 = "http://wap.tianshijie.com.cn/appuser/my_project";
				result = postUtil2.DoPostNew(pairList2, url2);
				/**
				 * BugStart
				 * Bug编号：BUG4
				 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
				 * 修复人：李超
				 * 修复日期：2015-10-23
				 */
				if(result == null){
					CToast.makeText(MineguanzhuActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					JSONArray jsonArray = jsonObject2.getJSONArray("project");

					for (int i = 0; i < jsonArray.length(); i++) {
						muMingxingxiangmu = new Mingxingxiangmu();
						JSONObject jsonObject3 = jsonArray.getJSONObject(i);
						muMingxingxiangmu.setImage(MainActivity.PJURl
								+ jsonObject3.getString("image"));
						muMingxingxiangmu.setCity_val(jsonObject3
								.getString("city_val"));
						muMingxingxiangmu.setCollect(jsonObject3
								.getString("collect"));
						muMingxingxiangmu.setCopy_price(jsonObject3
								.getString("copy_price"));
						muMingxingxiangmu.setId(jsonObject3.getString("id"));
						muMingxingxiangmu.setStatus_val(jsonObject3
								.getString("status_val"));
						muMingxingxiangmu
								.setName(jsonObject3.getString("name"));
						muMingxingxiangmu.setCity_val(jsonObject3
								.getString("city_val"));
						muMingxingxiangmu.setSy_time(jsonObject3
								.getString("sy_time"));
						muMingxingxiangmu.setCopy_number(jsonObject3
								.getString("copy_number"));
						muMingxingxiangmu.setLoan_amount(jsonObject3
								.getString("loan_amount"));
						muMingxingxiangmu.setJindu(jsonObject3
								.getString("jindu"));
						muMingxingxiangmu.setVersion(jsonObject3
								.getString("version"));
						muMingxingxiangmu.setSummary(jsonObject3
								.getString("summary"));
						listMingxingxiangmus.add(muMingxingxiangmu);
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mineguanzhu, menu);
		return true;
	}

	ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

		@Override
		public void FullSwipeListView(int position) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Action_2",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void HalfSwipeListView(int position) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "Action_1",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void LoadDataForScroll(int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT)
					.show();
			for (int i : reverseSortedPositions) {
				final String id = listMingxingxiangmus.get(i).getId();
				new Thread() {
					public void run() {
						NameValuePair pair3 = new BasicNameValuePair("pid", id);
						NameValuePair pair5 = new BasicNameValuePair("uid",
								LoginActivity.UID);
						final List<NameValuePair> pairList2 = new ArrayList<NameValuePair>();
						pairList2.add(pair3);
						pairList2.add(pair5);
						PostUtil postUtil2 = new PostUtil();
						String url2 = "http://wap.tianshijie.com.cn/appproject/favorite";
						result = postUtil2.DoPostNew(pairList2, url2);
						/**
						 * BugStart
						 * Bug编号：BUG4
						 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
						 * 修复人：李超
						 * 修复日期：2015-10-23
						 */
						if(result == null){
							CToast.makeText(MineguanzhuActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
							return;
						}
						//BugEnd
						Log.v("san", "1" + result);
						Intent intent = new Intent(); // Itent就是我们要发送的内容
						intent.setAction("shuaxin"); // 设置你这个广播的action
						intent.putExtra("pid", id);
						sendBroadcast(intent); // 发送广播

						try {
							JSONObject jsonObject = new JSONObject(result);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				listMingxingxiangmus.remove(i);
				Log.v("a", "shan");
				mainlistAdapter.notifyDataSetChanged();
			}
		}

		@Override
		public void OnClickListView(int position) {
			// TODO Auto-generated method stub
			if (listMingxingxiangmus.get(position).getVersion().equals("3")) {
				Intent intent = new Intent();
				intent.setClass(MineguanzhuActivity.this,
						XiangmushitiActivity.class);
				intent.putExtra("id", listMingxingxiangmus.get(position)
						.getId());
				Log.v("id", listMingxingxiangmus.get(position).getId());
				startActivity(intent);
			} else {
				Intent intent = new Intent();
				intent.setClass(MineguanzhuActivity.this,
						XiangqingFeishitiActivity.class);
				intent.putExtra("id", listMingxingxiangmus.get(position)
						.getId());
				Log.v("id", listMingxingxiangmus.get(position).getId());
				startActivity(intent);
			}
		}

	};

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
