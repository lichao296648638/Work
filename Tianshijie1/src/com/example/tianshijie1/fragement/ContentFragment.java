package com.example.tianshijie1.fragement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tianshijie1.CityChoiseActivity;
import com.example.tianshijie1.FindActivity;
import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.MineguanzhuActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.WebviewActivity;
import com.example.tianshijie1.XiangmuListActivity;
import com.example.tianshijie1.XiangmushitiActivity;
import com.example.tianshijie1.XiangqingFeishitiActivity;
import com.example.tianshijie1.adapter.MainlistAdapter;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.lunbo.GuideGallery;
import com.example.tianshijie1.lunbo.ImageAdapter;
import com.example.tianshijie1.shangla.PullToRefreshView;
import com.example.tianshijie1.shangla.PullToRefreshView.OnFooterRefreshListener;
import com.example.tianshijie1.shangla.PullToRefreshView.OnHeaderRefreshListener;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.CustomProgressDialog;
import com.example.tianshijie1.util.Jindu;
import com.example.tianshijie1.util.LvHeightUtil;
import com.example.tianshijie1.util.PostUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment implements
		OnHeaderRefreshListener, OnFooterRefreshListener {

	/**
	 * 一堆为了实现轮播的参数
	 */
	RequestQueue mQueue;
	public boolean timeFlag = true;
	public ImageTimerTask timeTaks = null;
	public GuideGallery images_gallery;
	private int gallerypisition = 0;
	private static int positon = 0;
	Handler autoGalleryHandler, myHandler;
	private Thread timeThread = null;
	private boolean isExit = false;
	public ArrayList<String> lunbo_image;
	public ArrayList<String> lunbo_url;
	Mingxingxiangmu muMingxingxiangmu;
	Jindu jindu, jindu2;
	List<Mingxingxiangmu> listMingxingxiangmus;
	List<Mingxingxiangmu> listMingxingxiangmustwo;
	List<Mingxingxiangmu> listMingxingxiangmus2;
	private MainlistAdapter mainlistAdapter;
	Timer autoGallery;
	public boolean autoGallerybe;
	Editor editoram;
	/**
	 * mianactivity 中的一堆控件
	 * 
	 */
	private ImageView iv_rongzizhuangtai, iv_collect, iv_banner, iv_find;
	private LinearLayout ll_mingxing, ll_xiaofei, ll_keji, ll_xinsanban,
			ll_collect, ll_mineguanzhu;
	private RelativeLayout rl_collect;
	private TextView tv_collectnum, tv_name, tv_location, tv_sytime, tv_qitou,
			tv_mubiao;
	private ImageView iv_rongzizhuangtai2, iv_collect2, iv_banner2;
	public ImageView title_bar_menu;
	private LinearLayout ll_mingxing2;
	private RelativeLayout rl_collect2, rl_mingxing2;
	private TextView tv_collectnum2, tv_name2, tv_location2, tv_sytime2,
			tv_qitou2, tv_mubiao2, tv_citychoise;
	private ListView lv_remen;
	int biaoji;
	Handler handler;
	String url;
	String result;

	int ivcollectadap = 0;
	public static String cityid;
	String cityname;
	int j = 0;
	PullToRefreshView mPullToRefreshView;// 下拉
	SharedPreferences sharedPreferencesam;
	/**
	 * 在家两个ImageLoader
	 */
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	static View view;
	private CustomProgressDialog progressDialog = null;
	private ReceiveBroadCast receiveBroadCast;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_content, container, false);
		mQueue = Volley.newRequestQueue(getActivity());
		progressDialog = CustomProgressDialog.createDialog(getActivity());
		progressDialog.show();
		progressDialog.setCancelable(false);// 设置进度条是否可以按退回键取消
		/** 注册广播 */
		receiveBroadCast = new ReceiveBroadCast();
		IntentFilter filter = new IntentFilter();
		filter.addAction("shuaxin"); // 只有持有相同的action的接受者才能接收此广播
		filter.addAction("shuaxinlist"); // 只有持有相同的action的接受者才能接收此广播
		getActivity().registerReceiver(receiveBroadCast, filter);
		url = "http://wap.tianshijie.com.cn/appindex/index";
		images_gallery = (GuideGallery) view
				.findViewById(R.id.image_wall_gallery);
		listMingxingxiangmus = new ArrayList<Mingxingxiangmu>();
		listMingxingxiangmustwo = new ArrayList<Mingxingxiangmu>();
		listMingxingxiangmus2 = new ArrayList<Mingxingxiangmu>();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_background)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_background)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_background)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(0, false)).build();
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(LoginActivity.PHONE, Context.MODE_PRIVATE);
		sharedPreferencesam = getActivity().getSharedPreferences(
				LoginActivity.PHONE, Activity.MODE_PRIVATE);
		LoginActivity.UID = sharedPreferences.getString("id", "null");
		editoram = sharedPreferencesam.edit();
		MainActivity.REALNAME = sharedPreferences.getString("realname", "null");
		MainActivity.INVEST_TYPEi = sharedPreferences.getString("invest_type",
				"-1");
		MainActivity.UNREADMESS = sharedPreferences.getString("unreadmess",
				"-1");
		MainActivity.AVATAR = sharedPreferences.getString("avatar", "-1");
		LoginActivity.USERNAME = sharedPreferences.getString("username", "-1");
		LoginActivity.PASSWORD = sharedPreferences.getString("password", "-1");
		init();
		lunbo();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					muMingxingxiangmu = listMingxingxiangmus.get(0);
					Log.v("123", muMingxingxiangmu.getName());
					tv_collectnum.setText(muMingxingxiangmu.getCollect());
					tv_name.setText(muMingxingxiangmu.getName());
					tv_location.setText(muMingxingxiangmu.getCity_val());
					tv_sytime.setText(muMingxingxiangmu.getSy_time() + "天");
					if (muMingxingxiangmu.getIs_sc().equals("1")) {
						iv_collect
								.setBackgroundResource(R.drawable.collect_xuanzhong);

					} else {
						iv_collect
								.setBackgroundResource(R.drawable.xin_wxz_bai);
					}

					tv_qitou.setText(muMingxingxiangmu.getCopy_price() + "万");
					tv_mubiao.setText(muMingxingxiangmu.getLoan_amount() + "万");
					ImageLoader imageLoader = ImageLoader.getInstance();
					imageLoader.displayImage(muMingxingxiangmu.getBanner(),
							iv_banner, options, animateFirstListener);
					Log.v("mu", muMingxingxiangmu.getStatus_val());
					if (muMingxingxiangmu.getStatus_val().equals("众筹中")) {
						iv_rongzizhuangtai
								.setBackgroundResource(R.drawable.rongzhong);
					}
					if (muMingxingxiangmu.getStatus_val().equals("众筹完成")) {
						iv_rongzizhuangtai
								.setBackgroundResource(R.drawable.rongcheng);
					}
					if (lunbo_image.size() != 0) {

						ImageAdapter imageAdapter = new ImageAdapter(
								lunbo_image, getActivity());
						images_gallery.setAdapter(imageAdapter);
						LinearLayout pointLinear = (LinearLayout) view
								.findViewById(R.id.gallery_point_linear);
						pointLinear.setBackgroundColor(Color
								.parseColor("#00000000"));

						if (j == 0) {

							for (int i = 0; i < lunbo_image.size(); i++) {

								ImageView pointView = null;
								pointView = new ImageView(getActivity());

								if (pointView != null) {
									if (i == 0) {
										pointView
												.setBackgroundResource(R.drawable.feature_point_cur);
									} else
										pointView
												.setBackgroundResource(R.drawable.feature_point);

								}
								pointLinear.addView(pointView);
							}
						} else {
							images_gallery
									.setBackgroundResource(R.drawable.zhanwei_background);
						}
						j++;
					}
					muMingxingxiangmu = listMingxingxiangmustwo.get(0);
					Log.v("123", muMingxingxiangmu.getName());
					tv_collectnum2.setText(muMingxingxiangmu.getCollect());
					tv_name2.setText(muMingxingxiangmu.getName());
					tv_location2.setText(muMingxingxiangmu.getCity_val());
					tv_sytime2.setText(muMingxingxiangmu.getSy_time() + "天");

					if (muMingxingxiangmu.getIs_sc().equals("1")) {
						iv_collect2
								.setBackgroundResource(R.drawable.collect_xuanzhong);

					} else {
						iv_collect2
								.setBackgroundResource(R.drawable.xin_wxz_bai);

					}

					tv_qitou2.setText(muMingxingxiangmu.getCopy_price() + "万");
					tv_mubiao2
							.setText(muMingxingxiangmu.getLoan_amount() + "万");
					ImageLoader imageLoader2 = ImageLoader.getInstance();
					imageLoader2.displayImage(muMingxingxiangmu.getBanner(),
							iv_banner2, options, animateFirstListener);
					Log.v("mu", muMingxingxiangmu.getStatus_val());
					if (muMingxingxiangmu.getStatus_val().equals("众筹中")) {
						iv_rongzizhuangtai2
								.setBackgroundResource(R.drawable.rongzhong);
					}
					if (muMingxingxiangmu.getStatus_val().equals("众筹完成")) {
						iv_rongzizhuangtai2
								.setBackgroundResource(R.drawable.rongcheng);
					}

					mainlistAdapter = new MainlistAdapter(getActivity(),
							listMingxingxiangmus2, 0, 1);
					lv_remen.setAdapter(mainlistAdapter);
					LvHeightUtil.setListViewHeightBasedOnChildren(lv_remen);
					progressDialog.dismiss();
					break;
				case 3:
					mainlistAdapter = new MainlistAdapter(getActivity(),
							listMingxingxiangmus2, 0, 1);
					lv_remen.setAdapter(mainlistAdapter);
					LvHeightUtil.setListViewHeightBasedOnChildren(lv_remen);

					if (mainlistAdapter.getCount() <= 3) {
						lv_remen.setSelection(mainlistAdapter.getCount() - 3);
					} else {
						lv_remen.setSelection(mainlistAdapter.getCount() - 6);
					}
					break;
				case 4:

					break;
				}
				super.handleMessage(msg);
			}

		};
		myHandler = new Handler() {
			public void handleMessage(Message msg2) {
				switch (msg2.what) {
				case 2:
					int tvjindu = Integer.parseInt(listMingxingxiangmus.get(0)
							.getJindu());
					Log.v("tvjindu", tvjindu + "");
					jindu.TVJindu(tvjindu);
					int tvjindu2 = Integer.parseInt(listMingxingxiangmustwo
							.get(0).getJindu());
					Log.v("tvjindu", tvjindu + "");
					jindu2.TVJindu(tvjindu2);
					break;
				}
				super.handleMessage(msg2);
			}
		};
		return view;
	}

	private void lunbo() {
		// TODO Auto-generated method stub
		timeTaks = new ImageTimerTask();
		autoGallery = new Timer();
		autoGallerybe = true;
		if (autoGallerybe) {
			autoGallery.scheduleAtFixedRate(timeTaks, 4000, 4000);
		}

		autoGalleryHandler = new Handler() {
			public void handleMessage(Message message) {
				super.handleMessage(message);
				switch (message.what) {
				case 1:
					images_gallery
							.setSelection(message.getData().getInt("pos"));
					break;
				}
			}
		};
		timeThread = new Thread() {
			public void run() {
				while (!isExit) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (timeTaks) {
						if (!timeFlag) {
							timeTaks.timeCondition = true;
							timeTaks.notifyAll();
						}
					}
					timeFlag = true;
				}
			};
		};
		timeThread.start();

	}

	class ReceiveBroadCast extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String pid = intent.getStringExtra("pid");
			Log.v("pid", intent.getStringExtra("pid"));
			if (intent.getAction().equals("shuaxin")) {
				// 得到广播中得到的数据，并显示出来
				if (pid.equals(listMingxingxiangmus.get(0).getId())) {
					if (listMingxingxiangmus.get(0).getIs_sc().equals("1")) {
						iv_collect
								.setBackgroundResource(R.drawable.xin_wxz_bai);
						int tvcollectnum = Integer.parseInt(tv_collectnum
								.getText().toString());
						listMingxingxiangmus.get(0).setIs_sc("0");
						tv_collectnum.setText(tvcollectnum - 1 + "");

					} else {
						iv_collect
								.setBackgroundResource(R.drawable.collect_xuanzhong);
						int tvcollectnum = Integer.parseInt(tv_collectnum
								.getText().toString());
						listMingxingxiangmus.get(0).setIs_sc("1");
						tv_collectnum.setText(tvcollectnum + 1 + "");

					}
				}
				if (pid.equals(listMingxingxiangmustwo.get(0).getId())) {
					if (listMingxingxiangmustwo.get(0).getIs_sc().equals("1")) {
						iv_collect2
								.setBackgroundResource(R.drawable.xin_wxz_bai);
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						listMingxingxiangmustwo.get(0).setIs_sc("0");
						tv_collectnum2.setText(tvcollectnum - 1 + "");

					} else {
						iv_collect2
								.setBackgroundResource(R.drawable.collect_xuanzhong);
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						listMingxingxiangmustwo.get(0).setIs_sc("1");
						tv_collectnum2.setText(tvcollectnum + 1 + "");

					}
				}
				for (int i = 0; i < listMingxingxiangmus2.size(); i++) {
					if (pid.equals(listMingxingxiangmus2.get(i).getId())) {

						if (listMingxingxiangmus2.get(i).getIs_sc().equals("0")) {
							listMingxingxiangmus2.get(i).setIs_sc("1");
							int tvcollectnum = Integer
									.parseInt(listMingxingxiangmus2.get(i)
											.getCollect());
							listMingxingxiangmus2.get(i).setCollect(
									tvcollectnum + 1 + "");
						} else {
							listMingxingxiangmus2.get(i).setIs_sc("0");
							int tvcollectnum = Integer
									.parseInt(listMingxingxiangmus2.get(i)
											.getCollect());
							listMingxingxiangmus2.get(i).setCollect(
									tvcollectnum - 1 + "");
						}
						mainlistAdapter.notifyDataSetChanged();
					}
				}

			} else {
				if (pid.equals(listMingxingxiangmus.get(0).getId())) {
					if (listMingxingxiangmus.get(0).getIs_sc().equals("1")) {
						iv_collect
								.setBackgroundResource(R.drawable.xin_wxz_bai);
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						tv_collectnum.setText(tvcollectnum - 1 + "");
						listMingxingxiangmus.get(0).setIs_sc("0");

					} else {
						iv_collect
								.setBackgroundResource(R.drawable.collect_xuanzhong);
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						tv_collectnum.setText(tvcollectnum + 1 + "");
						listMingxingxiangmus.get(0).setIs_sc("1");

					}
				}
				if (pid.equals(listMingxingxiangmustwo.get(0).getId())) {

					if (listMingxingxiangmustwo.get(0).getIs_sc().equals("1")) {
						iv_collect2
								.setBackgroundResource(R.drawable.xin_wxz_bai);
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						tv_collectnum2.setText(tvcollectnum - 1 + "");
						listMingxingxiangmustwo.get(0).setIs_sc("0");

					} else {
						iv_collect2
								.setBackgroundResource(R.drawable.collect_xuanzhong);
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						tv_collectnum2.setText(tvcollectnum + 1 + "");
						listMingxingxiangmustwo.get(0).setIs_sc("1");

					}
				}
			}

		}
	}

	/**
	 * 注销广播
	 * */
	@Override
	public void onDestroyView() {
		// getActivity().unregisterReceiver(receiveBroadCast);
		super.onDestroyView();
	}

	private void init() {
		// TODO Auto-generated method stub
		iv_rongzizhuangtai = (ImageView) view
				.findViewById(R.id.iv_rongzizhuangtai);
		ll_mingxing = (LinearLayout) view.findViewById(R.id.ll_mingxing);
		rl_collect = (RelativeLayout) view.findViewById(R.id.rl_collect);
		iv_collect = (ImageView) view.findViewById(R.id.iv_collect);
		ll_collect = (LinearLayout) view.findViewById(R.id.ll_collect);
		iv_banner = (ImageView) view.findViewById(R.id.iv_banner);
		iv_find = (ImageView) view.findViewById(R.id.iv_find);
		title_bar_menu = (ImageView) view.findViewById(R.id.title_bar_menu);
		tv_collectnum = (TextView) view.findViewById(R.id.tv_collectnum);
		tv_sytime = (TextView) view.findViewById(R.id.tv_sytime);
		tv_qitou = (TextView) view.findViewById(R.id.tv_qitou);
		tv_mubiao = (TextView) view.findViewById(R.id.tv_mubiao);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		lv_remen = (ListView) view.findViewById(R.id.lv_remen);
		lv_remen.setFocusable(false);
		tv_location = (TextView) view.findViewById(R.id.tv_location);
		iv_rongzizhuangtai2 = (ImageView) view
				.findViewById(R.id.iv_rongzizhuangtai2);
		rl_mingxing2 = (RelativeLayout) view.findViewById(R.id.rl_mingxing2);
		jindu = (Jindu) view.findViewById(R.id.jindu);
		jindu2 = (Jindu) view.findViewById(R.id.jindu2);
		rl_collect2 = (RelativeLayout) view.findViewById(R.id.rl_collect2);
		iv_collect2 = (ImageView) view.findViewById(R.id.iv_collect2);
		iv_banner2 = (ImageView) view.findViewById(R.id.iv_banner2);
		tv_collectnum2 = (TextView) view.findViewById(R.id.tv_collectnum2);
		tv_sytime2 = (TextView) view.findViewById(R.id.tv_sytime2);
		tv_qitou2 = (TextView) view.findViewById(R.id.tv_qitou2);
		tv_mubiao2 = (TextView) view.findViewById(R.id.tv_mubiao2);
		tv_citychoise = (TextView) view.findViewById(R.id.tv_citychoise);
		tv_name2 = (TextView) view.findViewById(R.id.tv_name2);
		ll_xiaofei = (LinearLayout) view.findViewById(R.id.ll_xiaofei);
		ll_keji = (LinearLayout) view.findViewById(R.id.ll_keji);
		ll_xinsanban = (LinearLayout) view.findViewById(R.id.ll_xinsanban);
		ll_mineguanzhu = (LinearLayout) view.findViewById(R.id.ll_mineguanzhu);
		tv_location2 = (TextView) view.findViewById(R.id.tv_location2);
		mPullToRefreshView = (PullToRefreshView) view
				.findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		images_gallery = (GuideGallery) view
				.findViewById(R.id.image_wall_gallery);
		images_gallery.setImageActivity(this);
		cityid = getActivity().getIntent().getStringExtra("city");
		cityname = getActivity().getIntent().getStringExtra("cityname");
		if (cityname != null) {
			tv_citychoise.setText(cityname);
		} else {
			tv_citychoise.setText("全国");
		}

		title_bar_menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("open");
				// intent.putExtra("position", position);
				LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
						intent);
			}
		});
		images_gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				int a = position % 3;
				Intent intent = new Intent();
				intent.setClass(getActivity(), WebviewActivity.class);
				intent.putExtra("title", "专题");
				intent.putExtra("url", lunbo_url.get(a));
				startActivity(intent);

			}
		});
		tv_citychoise.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), CityChoiseActivity.class);
				startActivity(intent);
			}
		});
		iv_banner.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listMingxingxiangmus.get(0).getVersion().equals("3")) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), XiangmushitiActivity.class);
					intent.putExtra("id", listMingxingxiangmus.get(0).getId());
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(),
							XiangqingFeishitiActivity.class);
					intent.putExtra("id", listMingxingxiangmus.get(0).getId());
					startActivity(intent);
				}
			}
		});
		iv_banner2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (listMingxingxiangmustwo.get(0).getVersion().equals("3")) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), XiangmushitiActivity.class);
					intent.putExtra("id", listMingxingxiangmustwo.get(0)
							.getId());
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(),
							XiangqingFeishitiActivity.class);
					intent.putExtra("id", listMingxingxiangmustwo.get(0)
							.getId());
					startActivity(intent);
				}
			}
		});
		lv_remen.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (listMingxingxiangmus2.get(position).getVersion()
						.equals("3")) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), XiangmushitiActivity.class);
					intent.putExtra("id", listMingxingxiangmus2.get(position)
							.getId());
					startActivity(intent);
				} else {
					Intent intent = new Intent();
					intent.setClass(getActivity(),
							XiangqingFeishitiActivity.class);
					intent.putExtra("id", listMingxingxiangmus2.get(position)
							.getId());
					startActivity(intent);
				}
			}
		});

		iv_find.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), FindActivity.class);
				startActivity(intent);
			}
		});

		ll_xiaofei.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), XiangmuListActivity.class);
				intent.putExtra("title", "生活消费");
				MainActivity.TITLE = "27,24,23,25";
				startActivity(intent);
			}
		});
		ll_xinsanban.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), XiangmuListActivity.class);
				intent.putExtra("title", "新三板");
				MainActivity.TITLE = "1";
				startActivity(intent);
			}
		});
		ll_keji.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), XiangmuListActivity.class);
				intent.putExtra("title", "科技金融");
				MainActivity.TITLE = "26,4,2,22";
				startActivity(intent);
			}
		});
		ll_mineguanzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), MineguanzhuActivity.class);
				startActivity(intent);
			}
		});
		rl_collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new Thread() {
					public void run() {
						final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
						NameValuePair pair1 = new BasicNameValuePair("pid",
								listMingxingxiangmus.get(0).getId());
						NameValuePair pair2 = new BasicNameValuePair("uid",
								LoginActivity.UID);
						pairList.add(pair1);
						pairList.add(pair2);
						PostUtil postUtil = new PostUtil();
						String url1 = "http://wap.tianshijie.com.cn/appproject/favorite";
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
							String info = jsonObject.getString("info");
							Looper.prepare();
							Toast.makeText(getActivity(), info,
									Toast.LENGTH_SHORT).show();
							Looper.loop();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}.start();
				if (listMingxingxiangmus.get(0).getIs_sc().equals("0")) {
					listMingxingxiangmus.get(0).setIs_sc("1");
					iv_collect
							.setBackgroundResource(R.drawable.collect_xuanzhong);
					int tvcollectnum = Integer.parseInt(tv_collectnum.getText()
							.toString());
					tv_collectnum.setText(tvcollectnum + 1 + "");
				} else if (listMingxingxiangmus.get(0).getIs_sc().equals("1")) {
					iv_collect.setBackgroundResource(R.drawable.xin_wxz_bai);
					listMingxingxiangmus.get(0).setIs_sc("1");
					int tvcollectnum = Integer.parseInt(tv_collectnum.getText()
							.toString());
					tv_collectnum.setText(tvcollectnum - 1 + "");
				}
				for (int i = 0; i < listMingxingxiangmus2.size(); i++) {
					if (listMingxingxiangmus.get(0).getId()
							.equals(listMingxingxiangmus2.get(i).getId())) {
						if (listMingxingxiangmus2.get(i).getIs_sc().equals("0")) {
							listMingxingxiangmus2.get(i).setIs_sc("1");
							int tvcollectnumad = Integer
									.parseInt(listMingxingxiangmus2.get(i)
											.getCollect());
							listMingxingxiangmus2.get(i).setCollect(
									tvcollectnumad + 1 + "");
						} else {
							listMingxingxiangmus2.get(i).setIs_sc("0");
							int tvcollectnumad = Integer
									.parseInt(listMingxingxiangmus2.get(i)
											.getCollect());
							listMingxingxiangmus2.get(i).setCollect(
									tvcollectnumad - 1 + "");
						}
						mainlistAdapter.notifyDataSetChanged();
					}
				}
			}
		});

		rl_collect2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new Thread() {
					public void run() {
						final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
						NameValuePair pair1 = new BasicNameValuePair("pid",
								listMingxingxiangmustwo.get(0).getId());
						NameValuePair pair2 = new BasicNameValuePair("uid",
								LoginActivity.UID);
						pairList.add(pair1);
						pairList.add(pair2);
						PostUtil postUtil = new PostUtil();
						String url1 = "http://wap.tianshijie.com.cn/appproject/favorite";
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
							String info = jsonObject.getString("info");
							Looper.prepare();
							Toast.makeText(getActivity(), info,
									Toast.LENGTH_SHORT).show();
							Looper.loop();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}.start();
				if (listMingxingxiangmustwo.get(0).getIs_sc().equals("0")) {
					listMingxingxiangmustwo.get(0).setIs_sc("1");
					iv_collect2
							.setBackgroundResource(R.drawable.collect_xuanzhong);
					int tvcollectnum = Integer.parseInt(tv_collectnum2
							.getText().toString());
					tv_collectnum2.setText(tvcollectnum + 1 + "");
				} else if (listMingxingxiangmustwo.get(0).getIs_sc()
						.equals("1")) {
					iv_collect2.setBackgroundResource(R.drawable.xin_wxz_bai);
					listMingxingxiangmustwo.get(0).setIs_sc("0");
					int tvcollectnum = Integer.parseInt(tv_collectnum2
							.getText().toString());
					tv_collectnum2.setText(tvcollectnum - 1 + "");
				}
				for (int i = 0; i < listMingxingxiangmus2.size(); i++) {
					if (listMingxingxiangmustwo.get(0).getId()
							.equals(listMingxingxiangmus2.get(i).getId())) {
						if (listMingxingxiangmus2.get(i).getIs_sc().equals("0")) {
							listMingxingxiangmus2.get(i).setIs_sc("1");
							int tvcollectnum = Integer
									.parseInt(listMingxingxiangmus2.get(i)
											.getCollect());
							listMingxingxiangmus2.get(i).setCollect(
									tvcollectnum + 1 + "");

						} else {
							listMingxingxiangmus2.get(i).setIs_sc("0");
							int tvcollectnum = Integer
									.parseInt(listMingxingxiangmus2.get(i)
											.getCollect());
							listMingxingxiangmus2.get(i).setCollect(
									tvcollectnum - 1 + "");

						}
						mainlistAdapter.notifyDataSetChanged();
					}
				}
			}
		});

		mypost();
	}

	private void mypost() {
		// TODO Auto-generated method stub

		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						/**
						 * BugStart
						 * Bug编号：BUG4
						 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
						 * 修复人：李超
						 * 修复日期：2015-10-23
						 */
						if(response == null){
							CToast.makeText(getActivity(), getActivity().getResources().getText(R.string.toast_error_network), 3000).show();
							return;
						}
						//BugEnd
						Log.d("TAG", response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							JSONObject jsonObject2 = jsonObject
									.getJSONObject("data");
							JSONArray jsonArray = jsonObject2
									.getJSONArray("zhuanti");
							lunbo_image = new ArrayList<String>();
							lunbo_url = new ArrayList<String>();
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject3 = jsonArray
										.getJSONObject(i);
								String img = jsonObject3.getString("img");
								String lunbourl = jsonObject3.getString("url");
								Log.v("img", img);
								lunbo_url.add(lunbourl);
								lunbo_image.add(MainActivity.PJURl + img);
							}
							JSONArray jsonArray2 = jsonObject2
									.getJSONArray("rec_project");
							muMingxingxiangmu = new Mingxingxiangmu();
							JSONObject jsonObject3 = jsonArray2
									.getJSONObject(0);
							muMingxingxiangmu.setBanner(MainActivity.PJURl
									+ jsonObject3.getString("appbanner"));
							muMingxingxiangmu.setId(jsonObject3.getString("id"));
							muMingxingxiangmu.setCity_val(jsonObject3
									.getString("city_val"));
							muMingxingxiangmu.setCollect(jsonObject3
									.getString("collect"));
							muMingxingxiangmu.setCopy_price(jsonObject3
									.getString("copy_price"));
							muMingxingxiangmu.setStatus_val(jsonObject3
									.getString("status_val"));
							muMingxingxiangmu.setName(jsonObject3
									.getString("name"));
							muMingxingxiangmu.setCity_val(jsonObject3
									.getString("city_val"));
							muMingxingxiangmu.setIs_sc(jsonObject3
									.getString("is_sc"));
							muMingxingxiangmu.setSy_time(jsonObject3
									.getString("sy_time"));
							muMingxingxiangmu.setCopy_number(jsonObject3
									.getString("copy_number"));
							muMingxingxiangmu.setLoan_amount(jsonObject3
									.getString("loan_amount"));
							muMingxingxiangmu.setJindu(jsonObject3
									.getString("jindu"));
							muMingxingxiangmu.setVersion(jsonObject3
									.getString("version"));
							listMingxingxiangmus.add(muMingxingxiangmu);
							muMingxingxiangmu = new Mingxingxiangmu();
							JSONObject jsonObject4 = jsonArray2
									.getJSONObject(1);
							muMingxingxiangmu.setBanner(MainActivity.PJURl
									+ jsonObject4.getString("appbanner"));
							muMingxingxiangmu.setId(jsonObject4.getString("id"));
							muMingxingxiangmu.setCity_val(jsonObject4
									.getString("city_val"));
							muMingxingxiangmu.setIs_sc(jsonObject4
									.getString("is_sc"));
							muMingxingxiangmu.setCollect(jsonObject4
									.getString("collect"));
							muMingxingxiangmu.setCopy_price(jsonObject4
									.getString("copy_price"));
							muMingxingxiangmu.setStatus_val(jsonObject4
									.getString("status_val"));
							muMingxingxiangmu.setName(jsonObject4
									.getString("name"));
							muMingxingxiangmu.setCity_val(jsonObject4
									.getString("city_val"));
							muMingxingxiangmu.setSy_time(jsonObject4
									.getString("sy_time"));
							muMingxingxiangmu.setCopy_number(jsonObject4
									.getString("copy_number"));
							muMingxingxiangmu.setLoan_amount(jsonObject4
									.getString("loan_amount"));
							muMingxingxiangmu.setJindu(jsonObject4
									.getString("jindu"));
							muMingxingxiangmu.setVersion(jsonObject4
									.getString("version"));
							listMingxingxiangmustwo.add(muMingxingxiangmu);
							JSONArray jsonArray3 = jsonObject2
									.getJSONArray("hot_project");
							if (jsonArray3 != null) {

								for (int i = 0; i < jsonArray3.length(); i++) {
									muMingxingxiangmu = new Mingxingxiangmu();
									JSONObject jsonObject5 = jsonArray3
											.getJSONObject(i);
									muMingxingxiangmu
											.setImage(MainActivity.PJURl
													+ jsonObject5
															.getString("image"));
									muMingxingxiangmu.setCity_val(jsonObject5
											.getString("city_val"));
									muMingxingxiangmu.setIs_sc(jsonObject5
											.getString("is_sc"));
									muMingxingxiangmu.setCollect(jsonObject5
											.getString("collect"));
									muMingxingxiangmu.setCopy_price(jsonObject5
											.getString("copy_price"));
									muMingxingxiangmu.setStatus_val(jsonObject5
											.getString("status_val"));
									muMingxingxiangmu.setName(jsonObject5
											.getString("name"));
									muMingxingxiangmu.setCity_val(jsonObject5
											.getString("city_val"));
									muMingxingxiangmu.setSy_time(jsonObject5
											.getString("sy_time"));
									muMingxingxiangmu
											.setCopy_number(jsonObject5
													.getString("copy_number"));
									muMingxingxiangmu
											.setLoan_amount(jsonObject5
													.getString("loan_amount"));
									muMingxingxiangmu.setJindu(jsonObject5
											.getString("jindu"));
									muMingxingxiangmu.setVersion(jsonObject5
											.getString("version"));
									muMingxingxiangmu.setSummary(jsonObject5
											.getString("summary"));
									muMingxingxiangmu.setId(jsonObject5
											.getString("id"));
									listMingxingxiangmus2
											.add(muMingxingxiangmu);
								}
							}
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							Message message2 = new Message();
							message2.what = 2;
							myHandler.sendMessage(message2);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("uid", LoginActivity.UID);
				if (cityid != null) {
					map.put("area", cityid);
				}

				return map;
			}
		};
		mQueue.add(stringRequest);
	}

	public class ImageTimerTask extends TimerTask {
		public volatile boolean timeCondition = true;

		// int gallerypisition = 0;

		public void run() {
			synchronized (this) {
				while (!timeCondition) {
					try {
						Thread.sleep(100);
						wait();
					} catch (InterruptedException e) {
						Thread.interrupted();
					}
				}
			}
			try {
				gallerypisition = images_gallery.getSelectedItemPosition() + 1;
				System.out.println(gallerypisition + "" + 123);
				Message msg = new Message();
				Bundle date = new Bundle();// 存放数据
				date.putInt("pos", gallerypisition);
				msg.setData(date);
				msg.what = 1;// 消息标识
				autoGalleryHandler.sendMessage(msg);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	public static void changePointView(int cur) {
		LinearLayout pointLinear = (LinearLayout) view
				.findViewById(R.id.gallery_point_linear);
		View view = pointLinear.getChildAt(positon);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			positon = cur;
		}
	}

	int foot = 0;

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {

			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
			}
		}, 1000);
		String url1 = "http://wap.tianshijie.com.cn/appindex/ajaxproject";
		StringRequest stringRequest = new StringRequest(Method.POST, url1,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						/**
						 * BugStart
						 * Bug编号：BUG4
						 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
						 * 修复人：李超
						 * 修复日期：2015-10-23
						 */
						if(response == null){
							CToast.makeText(getActivity(), getActivity().getResources().getText(R.string.toast_error_network), 3000).show();
							return;
						}
						//BugEnd
						Log.d("TAG", response);
						try {
							JSONObject jsonObject = new JSONObject(response);
							String info = jsonObject.getString("info");
							Log.v("info", info);
							if (info.equals("暂无更多项目")) {

								Toast.makeText(getActivity(), "暂无更多项目",
										Toast.LENGTH_SHORT).show();
								Log.v("info", info);

							}
							JSONArray jsonArray = jsonObject
									.getJSONArray("data");
							for (int i = 0; i < jsonArray.length(); i++) {
								muMingxingxiangmu = new Mingxingxiangmu();
								JSONObject jsonObject3 = jsonArray
										.getJSONObject(i);
								muMingxingxiangmu.setImage(MainActivity.PJURl
										+ jsonObject3.getString("image"));
								muMingxingxiangmu.setCity_val(jsonObject3
										.getString("city_val"));
								muMingxingxiangmu.setId(jsonObject3
										.getString("id"));
								muMingxingxiangmu.setCollect(jsonObject3
										.getString("collect"));
								muMingxingxiangmu.setCopy_price(jsonObject3
										.getString("copy_price"));
								muMingxingxiangmu.setSummary(jsonObject3
										.getString("summary"));
								muMingxingxiangmu.setIs_sc(jsonObject3
										.getString("is_sc"));
								muMingxingxiangmu.setStatus_val(jsonObject3
										.getString("status_val"));
								muMingxingxiangmu.setName(jsonObject3
										.getString("name"));
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
								muMingxingxiangmu.setVersion(jsonObject3
										.getString("version"));
								listMingxingxiangmus2.add(muMingxingxiangmu);
								Message message = new Message();
								message.what = 3;
								handler.sendMessage(message);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				if (cityid != null) {
					map.put("area", cityid);
				}
				map.put("start", (listMingxingxiangmus2.size()) + 1 + "");
				map.put("uid", LoginActivity.UID);

				return map;
			}
		};
		mQueue.add(stringRequest);
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
////////BugStart////////
//		Bug编号：BUG8
//		Bug描述：未能考虑到该对象为null的情况，可能会导致空指针
//		修复人：李超
//		修复时间：2015-10-23
		if(autoGallery != null){
			autoGallery.cancel();
		}
////////BugEnd////////
			autoGallerybe = false;
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
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!autoGallerybe) {
			lunbo();
		}

	}
}
