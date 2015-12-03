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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.XiangmushitiActivity;
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.adapter.MainlistAdapter;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.fragement.fragment1.ReceiveBroadCast;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.LvHeightUtil;
import com.example.tianshijie1.util.PostUtil;

public class fragment3 extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener {
	private View mMainView;
	private ListView lv_xiangmu;
	private TextView tv_meiyou;
	private LinearLayout ll_meiyou;
	PullToRefreshView mPullToRefreshView;// 下拉
	Mingxingxiangmu muMingxingxiangmu;
	List<Mingxingxiangmu> listMingxingxiangmus;
	private MainlistAdapter mainlistAdapter;
	String result, get;
	Handler handler;
	int i = 1;
	private ReceiveBroadCast receiveBroadCast;
	/**
	 * Bug12Start
	 * Bug编号：Bug12
	 * Bug描述：点击首页科技进入的页面，上拉无反应
	 * 修复人：李超
	 * 修复时间：2015-12-02
	 */
	private int i_StartIndex = 0;
	private boolean b_Isinitdata = false;
	//Bug12End
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
					/**
					 * Bug12Start
					 * Bug编号：Bug12
					 * Bug描述：点击首页科技进入的页面，上拉无反应
					 * 修复人：李超
					 * 修复时间：2015-12-02
					 */
					if (!b_Isinitdata) {
						mainlistAdapter = new MainlistAdapter(getActivity(),
								listMingxingxiangmus, 0);
						lv_xiangmu.setAdapter(mainlistAdapter);
						b_Isinitdata = true;
					} else {
						mainlistAdapter.notifyDataSetInvalidated();
					}
					if (mainlistAdapter.getCount() <= 3) {
						lv_xiangmu.setSelection(mainlistAdapter.getCount() - 3);
					} else {
						lv_xiangmu.setSelection(mainlistAdapter.getCount() - 10);
					}
					mPullToRefreshView.onFooterRefreshComplete();
					LvHeightUtil.setListViewHeightBasedOnChildren(lv_xiangmu);
					//Bug12End
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
					intent.putExtra("id", listMingxingxiangmus.get(position)
							.getId());
					intent.putExtra("title", MainActivity.TITLE);
					startActivity(intent);
				}

			}
		});
		mypost(MainActivity.TITLE);

	}

	private void mypost(final String a) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();

				NameValuePair pair1 = new BasicNameValuePair("status", "600");
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);

				NameValuePair pair3 = new BasicNameValuePair("category", a);

				pairList.add(pair1);
				pairList.add(pair2);
				pairList.add(pair3);
				/**
				 * Bug12Start
				 * Bug编号：Bug12
				 * Bug描述：点击首页科技进入的页面，上拉无反应
				 * 修复人：李超
				 * 修复时间：2015-12-02
				 */
				NameValuePair pair5 = new BasicNameValuePair("start", String.valueOf(i_StartIndex));
				pairList.add(pair5);
				//Bug12End
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
				 * Bug描述：在有网络链接的情况下进入科技金融→右上角“行”按钮页面，然后此时切断所有网络连接，点击社区
				 * 			会因为没有网络获取不到json数据产生nullpointer
				 * 修复人：李超
				 * 修复日期：2015-10-22
				 */
				if(result == null){
					CToast.makeText(getActivity(), getResources().getText(R.string.toast_error_network), 3000).show();
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					/**
					 * Bug12Start
					 * Bug编号：Bug12
					 * Bug描述：点击首页科技进入的页面，上拉无反应
					 * 修复人：李超
					 * 修复时间：2015-12-02
					 */
					JSONArray jsonArray;
					if (jsonObject.get("data").equals("") || jsonObject.get("data") == null) {
						if (jsonObject.get("info").equals("暂无项目信息")) {
							if (listMingxingxiangmus.size() <= 0) {
								Message message = new Message();
								message.what = 2;
								handler.sendMessage(message);
							} else {
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						}
					}else{
						jsonArray = jsonObject.getJSONArray("data");
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
							i_StartIndex++;
						}

						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
					//Bug12End
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
		// TODO Auto-generated method stub

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
		Log.v("huahua", "fragment3-->onDestroy()");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v("huahua", "fragment3-->onPause()");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("huahua", "fragment3-->onResume()");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v("huahua", "fragment3-->onStart()");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v("huahua", "fragment3-->onStop()");
	}

}
