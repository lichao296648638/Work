package com.example.tianshijie1.fragement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.XiangmushitiActivity;
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.adapter.MainlistAdapter;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.fragement.ContentFragment.ReceiveBroadCast;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class fragment1 extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener {
	private View mMainView;
	private ListView lv_xiangmu;
	private TextView tv_meiyou;
	private LinearLayout ll_meiyou;
	PullToRefreshView mPullToRefreshView;// 下拉
	Mingxingxiangmu muMingxingxiangmu;
	List<Mingxingxiangmu> listMingxingxiangmus;
	private MainlistAdapter mainlistAdapter;
	String result;
	Handler handler;
	String get;
	int i = 1;
	private ReceiveBroadCast receiveBroadCast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment1,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);
		/** 注册广播 */
		receiveBroadCast = new ReceiveBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("shuaxin2"); // 只有持有相同的action的接受者才能接收此广播
		getActivity().registerReceiver(receiveBroadCast, filter);
		LocalBroadcastManager broadcastManager = LocalBroadcastManager
				.getInstance(getActivity());
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("hangye");// 建议把它写一个公共的变量，这里方便阅读就不写了。
		BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				listMingxingxiangmus.clear();
				get = intent.getStringExtra("data");
				mypost(get);
				System.out.println(get);
			}
		};
		broadcastManager.registerReceiver(mItemViewListClickReceiver,
				intentFilter);

		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 1:
					ll_meiyou.setVisibility(View.VISIBLE);
					tv_meiyou.setVisibility(View.GONE);
					mainlistAdapter = new MainlistAdapter(getActivity(),
							listMingxingxiangmus, 0);
					lv_xiangmu.setAdapter(mainlistAdapter);
					mainlistAdapter.notifyDataSetInvalidated();
					break;
				case 2:
					ll_meiyou.setVisibility(View.GONE);
					tv_meiyou.setVisibility(View.VISIBLE);
					tv_meiyou.setText("暂时还没有您要的项目，换个条件吧。");
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
			MainActivity.TITLE = intent.getStringExtra("title");
			mypost(MainActivity.TITLE);

		}
	}

	private void init() {
		// TODO Auto-generated method stub
		listMingxingxiangmus = new ArrayList<Mingxingxiangmu>();
		lv_xiangmu = (ListView) mMainView.findViewById(R.id.lv_xiangmu);
		ll_meiyou = (LinearLayout) mMainView.findViewById(R.id.ll_meiyou);
		tv_meiyou = (TextView) mMainView.findViewById(R.id.tv_meiyou);
		lv_xiangmu.setFocusable(false);

		mPullToRefreshView = (PullToRefreshView) mMainView
				.findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		lv_xiangmu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (listMingxingxiangmus.get(position).getVersion().equals("3")) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), XiangmushitiActivity.class);
					intent.putExtra("id", listMingxingxiangmus.get(position)
							.getId());
					intent.putExtra("title", MainActivity.TITLE);
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(),
							XiangqingFeishitiActivity.class);
					intent.putExtra("title", MainActivity.TITLE);
					intent.putExtra("id", listMingxingxiangmus.get(position)
							.getId());
					startActivity(intent);
				}
			}
		});

		mypost(MainActivity.TITLE);
		Log.v("aaaa", MainActivity.TITLE);
	}

	private void mypost(final String a) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				NameValuePair pair1 = new BasicNameValuePair("status", "200");
				NameValuePair pair3 = new BasicNameValuePair("category", a);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				pairList.add(pair2);
				pairList.add(pair3);
				if (ContentFragment.cityid != null) {
					NameValuePair pair4 = new BasicNameValuePair("area",
							ContentFragment.cityid);
					pairList.add(pair4);
				}
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appproject/index";
				result = postUtil.DoPostNew(pairList, url1);
				/**
				 * BugStart
				 * Bug编号：BUG3
				 */
				if(result == null){
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONArray jsonArray = jsonObject.getJSONArray("data");

					for (int i = 0; i < jsonArray.length(); i++) {
						muMingxingxiangmu = new Mingxingxiangmu();
						JSONObject jsonObject2 = jsonArray.getJSONObject(i);
						muMingxingxiangmu.setImage(MainActivity.PJURl
								+ jsonObject2.getString("image"));
						muMingxingxiangmu.setCity_val(jsonObject2
								.getString("city_val"));
						muMingxingxiangmu.setCollect(jsonObject2
								.getString("collect"));
						muMingxingxiangmu.setCopy_price(jsonObject2
								.getString("copy_price"));
						muMingxingxiangmu.setStatus_val(jsonObject2
								.getString("status_val"));
						muMingxingxiangmu
								.setName(jsonObject2.getString("name"));
						muMingxingxiangmu.setCity_val(jsonObject2
								.getString("city_val"));
						muMingxingxiangmu.setSy_time(jsonObject2
								.getString("sy_time"));
						muMingxingxiangmu.setCopy_number(jsonObject2
								.getString("copy_number"));
						muMingxingxiangmu.setLoan_amount(jsonObject2
								.getString("loan_amount"));
						muMingxingxiangmu.setJindu(jsonObject2
								.getString("jindu"));
						muMingxingxiangmu.setId(jsonObject2.getString("id"));
						muMingxingxiangmu.setVersion(jsonObject2
								.getString("version"));
						muMingxingxiangmu.setIs_sc(jsonObject2
								.getString("is_sc"));
						muMingxingxiangmu.setSummary(jsonObject2
								.getString("summary"));
						listMingxingxiangmus.add(muMingxingxiangmu);
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.v("2", "2");
					Message message = new Message();
					message.what = 2;
					handler.sendMessage(message);
					e.printStackTrace();
				}

			}
		}.start();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);

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
