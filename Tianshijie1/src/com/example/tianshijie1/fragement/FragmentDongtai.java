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
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.adapter.DongtaiAdapter;
import com.example.tianshijie1.bean.MyMessage;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.LvHeightUtil;
import com.example.tianshijie1.util.PostUtil;

public class FragmentDongtai extends Fragment {
	private View mMainView;
	private ListView lv_xiangmu;
	PullToRefreshView mPullToRefreshView;// 下拉
	MyMessage myMessage;
	List<MyMessage> listmymessages;
	private DongtaiAdapter dongtaiAdapter;
	String result;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment_tuandui,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 1:
					dongtaiAdapter = new DongtaiAdapter(getActivity(),
							listmymessages);
					lv_xiangmu.setAdapter(dongtaiAdapter);
					LvHeightUtil.setListViewHeightBasedOnChildren(lv_xiangmu);
					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	private void init() {
		// TODO Auto-generated method stub
		listmymessages = new ArrayList<MyMessage>();
		lv_xiangmu = (ListView) mMainView.findViewById(R.id.lv_xiangmu);
		lv_xiangmu.setFocusable(false);
		TextView emptyView = new TextView(getActivity());
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER_HORIZONTAL);
		emptyView.setText("该项目暂时还没有任何动态。");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_xiangmu.getParent()).addView(emptyView);
		lv_xiangmu.setEmptyView(emptyView);
		mPullToRefreshView = (PullToRefreshView) mMainView
				.findViewById(R.id.main_pull_refresh_view);

		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("pid",
						XiangqingFeishitiActivity.PID);
				NameValuePair pair1 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				pairList.add(pair2);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appproject/detail";
				result = postUtil.DoPostNew(pairList, url1);
				/**
				 * BugStart
				 * Bug编号：BUG4
				 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
				 * 修复人：李超
				 * 修复日期：2015-10-23
				 */
				if(result == null){
					CToast.makeText(getActivity(), getActivity().getResources().getText(R.string.toast_error_network), 3000).show();
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					JSONArray jsonArray = jsonObject2.getJSONArray("project");
					JSONObject jsonObject3 = jsonArray.getJSONObject(0);
					JSONArray jsonArray2 = jsonObject3.getJSONArray("dongtai");
					for (int i = 0; i < jsonArray2.length(); i++) {
						myMessage = new MyMessage();
						JSONObject jsonObject4 = jsonArray2.getJSONObject(i);
						myMessage.setMsgfrom(jsonObject4.getString("content"));
						myMessage.setDateline(jsonObject4.getString("addtime"));

						listmymessages.add(myMessage);
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
