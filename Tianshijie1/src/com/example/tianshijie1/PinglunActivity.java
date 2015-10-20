package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.adapter.PinglunAdapter;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Pinglun;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class PinglunActivity extends Activity {
	private TextView tv_xiangmu, tv_set;
	private ListView lv_pinglun;
	String xiangmu_name;
	Handler handler;
	String info;
	Pinglun pinglun;
	List<Pinglun> listPingluns;
	PinglunAdapter pinglunAdapter;
	private ImageView iv_backpinglun;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_pinglun);

		xiangmu_name = getIntent().getStringExtra("name");
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					pinglunAdapter = new PinglunAdapter(PinglunActivity.this,
							listPingluns);
					lv_pinglun.setAdapter(pinglunAdapter);
					pinglunAdapter.notifyDataSetChanged();
					break;
				case 2:
					View view = getWindow().peekDecorView();
					if (view != null) {
						InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						inputmanger.hideSoftInputFromWindow(
								view.getWindowToken(), 0);
					}
					Toast.makeText(PinglunActivity.this, info,
							Toast.LENGTH_LONG).show();
					listPingluns.clear();
					mypost();
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		listPingluns = new ArrayList<Pinglun>();
		tv_set = (TextView) findViewById(R.id.tv_set);
		tv_xiangmu = (TextView) findViewById(R.id.tv_xiangmu);
		iv_backpinglun = (ImageView) findViewById(R.id.iv_backpinglun);
		lv_pinglun = (ListView) findViewById(R.id.lv_pinglun);
		TextView emptyView = new TextView(PinglunActivity.this);
		iv_backpinglun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("暂时没有任何评论。");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_pinglun.getParent()).addView(emptyView);
		lv_pinglun.setEmptyView(emptyView);
		tv_xiangmu.setText(xiangmu_name);
		mypost();
		tv_set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog dlg = new AlertDialog.Builder(
						PinglunActivity.this).create();
				dlg.show();
				Window window = dlg.getWindow();
				window.setContentView(R.layout.activity_pinglun_shuru);
				dlg.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				final EditText content = (EditText) window
						.findViewById(R.id.et_pinglun);// 定义一个文本输入框
				content.requestFocus(); // edittext是一个EditText控件
				Timer timer = new Timer(); // 设置定时器
				timer.schedule(new TimerTask() {
					@Override
					public void run() { // 弹出软键盘的代码
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.showSoftInput(content,
								InputMethodManager.RESULT_SHOWN);
						imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
								InputMethodManager.HIDE_IMPLICIT_ONLY);
					}
				}, 300); // 设置300毫秒的时长
				final TextView hasnumTV = (TextView) window
						.findViewById(R.id.tv_current_zishu);// 用来显示剩余字数
				Log.v("sele", content.getSelectionStart() + "");
				Button btn_send = (Button) window.findViewById(R.id.btn_send);
				btn_send.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						new Thread() {
							public void run() {

								NameValuePair pair1 = new BasicNameValuePair(
										"pid", getIntent()
												.getStringExtra("pid"));
								NameValuePair pair2 = new BasicNameValuePair(
										"uid", LoginActivity.UID);
								NameValuePair pair3 = new BasicNameValuePair(
										"content", content.getText().toString());
								final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
								pairList.add(pair1);
								pairList.add(pair2);
								pairList.add(pair3);
								PostUtil postUtil = new PostUtil();
								String url1 = "http://wap.tianshijie.com.cn/appproject/ajaxAddComment";
								String result = postUtil.DoPostNew(pairList,
										url1);
								Log.v("url", "1" + result);
								try {
									JSONObject jsonObject = new JSONObject(
											result);
									info = jsonObject.getString("info");

									Message message = new Message();
									message.what = 2;
									handler.sendMessage(message);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}.start();
						dlg.dismiss();
					}

				});
				content.setSelection(content.getSelectionEnd());// 设置光标在最后
				// 下面为EditText文本框添加监听
				content.addTextChangedListener(new TextWatcher() {
					private CharSequence temp;
					private int selectionStart;
					private int selectionEnd;

					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {
					}

					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						temp = s;
					}

					public void afterTextChanged(Editable s) {
						int num = 250;// 限制的最大字数　
						hasnumTV.setText("" + s.length());
						selectionStart = content.getSelectionStart();
						selectionEnd = content.getSelectionEnd();
						if (temp.length() > num) {
							s.delete(selectionStart - 1, selectionEnd);
							int tempSelection = selectionEnd;
							content.setText(s);
							content.setSelection(tempSelection);// 设置光标在最后
						}
					}
				});

				dlg.setCanceledOnTouchOutside(true);
				dlg.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						View view = getWindow().peekDecorView();
						if (view != null) {
							InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							inputmanger.hideSoftInputFromWindow(
									view.getWindowToken(), 0);
						}
					}
				});
			}
		});
	}

	private void mypost() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {

				NameValuePair pair1 = new BasicNameValuePair("pid", getIntent()
						.getStringExtra("pid"));
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appproject/commentlist";
				String result = postUtil.DoPostNew(pairList, url1);
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					info = jsonObject.getString("info");
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					for (int i = 0; i < jsonObject2.length() - 1; i++) {
						pinglun = new Pinglun();
						JSONObject jsonObject3 = jsonObject2.getJSONObject(i
								+ "");
						pinglun.setUsername(jsonObject3.getString("username"));
						pinglun.setDateline(jsonObject3.getString("dateline"));
						pinglun.setContent(jsonObject3.getString("content"));
						pinglun.setAvatar(MainActivity.PJURl
								+ jsonObject3.getString("avatar"));
						pinglun.setPinglunxiangmu(xiangmu_name);
						listPingluns.add(pinglun);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pinglun, menu);
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
