package com.example.tianshijie1.fragement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.adapter.TuanduiAdapter;
import com.example.tianshijie1.bean.Tuandui;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.LvHeightUtil;
import com.example.tianshijie1.util.PostUtil;

public class FragmentTuandui extends Fragment {
	private View mMainView;
	public ListView lv_xiangmu;

	Tuandui tuandui;
	List<Tuandui> listTuandui;
	private TuanduiAdapter tuanduiAdapter;
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
					tuanduiAdapter = new TuanduiAdapter(getActivity(),
							listTuandui);
					lv_xiangmu.setAdapter(tuanduiAdapter);
					LvHeightUtil.setListViewHeightBasedOnChildren(lv_xiangmu);
					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	private void init() {
		// TODO Auto-generated method stub
		listTuandui = new ArrayList<Tuandui>();
		lv_xiangmu = (ListView) mMainView.findViewById(R.id.lv_xiangmu);
		lv_xiangmu.setFocusable(false);
		TextView emptyView = new TextView(getActivity());
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("暂时还没有任何记录。");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_xiangmu.getParent()).addView(emptyView);
		lv_xiangmu.setEmptyView(emptyView);
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("pid",
						XiangqingFeishitiActivity.PID);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();

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
					JSONArray jsonArray2 = jsonObject3.getJSONArray("pro_team");
					for (int i = 0; i < jsonArray2.length(); i++) {
						tuandui = new Tuandui();
						JSONObject jsonObject4 = jsonArray2.getJSONObject(i);
						tuandui.setIv_touxiang(MainActivity.PJURl
								+ jsonObject4.getString("image"));
						tuandui.setName(jsonObject4.getString("name"));
						tuandui.setJianjie(jsonObject4.getString("intro"));
						tuandui.setZhiwei(jsonObject4.getString("post"));
						listTuandui.add(tuandui);
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
