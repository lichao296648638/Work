package com.example.tianshijie1.fragement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tianshijie1.FabuxiangmuActivity;
import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.MineShezhiActivity;
import com.example.tianshijie1.MineXinxiActivity;
import com.example.tianshijie1.MineguanzhuActivity;
import com.example.tianshijie1.MyMessageActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.WebviewActivity;
import com.example.tianshijie1.XiangmuguanliActivity;
import com.example.tianshijie1.ZhanghuguanliActivity;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MenuFragment extends Fragment {
	static View view;
	/**
	 * 布局控件 这里都定义成公共的 方便activity 中调用 完成Intent 跳转
	 */
	public RelativeLayout rl_touxiang, rl_xiangmuguanli, rl_shezhi;
	Handler handler;
	/**
	 * 在家两个ImageLoader
	 */
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	/**
	 * 把布局中我要用来写点击事件的部分都放在这里
	 * 
	 * @param context
	 */
	public RelativeLayout rl_fabuxiangmu, rl_wodeguanzhu, rl_bangzhuzhongxin,
			rl_zhanghuguanli, rl_mymessage;
	public ImageView iv_touxiang, iv_xiaoxi;
	private TextView tv_name, tv_shenfen, tv_guanzhi_fengexian;
	String result, realname, sinvest_type, avatar, unreadmess, name;
	Editor editor = null;
	RequestQueue mQueue;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_menu, container, false);
		mQueue = Volley.newRequestQueue(getActivity());
		editor = getActivity().getSharedPreferences(LoginActivity.PHONE,
				Activity.MODE_PRIVATE).edit();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 4:
					ImageLoader imageLoader_toxiang = ImageLoader.getInstance();
					DisplayImageOptions options1;
					options1 = new DisplayImageOptions.Builder()
							.showStubImage(R.drawable.zhanwei_fang)
							// 设置图片在下载期间显示的图片
							.showImageForEmptyUri(R.drawable.zhanwei_fang)
							// 设置图片Uri为空或是错误的时候显示的图片
							.showImageOnFail(R.drawable.zhanwei_fang)
							// 设置图片加载/解码过程中错误时候显示的图片
							.cacheInMemory(true)
							// 是否緩存都內存中
							.cacheOnDisc(true)
							// 是否緩存到sd卡上
							.displayer(new RoundedBitmapDisplayer(360, true))
							.build();
					imageLoader_toxiang.displayImage(MainActivity.PJURl
							+ MainActivity.AVATAR, iv_touxiang, options1,
							animateFirstListener);

					if (editor != null) {
						editor.putString("unreadmess", MainActivity.UNREADMESS);
						editor.putString("avatar", avatar);
						editor.commit();
					}
					if (!MainActivity.UNREADMESS.equals("0")) {
						iv_xiaoxi.setVisibility(View.VISIBLE);
					} else {
						iv_xiaoxi.setVisibility(View.GONE);
					}
					SimpleDateFormat sDateFormat = new SimpleDateFormat("HH",
							Locale.getDefault());
					String date = sDateFormat.format(new java.util.Date());
					int time = Integer.parseInt(date);
					if (realname.equals("")) {
						if (time > 12) {
							tv_name.setText("下午好，" + LoginActivity.USERNAME);
						} else {
							tv_name.setText("上午好，" + LoginActivity.USERNAME);
						}
					} else {
						if (time > 12) {
							tv_name.setText("下午好，" + realname);
						} else {
							tv_name.setText("上午好，" + realname);
						}
					}
					break;
				}
				super.handleMessage(msg);
			}

		};
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_shenfen = (TextView) view.findViewById(R.id.tv_shenfen);
		tv_guanzhi_fengexian = (TextView) view
				.findViewById(R.id.tv_guanzhi_fengexian);
		iv_touxiang = (ImageView) view.findViewById(R.id.iv_touxiang);
		iv_xiaoxi = (ImageView) view.findViewById(R.id.iv_xiaoxi);
		rl_fabuxiangmu = (RelativeLayout) view
				.findViewById(R.id.rl_fabuxiangmu);
		rl_touxiang = (RelativeLayout) view.findViewById(R.id.rl_touxiang);
		rl_wodeguanzhu = (RelativeLayout) view
				.findViewById(R.id.rl_wodeguanzhu);
		rl_bangzhuzhongxin = (RelativeLayout) view
				.findViewById(R.id.rl_bangzhuzhongxin);
		rl_zhanghuguanli = (RelativeLayout) view
				.findViewById(R.id.rl_zhanghuguanli);
		rl_mymessage = (RelativeLayout) view.findViewById(R.id.rl_mymessage);
		rl_xiangmuguanli = (RelativeLayout) view
				.findViewById(R.id.rl_xiangmuguanli);
		rl_shezhi = (RelativeLayout) view.findViewById(R.id.rl_shezhi);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_fang)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_fang)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_fang)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(360, true)).build();
		name = LoginActivity.USERNAME;
		realname = MainActivity.REALNAME;
		sinvest_type = MainActivity.INVEST_TYPEi;
		avatar = MainActivity.AVATAR;
		unreadmess = MainActivity.UNREADMESS;
		if (unreadmess != null) {
			if (unreadmess.equals("0")) {
				iv_xiaoxi.setVisibility(View.VISIBLE);
			} else {
				iv_xiaoxi.setVisibility(View.GONE);
			}
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH",
				Locale.getDefault());
		String date = sDateFormat.format(new java.util.Date());
		int time = Integer.parseInt(date);
		Log.v("date", date);
		if (realname.equals("")) {
			if (time > 12) {
				tv_name.setText("下午好，" + name);
			} else {
				tv_name.setText("上午好，" + name);
			}
		} else {
			if (time > 12) {
				tv_name.setText("下午好，" + realname);
			} else {
				tv_name.setText("上午好，" + realname);
			}
		}

		int invest_type = Integer.parseInt(sinvest_type);

		if (invest_type == 0) {
			tv_shenfen.setText("创业者");
		} else if (invest_type == 1) {
			tv_shenfen.setText("投资者");
		} else if (invest_type == 2) {
			tv_shenfen.setText("投资者");
		}
		if (invest_type != 0) {
			rl_fabuxiangmu.setVisibility(View.GONE);
			tv_guanzhi_fengexian.setVisibility(View.GONE);
		}
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(MainActivity.PJURl + avatar, iv_touxiang,
				options, animateFirstListener);

		rl_xiangmuguanli.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_xiangmuguanli.setBackgroundColor(Color.parseColor("#384150"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), XiangmuguanliActivity.class);
				startActivity(intent);
			}
		});
		rl_wodeguanzhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_wodeguanzhu.setBackgroundColor(Color.parseColor("#423f46"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), MineguanzhuActivity.class);
				startActivity(intent);
			}
		});
		rl_touxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), MineXinxiActivity.class);
				startActivity(intent);
			}
		});
		rl_shezhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_shezhi.setBackgroundColor(Color.parseColor("#3f434c"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), MineShezhiActivity.class);
				startActivity(intent);
			}
		});
		rl_bangzhuzhongxin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_bangzhuzhongxin.setBackgroundColor(Color
						.parseColor("#414544"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), WebviewActivity.class);

				intent.putExtra("title", "帮助中心");
				intent.putExtra("url",
						"http://wap.tianshijie.com.cn/apphelp/index");
				startActivity(intent);
			}
		});
		rl_zhanghuguanli.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_zhanghuguanli.setBackgroundColor(Color.parseColor("#3a4446"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), ZhanghuguanliActivity.class);
				startActivity(intent);
			}
		});
		rl_mymessage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_mymessage.setBackgroundColor(Color.parseColor("#433b4a"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), MyMessageActivity.class);
				startActivity(intent);
			}
		});
		rl_fabuxiangmu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rl_fabuxiangmu.setBackgroundColor(Color.parseColor("#413944"));
				Intent intent = new Intent();
				intent.setClass(getActivity(), FabuxiangmuActivity.class);
				startActivity(intent);
			}
		});

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		rl_fabuxiangmu.setBackgroundColor(Color.parseColor("#343843"));
		rl_mymessage.setBackgroundColor(Color.parseColor("#343843"));
		rl_zhanghuguanli.setBackgroundColor(Color.parseColor("#343843"));
		rl_bangzhuzhongxin.setBackgroundColor(Color.parseColor("#343843"));
		rl_shezhi.setBackgroundColor(Color.parseColor("#343843"));
		rl_wodeguanzhu.setBackgroundColor(Color.parseColor("#343843"));
		rl_xiangmuguanli.setBackgroundColor(Color.parseColor("#343843"));
		StringRequest stringRequest = new StringRequest(Method.POST,
				"http://wap.tianshijie.com.cn/appuser/login",
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
						try {
							JSONObject jsonObject = new JSONObject(response);
							if (response != null) {
								if (jsonObject != null) {
									JSONArray jsonArray = jsonObject
											.getJSONArray("data");
									JSONObject jsonObject2 = jsonArray
											.getJSONObject(0);
									avatar = jsonObject2.getString("avatar");
									realname = jsonObject2
											.getString("realname");
									String unreadmess = jsonObject2
											.getString("unreadmess");
									if (realname != null) {
										MainActivity.REALNAME = realname;
									}
									if (avatar != null) {
										MainActivity.AVATAR = avatar;
									}
									if (unreadmess != null) {
										MainActivity.UNREADMESS = unreadmess;
									}

									Message message = new Message();
									message.what = 4;
									handler.sendMessage(message);
								}
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
				map.put("username", LoginActivity.USERNAME);
				map.put("password", LoginActivity.PASSWORD);
				return map;
			}
		};
		mQueue.add(stringRequest);

	}
}
