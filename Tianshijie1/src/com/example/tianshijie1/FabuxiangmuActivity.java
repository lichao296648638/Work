package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tianshijie1.adapter.FabuAdapter;
import com.example.tianshijie1.adapter.MainlistAdapter;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class FabuxiangmuActivity extends Activity {
	private ListView lv_xiangmu;
	Mingxingxiangmu muMingxingxiangmu;
	List<Mingxingxiangmu> listMingxingxiangmus;

	private FabuAdapter fabuadapter;
	String result;
	Handler handler;
	private ImageView iv_backfabu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_fabuxiangmu);
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					fabuadapter = new FabuAdapter(FabuxiangmuActivity.this,
							listMingxingxiangmus, 1);
					lv_xiangmu.setAdapter(fabuadapter);

					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		listMingxingxiangmus = new ArrayList<Mingxingxiangmu>();
		lv_xiangmu = (ListView) findViewById(R.id.lv_xiangmu);
		iv_backfabu = (ImageView) findViewById(R.id.iv_backfabu);
		TextView emptyView = new TextView(FabuxiangmuActivity.this);
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("您还没有发布任何项目，快去发布吧！");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_xiangmu.getParent()).addView(emptyView);
		lv_xiangmu.setEmptyView(emptyView);
		iv_backfabu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lv_xiangmu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (listMingxingxiangmus.get(position).getStatus_val()
						.equals("众筹中")
						|| listMingxingxiangmus.get(position).getStatus_val()
								.equals("众筹完成")
						|| listMingxingxiangmus.get(position).getStatus_val()
								.equals("预热中")) {
					if (listMingxingxiangmus.get(position).getVersion()
							.equals("3")) {
						Intent intent = new Intent();
						intent.setClass(FabuxiangmuActivity.this,
								XiangmushitiActivity.class);
						intent.putExtra("id", listMingxingxiangmus
								.get(position).getId());
						startActivity(intent);
					} else {
						Intent intent = new Intent();
						intent.setClass(FabuxiangmuActivity.this,
								XiangqingFeishitiActivity.class);
						intent.putExtra("id", listMingxingxiangmus
								.get(position).getId());
						startActivity(intent);
					}
				}
			}

		});
		lv_xiangmu.setFocusable(false);

		new Thread() {
			public void run() {
				NameValuePair pair5 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				final List<NameValuePair> pairList2 = new ArrayList<NameValuePair>();
				pairList2.add(pair5);
				PostUtil postUtil2 = new PostUtil();
				String url2 = "http://wap.tianshijie.com.cn/appuser/myproject";
				result = postUtil2.DoPostNew(pairList2, url2);
				Log.v("url", "1" + result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					JSONArray jsonArray = jsonObject2.getJSONArray("myproject");

					for (int i = 0; i < jsonArray.length(); i++) {
						muMingxingxiangmu = new Mingxingxiangmu();
						JSONObject jsonObject3 = jsonArray.getJSONObject(i);
						muMingxingxiangmu.setImage(MainActivity.PJURl
								+ jsonObject3.getString("image"));
						muMingxingxiangmu.setCity_val(jsonObject3
								.getString("city_val"));
						muMingxingxiangmu.setCollect(jsonObject3
								.getString("collect"));
						muMingxingxiangmu.setId(jsonObject3.getString("id"));
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
						muMingxingxiangmu.setCopy_price(jsonObject3
								.getString("copy_price"));
						muMingxingxiangmu.setLoan_amount(jsonObject3
								.getString("loan_amount"));
						muMingxingxiangmu.setJindu(jsonObject3
								.getString("jindu"));
						muMingxingxiangmu.setVersion(jsonObject3
								.getString("version"));
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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fabuxiangmu, menu);
		return true;
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
