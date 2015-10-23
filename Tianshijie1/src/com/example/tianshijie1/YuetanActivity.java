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
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class YuetanActivity extends Activity {
	private Button btn_send;
	private EditText et_yuetan;
	private ImageView iv_backyuetan;
	private String uid, pid;
	Handler handler;
	String info;
	String result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_yuetan);
		uid = getIntent().getStringExtra("uid");
		pid = getIntent().getStringExtra("pid");
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (info.equals("约谈成功")) {
						Toast.makeText(YuetanActivity.this, info,
								Toast.LENGTH_LONG).show();
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
		btn_send = (Button) findViewById(R.id.btn_send);
		et_yuetan = (EditText) findViewById(R.id.et_yuetan);
		iv_backyuetan = (ImageView) findViewById(R.id.iv_backyuetan);
		et_yuetan.requestFocus();
		et_yuetan.requestFocus(); // edittext是一个EditText控件
		Timer timer = new Timer(); // 设置定时器
		timer.schedule(new TimerTask() {
			@Override
			public void run() { // 弹出软键盘的代码
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et_yuetan, InputMethodManager.RESULT_SHOWN);
				imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
						InputMethodManager.HIDE_IMPLICIT_ONLY);
			}
		}, 300); // 设置300毫秒的时长
		et_yuetan
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEND) {
							if (!et_yuetan.getText().toString().equals("")) {
								View view = getWindow().peekDecorView();
								if (view != null) {
									InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
									inputmanger.hideSoftInputFromWindow(
											view.getWindowToken(), 0);
								}
								if (!et_yuetan.getText().toString().equals("")) {
									new Thread() {
										public void run() {
											NameValuePair pair3 = new BasicNameValuePair(
													"content", et_yuetan
															.getText()
															.toString());
											NameValuePair pair1 = new BasicNameValuePair(
													"uid", LoginActivity.UID);
											NameValuePair pair2 = new BasicNameValuePair(
													"pid", pid);
											NameValuePair pair4 = new BasicNameValuePair(
													"touid", uid);
											final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
											pairList.add(pair3);
											pairList.add(pair1);
											pairList.add(pair2);
											pairList.add(pair4);
											PostUtil postUtil = new PostUtil();
											String url1 = "http://wap.tianshijie.com.cn/appuser/yuetan";
											result = postUtil.DoPostNew(
													pairList, url1);
											/**
											 * BugStart
											 * Bug编号：BUG4
											 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
											 * 修复人：李超
											 * 修复日期：2015-10-23
											 */
											if(result == null){
												CToast.makeText(YuetanActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
												return;
											}
											//BugEnd
											Log.v("url", "1" + result);
											try {
												JSONObject jsonObject = new JSONObject(
														result);
												info = jsonObject
														.getString("info");
												Message message = new Message();
												message.what = 1;
												handler.sendMessage(message);

											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

										}
									}.start();

								} else {
									Toast.makeText(YuetanActivity.this,
											"请填写要说的内容", Toast.LENGTH_LONG)
											.show();
								}
							}

							return true;
						}
						return false;
					}
				});
		iv_backyuetan.setOnClickListener(new OnClickListener() {

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
		btn_send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!et_yuetan.getText().toString().equals("")) {
					View view = getWindow().peekDecorView();
					if (view != null) {
						InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						inputmanger.hideSoftInputFromWindow(
								view.getWindowToken(), 0);
					}
					new Thread() {
						public void run() {
							NameValuePair pair3 = new BasicNameValuePair(
									"content", et_yuetan.getText().toString());
							NameValuePair pair1 = new BasicNameValuePair("uid",
									LoginActivity.UID);
							NameValuePair pair2 = new BasicNameValuePair("pid",
									pid);
							NameValuePair pair4 = new BasicNameValuePair(
									"touid", uid);

							final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
							pairList.add(pair3);
							pairList.add(pair1);
							pairList.add(pair2);
							pairList.add(pair4);
							PostUtil postUtil = new PostUtil();
							String url1 = "http://wap.tianshijie.com.cn/appuser/yuetan";
							result = postUtil.DoPostNew(pairList, url1);
							/**
							 * BugStart
							 * Bug编号：BUG4
							 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
							 * 修复人：李超
							 * 修复日期：2015-10-23
							 */
							if(result == null){
								CToast.makeText(YuetanActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
								return;
							}
							//BugEnd
							Log.v("url", "1" + result);

							try {
								JSONObject jsonObject = new JSONObject(result);
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
					Toast.makeText(YuetanActivity.this, "请填写要说的内容",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yuetan, menu);
		return true;
	}

	protected void onStart() {

		super.onStart();

		MyApplication mApp = (MyApplication) getApplication();

		if (mApp.isExit()) {

			finish();

		}

	}
}
