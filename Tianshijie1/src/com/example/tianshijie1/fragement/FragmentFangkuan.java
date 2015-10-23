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
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.adapter.FangkuanAdapter;
import com.example.tianshijie1.bean.Zijin;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class FragmentFangkuan extends Fragment {
	private View mMainView;
	private ListView lv_fangkuan;
	Zijin zijin;
	List<Zijin> listzijin;
	private FangkuanAdapter fangkuanAdapter;
	String result;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fangkuanqueren,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);

		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				
				case 1:
					fangkuanAdapter = new FangkuanAdapter(getActivity(),
							listzijin);
					lv_fangkuan.setAdapter(fangkuanAdapter);
					fangkuanAdapter.notifyDataSetInvalidated();
					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	private void init() {
		// TODO Auto-generated method stub
		listzijin = new ArrayList<Zijin>();
		lv_fangkuan = (ListView) mMainView.findViewById(R.id.lv_fangkuan);
		lv_fangkuan.setFocusable(false);
		TextView emptyView = new TextView(getActivity());
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("暂时还没有任何记录。");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_fangkuan.getParent()).addView(emptyView);
		lv_fangkuan.setEmptyView(emptyView);
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
				String url1 = "http://wap.tianshijie.com.cn/appuser/tocptransactionlist";
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
					JSONArray josnArray = jsonObject.getJSONArray("data");

					for (int i = 0; i < josnArray.length(); i++) {
						zijin = new Zijin();
						JSONObject jsonObject2 = josnArray.getJSONObject(i);
						zijin.setXiangmu(jsonObject2.getString("name"));
						zijin.setShijian(jsonObject2.getString("dateline"));
						zijin.setJine(jsonObject2.getString("s_amount"));
						zijin.setCid(jsonObject2.getString("id"));
						/**
						 * 功能添加Start 需求编号XQ1
						 */
						zijin.setAgree_fangkuan(jsonObject2.getString("agree_fangkuan"));
						//功能添加End
						String status = jsonObject2.getString("status");

						if (status.equals("200")) {
							zijin.setZhuangtia("预热");
						} else if (status.equals("500")) {
							zijin.setZhuangtia("成功");
						} else if (status.equals("600")) {
							zijin.setZhuangtia("完成");
						} else if (status.equals("700")) {
							zijin.setZhuangtia("失败");
						}
						zijin.setStatus(status);
						zijin.setT_status(jsonObject2.getString("t_status"));
						zijin.setLoanstatus(jsonObject2.getString("loanstatus"));
						zijin.setPid(jsonObject2.getString("pid"));
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
