package com.example.tianshijie1.fragement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.adapter.ZijinAdapter;
import com.example.tianshijie1.bean.Zijin;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.PostUtil;

public class FragmentZijin extends Fragment {
	private View mMainView;
	private ListView lv_zijin;
	PullToRefreshView mPullToRefreshView;// 下拉
	Zijin zijin;
	List<Zijin> listzijin;
	private ZijinAdapter zijinAdapter;
	String result;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.zijinjilu,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);

		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 1:
					zijinAdapter = new ZijinAdapter(getActivity(), listzijin);
					lv_zijin.setAdapter(zijinAdapter);
					zijinAdapter.notifyDataSetInvalidated();
					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	private void init() {
		// TODO Auto-generated method stub
		listzijin = new ArrayList<Zijin>();
		lv_zijin = (ListView) mMainView.findViewById(R.id.lv_zijin);
		lv_zijin.setFocusable(false);
		TextView emptyView = new TextView(getActivity());
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("暂时还没有任何记录。");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_zijin.getParent()).addView(emptyView);
		lv_zijin.setEmptyView(emptyView);
		mPullToRefreshView = (PullToRefreshView) mMainView
				.findViewById(R.id.main_pull_refresh_view);

		mypost();
	}

	private void mypost() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);

				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair2);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appuser/myaccount";
				result = postUtil.DoPostNew(pairList, url1);
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONArray josnArray = jsonObject.getJSONArray("data");

					for (int i = 0; i < josnArray.length(); i++) {
						zijin = new Zijin();
						JSONObject jsonObject2 = josnArray.getJSONObject(i);

						zijin.setXuhao(jsonObject2.getString("id"));
						zijin.setShijian(jsonObject2
								.getString("operation_time"));
						zijin.setJine(jsonObject2.getString("amount"));
						zijin.setCaozuo(jsonObject2.getString("description"));
						listzijin.add(zijin);
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
		Log.v("huahua", "fragment1-->onDestroy()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("huahua", "fragment1-->onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("huahua", "fragment1-->onResume()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("huahua", "fragment1-->onStart()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("huahua", "fragment1-->onStop()");
	}

}
