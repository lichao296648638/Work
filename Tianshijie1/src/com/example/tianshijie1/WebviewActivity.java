package com.example.tianshijie1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tianshijie1.application.MyApplication;
import com.umeng.analytics.MobclickAgent;

public class WebviewActivity extends Activity {
	private WebView wv_myweb;
	private ImageView iv_backwebview;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_webview);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		wv_myweb = new WebView(this);
		wv_myweb.requestFocus();// 这两行要放到findid之前
		wv_myweb = (WebView) findViewById(R.id.wv_myweb);
		iv_backwebview = (ImageView) findViewById(R.id.iv_backwebview);
		tv_title = (TextView) findViewById(R.id.tv_title);
		iv_backwebview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		final Intent intent = getIntent();
		final String url = intent.getStringExtra("url");
		String title = intent.getStringExtra("title");
		tv_title.setText(title);
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
			if (url.equals("tel:4006600008")) {

				Log.v("url", url);
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			} else {
				wv_myweb.loadUrl(url);
			}

			return true;

		}

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