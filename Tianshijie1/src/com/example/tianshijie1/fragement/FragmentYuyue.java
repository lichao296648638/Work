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

import com.example.tianshijie1.FabuxiangmuActivity;
import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.adapter.XiangmuAdapter;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class FragmentYuyue extends Fragment {
	private View mMainView;
	private ListView lv_xiangmu;

	Mingxingxiangmu muMingxingxiangmu;
	List<Mingxingxiangmu> listMingxingxiangmus;
	private XiangmuAdapter xiangmuAdapter;
	String result;
	Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragmentyugou,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {

				case 1:
					xiangmuAdapter = new XiangmuAdapter(getActivity(),
							listMingxingxiangmus);
					lv_xiangmu.setAdapter(xiangmuAdapter);

					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	private void init() {
		// TODO Auto-generated method stub
		listMingxingxiangmus = new ArrayList<Mingxingxiangmu>();
		lv_xiangmu = (ListView) mMainView.findViewById(R.id.lv_xiangmu);
		lv_xiangmu.setFocusable(false);
		TextView emptyView = new TextView(getActivity());
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("您还没有预购任何项目");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_xiangmu.getParent()).addView(emptyView);
		lv_xiangmu.setEmptyView(emptyView);
		lv_xiangmu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), XiangqingFeishitiActivity.class);
				intent.putExtra("id", listMingxingxiangmus.get(position)
						.getId());
				startActivity(intent);
			}
		});
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				NameValuePair pair1 = new BasicNameValuePair("type", "1");
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				pairList.add(pair2);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appuser/myprojects";
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
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						muMingxingxiangmu = new Mingxingxiangmu();
						JSONObject jsonObject3 = jsonArray.getJSONObject(i);
						muMingxingxiangmu.setImage(MainActivity.PJURl
								+ jsonObject3.getString("image"));
						muMingxingxiangmu.setCity_val(jsonObject3
								.getString("city_val"));
						muMingxingxiangmu.setCollect(jsonObject3
								.getString("collect"));
						muMingxingxiangmu.setCopy_price(jsonObject3
								.getString("copy_price"));
						muMingxingxiangmu.setStatus_val(jsonObject3
								.getString("status_val"));
						muMingxingxiangmu
								.setName(jsonObject3.getString("name"));
						muMingxingxiangmu.setCity_val(jsonObject3
								.getString("city_val"));
						muMingxingxiangmu.setSy_time(jsonObject3
								.getString("sy_time"));
						muMingxingxiangmu.setCopy_number(jsonObject3
								.getString("copy_number"));
						muMingxingxiangmu.setLoan_amount(jsonObject3
								.getString("loan_amount"));
						muMingxingxiangmu.setJindu(jsonObject3
								.getString("jindu"));
						muMingxingxiangmu.setId(jsonObject3.getString("id"));
						muMingxingxiangmu.setCopy_number(jsonObject3
								.getString("fenshu"));
						muMingxingxiangmu.setZongjia(jsonObject3
								.getString("jine"));
						muMingxingxiangmu.setNotime(jsonObject3
								.getString("time"));
						muMingxingxiangmu.setSummary(jsonObject3
								.getString("summary"));
						listMingxingxiangmus.add(muMingxingxiangmu);
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
