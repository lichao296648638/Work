package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.adapter.MainlistAdapter;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class FindActivity extends Activity {
	private ListView lv_xiangmu;

	Mingxingxiangmu muMingxingxiangmu;
	List<Mingxingxiangmu> listMingxingxiangmus;
	private MainlistAdapter mainlistAdapter;
	private EditText et_find;
	private ImageView iv_cha, iv_backfind;
	private TextView tv_find;
	Handler handler;
	String info;
	String result;
	private ReceiveBroadCast receiveBroadCast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_find);
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

					mainlistAdapter = new MainlistAdapter(FindActivity.this,
							listMingxingxiangmus, 0);
					lv_xiangmu.setAdapter(mainlistAdapter);
					break;
				case 2:
					if (!info.equals("项目信息")) {
						Toast.makeText(FindActivity.this, info,
								Toast.LENGTH_SHORT).show();
					}

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
			new Thread() {
				public void run() {
					NameValuePair pair3 = new BasicNameValuePair("keyword",
							et_find.getText().toString());
					final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
					pairList.add(pair3);
					PostUtil postUtil = new PostUtil();
					String url1 = "http://wap.tianshijie.com.cn/appproject/search";
					result = postUtil.DoPostNew(pairList, url1);
					/**
					 * BugStart
					 * Bug编号：BUG4
					 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
					 * 修复人：李超
					 * 修复日期：2015-10-23
					 */
					if(result == null){
						CToast.makeText(FindActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
						return;
					}
					//BugEnd
					Log.v("url", "1" + result);
					listMingxingxiangmus.clear();
					try {
						JSONObject jsonObject = new JSONObject(result);
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("data");
						info = jsonObject.getString("info");
						Message message2 = new Message();
						message2.what = 2;
						handler.sendMessage(message2);
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
							muMingxingxiangmu.setCopy_price(jsonObject3
									.getString("copy_price"));
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
							muMingxingxiangmu.setIs_sc(jsonObject3
									.getString("is_sc"));
							muMingxingxiangmu.setJindu(jsonObject3
									.getString("jindu"));
							muMingxingxiangmu.setSummary(jsonObject3
									.getString("summary"));
							muMingxingxiangmu
									.setId(jsonObject3.getString("id"));
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
		et_find = (EditText) findViewById(R.id.et_find);
		iv_cha = (ImageView) findViewById(R.id.iv_cha);
		iv_backfind = (ImageView) findViewById(R.id.iv_backfind);
		tv_find = (TextView) findViewById(R.id.tv_find);
		et_find.requestFocus();
		et_find.requestFocus(); // edittext是一个EditText控件
		Timer timer = new Timer(); // 设置定时器
		timer.schedule(new TimerTask() {
			@Override
			public void run() { // 弹出软键盘的代码
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et_find, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		}, 300); // 设置300毫秒的时长
		et_find.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					if (!et_find.getText().toString().equals("")) {
						View view = getWindow().peekDecorView();
						if (view != null) {
							InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							inputmanger.hideSoftInputFromWindow(
									view.getWindowToken(), 0);
						}
						new Thread() {
							public void run() {
								NameValuePair pair3 = new BasicNameValuePair(
										"keyword", et_find.getText().toString());
								final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair3);
								PostUtil postUtil = new PostUtil();
								String url1 = "http://wap.tianshijie.com.cn/appproject/search";
								result = postUtil.DoPostNew(pairList, url1);
								/**
								 * BugStart
								 * Bug编号：BUG4
								 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
								 * 修复人：李超
								 * 修复日期：2015-10-23
								 */
								if(result == null){
									CToast.makeText(FindActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
									return;
								}
								//BugEnd
								Log.v("url", "1" + result);
								listMingxingxiangmus.clear();
								try {
									JSONObject jsonObject = new JSONObject(
											result);
									JSONObject jsonObject2 = jsonObject
											.getJSONObject("data");
									info = jsonObject.getString("info");
									Message message2 = new Message();
									message2.what = 2;
									handler.sendMessage(message2);
									JSONArray jsonArray = jsonObject2
											.getJSONArray("project");
									for (int i = 0; i < jsonArray.length(); i++) {
										muMingxingxiangmu = new Mingxingxiangmu();
										JSONObject jsonObject3 = jsonArray
												.getJSONObject(i);
										muMingxingxiangmu.setImage(MainActivity.PJURl
												+ jsonObject3
														.getString("image"));
										muMingxingxiangmu
												.setCity_val(jsonObject3
														.getString("city_val"));
										muMingxingxiangmu
												.setCollect(jsonObject3
														.getString("collect"));
										muMingxingxiangmu.setCopy_price(jsonObject3
												.getString("copy_price"));
										muMingxingxiangmu
												.setSummary(jsonObject3
														.getString("summary"));
										muMingxingxiangmu.setStatus_val(jsonObject3
												.getString("status_val"));
										muMingxingxiangmu.setName(jsonObject3
												.getString("name"));
										muMingxingxiangmu
												.setCity_val(jsonObject3
														.getString("city_val"));
										muMingxingxiangmu
												.setSy_time(jsonObject3
														.getString("sy_time"));
										muMingxingxiangmu.setCopy_number(jsonObject3
												.getString("copy_number"));
										muMingxingxiangmu.setLoan_amount(jsonObject3
												.getString("loan_amount"));
										muMingxingxiangmu.setIs_sc(jsonObject3
												.getString("is_sc"));
										muMingxingxiangmu.setJindu(jsonObject3
												.getString("jindu"));
										muMingxingxiangmu.setId(jsonObject3
												.getString("id"));
										listMingxingxiangmus
												.add(muMingxingxiangmu);
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

					} else {
						Toast.makeText(FindActivity.this, "请填写要查找的内容",
								Toast.LENGTH_LONG).show();
					}

					return true;
				}
				return false;
			}
		});
		iv_backfind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View view = getWindow().peekDecorView();
				if (view != null) {
					InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
							0);
				}
				finish();
			}
		});
		iv_cha.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_find.setText("");
			}
		});
		tv_find.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!et_find.getText().toString().equals("")) {
					View view = getWindow().peekDecorView();
					if (view != null) {
						InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						inputmanger.hideSoftInputFromWindow(
								view.getWindowToken(), 0);
					}
					new Thread() {
						public void run() {
							NameValuePair pair3 = new BasicNameValuePair(
									"keyword", et_find.getText().toString());
							final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
							pairList.add(pair3);
							PostUtil postUtil = new PostUtil();
							String url1 = "http://wap.tianshijie.com.cn/appproject/search";
							result = postUtil.DoPostNew(pairList, url1);
							/**
							 * BugStart
							 * Bug编号：BUG4
							 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
							 * 修复人：李超
							 * 修复日期：2015-10-23
							 */
							if(result == null){
								CToast.makeText(FindActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
								return;
							}
							//BugEnd
							Log.v("url", "1" + result);
							listMingxingxiangmus.clear();
							try {
								JSONObject jsonObject = new JSONObject(result);
								JSONObject jsonObject2 = jsonObject
										.getJSONObject("data");
								info = jsonObject.getString("info");
								Message message2 = new Message();
								message2.what = 2;
								handler.sendMessage(message2);
								JSONArray jsonArray = jsonObject2
										.getJSONArray("project");
								for (int i = 0; i < jsonArray.length(); i++) {
									muMingxingxiangmu = new Mingxingxiangmu();
									JSONObject jsonObject3 = jsonArray
											.getJSONObject(i);
									muMingxingxiangmu
											.setImage(MainActivity.PJURl
													+ jsonObject3
															.getString("image"));
									muMingxingxiangmu.setCity_val(jsonObject3
											.getString("city_val"));
									muMingxingxiangmu.setCollect(jsonObject3
											.getString("collect"));
									muMingxingxiangmu.setCopy_price(jsonObject3
											.getString("copy_price"));
									muMingxingxiangmu.setSummary(jsonObject3
											.getString("summary"));
									muMingxingxiangmu.setStatus_val(jsonObject3
											.getString("status_val"));
									muMingxingxiangmu.setName(jsonObject3
											.getString("name"));
									muMingxingxiangmu.setCity_val(jsonObject3
											.getString("city_val"));
									muMingxingxiangmu.setSy_time(jsonObject3
											.getString("sy_time"));
									muMingxingxiangmu
											.setCopy_number(jsonObject3
													.getString("copy_number"));
									muMingxingxiangmu
											.setLoan_amount(jsonObject3
													.getString("loan_amount"));
									muMingxingxiangmu.setIs_sc(jsonObject3
											.getString("is_sc"));
									muMingxingxiangmu.setJindu(jsonObject3
											.getString("jindu"));
									muMingxingxiangmu.setId(jsonObject3
											.getString("id"));
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

				} else {
					Toast.makeText(FindActivity.this, "请填写要查找的内容",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		listMingxingxiangmus = new ArrayList<Mingxingxiangmu>();
		lv_xiangmu = (ListView) findViewById(R.id.lv_xiangmu);
		lv_xiangmu.setFocusable(false);

		lv_xiangmu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FindActivity.this,
						XiangqingFeishitiActivity.class);
				intent.putExtra("id", listMingxingxiangmus.get(position)
						.getId());
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find, menu);
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
