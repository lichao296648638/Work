package com.example.tianshijie1.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.tianshijie1.R;
import com.example.tianshijie1.XiangqingFeishitiActivity;

public class FragmentXiangmujieshao extends Fragment {
	public WebView wv_webview;
	private TextView tv_text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.webview, container, false);

		wv_webview = (WebView) view.findViewById(R.id.wv_webview);
		tv_text = (TextView) view.findViewById(R.id.tv_text);
		wv_webview.setFocusable(false);
		final String id = XiangqingFeishitiActivity.PID;
		String http = "http://wap.tianshijie.com.cn/appproject/info/pid/";
		String url = http + id;

		wv_webview.getSettings().setJavaScriptEnabled(true);
		wv_webview.setWebViewClient(new webViewClient());

		wv_webview.loadUrl(url);
		tv_text.setText("123");
		return view;

	}

	class webViewClient extends WebViewClient {

		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			Log.v("url", url);
			wv_webview.loadUrl(url);
			return true;

		}

		@Override
		public void onPageFinished(WebView view, String url) {
			// html加载完成之后，添加监听图片的点击js函数

			super.onPageFinished(view, url);

		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v("huahua", "fragmentX-->onDestroy()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("huahua", "fragmentX-->onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("huahua", "fragmentX-->onResume()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("huahua", "fragmentX-->onStart()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("huahua", "fragmentX-->onStop()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
