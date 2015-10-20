package com.example.tianshijie1.fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.tianshijie1.R;
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.fragement.FragmentXiangmujieshao.webViewClient;

public class FragmentHexinjingzhengli extends Fragment {
	private View mMainView;
	public WebView wv_webview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.webview,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);
		wv_webview = new WebView(getActivity());

		wv_webview.requestFocus();// 这两行要放到findid之前
		wv_webview = (WebView) mMainView.findViewById(R.id.wv_webview);
		wv_webview.setFocusable(true);
		final String id = XiangqingFeishitiActivity.PID;
		String http = "http://wap.tianshijie.com.cn/appproject/core/pid/";
		String url = http + id;

		Log.v("url", url);
		WebSettings webSettings = wv_webview.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		wv_webview.setWebViewClient(new webViewClient());
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置支持缩放
		webSettings.setBuiltInZoomControls(true);
		// 加载需要显示的网页
		wv_webview.loadUrl(url);

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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("huahua", "fragment1-->onCreateView()");

		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();

		}

		return mMainView;
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

}
