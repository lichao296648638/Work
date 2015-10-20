package com.example.tianshijie1;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class WebYibaoActivity extends Activity {
	private WebView wv_myweb;
	String result, info;
	Handler handler;
	int web = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_web_yibao);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					if (info.equals("您已经同步了易宝，无需重复同步")) {
						Toast.makeText(WebYibaoActivity.this,
								"您已经同步了易宝，无需重复同步。", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(WebYibaoActivity.this,
								ZhanghuguanliActivity.class);
						startActivity(intent);
						finish();
					} else if (info.equals("请先完善资料并通过认证")) {
						Toast.makeText(WebYibaoActivity.this, "请先完善资料并通过认证",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(WebYibaoActivity.this,
								MineXinxiActivity.class);
						startActivity(intent);
					}
					break;
				case 3:
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		wv_myweb = new WebView(this);
		wv_myweb.requestFocus();// 这两行要放到findid之前
		wv_myweb = (WebView) findViewById(R.id.wv_myweb);
		final Intent intent = getIntent();
		final String url = intent.getStringExtra("url");
		Log.v("url", url);
		WebSettings webSettings = wv_myweb.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		wv_myweb.setWebViewClient(new webViewClient());
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		// 加载需要显示的网页
		wv_myweb.loadUrl(url);
	}

	class webViewClient extends WebViewClient {

		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			Log.v("url", url);
			wv_myweb.loadUrl(url);
			return true;

		}
	}

	private void mypost() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				String url1 = "http://wap.tianshijie.com.cn/appuser/withdraw/uid/";
				String amount = "/amount/";
				String qianshu = "1";
				HttpGet httpRequest = new HttpGet(url1 + LoginActivity.UID
						+ amount + qianshu);

				/* 发送请求并等待响应 */
				HttpResponse httpResponse;
				try {
					httpResponse = new DefaultHttpClient().execute(httpRequest);
					/* 若状态码为200 ok */
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						/* 读 */
						result = EntityUtils.toString(httpResponse.getEntity());
						Log.v("result", result);
					}
					JSONObject jsonObject = new JSONObject(result);
					info = jsonObject.getString("info");
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Intent intent = new Intent();
					intent.setClass(WebYibaoActivity.this,
							ZhanghuguanliActivity.class);
					startActivity(intent);
					finish();
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
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
