package com.example.tianshijie1.fragement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.IdcordChangeActivity;
import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.MineXinxiActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.WebviewActivity;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class FragmentZhanghu extends Fragment {
	private View mMainView;
	private TextView tv_dongjie, tv_chongzhijine, tv_tixianjine, tv_yue,
			tv_chongzhi, tv_tixian;
	private Button btn_tongbu;
	String result, info;
	Handler handler;
	String amount, recharge_amount, withdraw_amount, freeze_amount, is_yeepay,
			email, mobile, realname, idcardnumber;
	String cardNom;// 应该是卡号

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.zhanghuzijin,
				(ViewGroup) getActivity().findViewById(R.id.viewpager), false);

		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					tv_dongjie.setText(freeze_amount + "元");
					tv_chongzhijine.setText(recharge_amount + "元");
					tv_tixianjine.setText(withdraw_amount + "元");
					tv_yue.setText("账户可用余额：" + amount + "元");
					if (freeze_amount == null) {
						tv_dongjie.setText(0 + "元");
					}
					if (recharge_amount == null) {
						tv_chongzhijine.setText("0" + "元");
					}
					if (withdraw_amount == null) {
						tv_tixianjine.setText(0 + "元");
					}
					if (amount == null) {
						tv_yue.setText("账户可用余额：" + 0 + "元");
					}
					Log.v("is_yeepay", is_yeepay);
					if (is_yeepay.equals("1")) {
						btn_tongbu.setBackgroundResource(R.drawable.shape8);

					}
					break;
				case 2:
					if (info.equals("您已经同步了易宝，无需重复同步")) {
						Toast.makeText(getActivity(), "您已经同步了易宝，无需重复同步。",
								Toast.LENGTH_SHORT).show();
					} else if (info.equals("请先完善资料并通过认证")) {
						Toast.makeText(getActivity(), "请先完善资料并通过认证",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(getActivity(), MineXinxiActivity.class);
						startActivity(intent);
					}

					break;
				case 3:
					Intent intent = new Intent();
					intent.setClass(getActivity(), MineXinxiActivity.class);
					startActivity(intent);
					break;
				}
				super.handleMessage(msg);
			}

		};

	}

	private void init() {
		// TODO Auto-generated method stub
		tv_dongjie = (TextView) mMainView.findViewById(R.id.tv_dongjie);
		tv_chongzhijine = (TextView) mMainView
				.findViewById(R.id.tv_chongzhijine);
		tv_tixianjine = (TextView) mMainView.findViewById(R.id.tv_tixianjine);
		tv_yue = (TextView) mMainView.findViewById(R.id.tv_yue);
		tv_chongzhi = (TextView) mMainView.findViewById(R.id.tv_chongzhi);
		tv_tixian = (TextView) mMainView.findViewById(R.id.tv_tixian);
		btn_tongbu = (Button) mMainView.findViewById(R.id.btn_tongbu);
		tv_chongzhi = (TextView) mMainView.findViewById(R.id.tv_chongzhi);
		tv_tixian = (TextView) mMainView.findViewById(R.id.tv_tixian);
		mypost("http://wap.tianshijie.com.cn/appuser/accountrecharge");
		btn_tongbu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (is_yeepay.equals("0")) {
					if (!realname.equals("") && !idcardnumber.equals("")
							&& !mobile.equals("") && !email.equals("")) {
						String url1 = "http://wap.tianshijie.com.cn/apppay/synchronousoperation/uid/";
						Intent intent = new Intent();
						intent.setClass(getActivity(), WebviewActivity.class);
						intent.putExtra("title", "同步易宝");
						intent.putExtra("url", url1 + LoginActivity.UID);
						startActivity(intent);
					} else {
						final AlertDialog dlg = new AlertDialog.Builder(
								getActivity()).create();
						dlg.show();
						dlg.setCancelable(true);
						Window window = dlg.getWindow();
						// *** 主要就是在这里实现这种效果的.
						// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
						window.setContentView(R.layout.idqueren);
						// 为确认按钮添加事件,执行退出应用操作
						final TextView tv_quxiao = (TextView) window
								.findViewById(R.id.tv_quxiao);
						final TextView tv_queren = (TextView) window
								.findViewById(R.id.tv_queren);
						final TextView tv_neirong = (TextView) window
								.findViewById(R.id.tv_neirong);
						tv_neirong.setText("您的个人信息尚未完善，是否先去完善个人信息？");
						tv_quxiao.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dlg.cancel();
							}
						});
						tv_queren.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent();
								intent.setClass(getActivity(),
										MineXinxiActivity.class);
								startActivity(intent);
								getActivity().finish();

							}
						});
						dlg.setCanceledOnTouchOutside(true);

					}

				}

			}
		});
		tv_chongzhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (is_yeepay.equals("1")) {

					final AlertDialog dlg = new AlertDialog.Builder(
							getActivity()).create();
					dlg.show();
					Window window = dlg.getWindow();
					window.setContentView(R.layout.congzhi_jine);
					dlg.getWindow().clearFlags(
							WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
					final EditText et_jine = (EditText) window
							.findViewById(R.id.et_jine);// 定义一个文本输入框
					Button btn_queren = (Button) window
							.findViewById(R.id.btn_queren);
					Button btn_quxiao = (Button) window
							.findViewById(R.id.btn_quxiao);
					btn_quxiao.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dlg.dismiss();
						}
					});
					btn_queren.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							String url1 = "http://wap.tianshijie.com.cn/apppay/recharge/uid/";
							String amount = "/amount/";
							String jine = et_jine.getText().toString();
							Intent intent = new Intent();
							intent.setClass(getActivity(),
									WebviewActivity.class);
							intent.putExtra("title", "充值");
							intent.putExtra("url", url1 + LoginActivity.UID
									+ amount + jine);
							startActivity(intent);
							dlg.dismiss();
						}

					});

					dlg.setCanceledOnTouchOutside(true);

				} else {
					Toast.makeText(getActivity(), "请先同步易宝", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		tv_tixian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (is_yeepay.equals("1")) {
					if (!cardNom.equals("0") && cardNom != null) {

						final AlertDialog dlg = new AlertDialog.Builder(
								getActivity()).create();
						dlg.show();
						Window window = dlg.getWindow();
						window.setContentView(R.layout.congzhi_jine);
						dlg.getWindow()
								.clearFlags(
										WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
						final EditText et_jine = (EditText) window
								.findViewById(R.id.et_jine);// 定义一个文本输入框
						Button btn_queren = (Button) window
								.findViewById(R.id.btn_queren);
						Button btn_quxiao = (Button) window
								.findViewById(R.id.btn_quxiao);
						btn_quxiao.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dlg.dismiss();

							}
						});
						btn_queren.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								String url1 = "http://wap.tianshijie.com.cn/apppay/withdraw/uid/";
								String amount = "/amount/";
								String jine = et_jine.getText().toString();
								Intent intent = new Intent();
								intent.setClass(getActivity(),
										WebviewActivity.class);

								intent.putExtra("title", "提现");
								intent.putExtra("url", url1 + LoginActivity.UID
										+ amount + jine);
								startActivity(intent);

								dlg.dismiss();
							}

						});

						dlg.setCanceledOnTouchOutside(true);
					} else {
						Toast.makeText(getActivity(), "请登录天使街官网绑定银行卡",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(getActivity(), "请先同步易宝", Toast.LENGTH_SHORT)
							.show();
				}
			}

		});
	}

	private void mypost(final String url1) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair2);
				PostUtil postUtil = new PostUtil();

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
					info = jsonObject.getString("info");

					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					String a = jsonObject2.getString("useracount");
					if (!a.equals("{}")) {
						JSONObject jsonObject3 = jsonObject2
								.getJSONObject("useracount");
						amount = jsonObject3.getString("amount");
						recharge_amount = jsonObject3
								.getString("recharge_amount");
						withdraw_amount = jsonObject3
								.getString("withdraw_amount");
						cardNom = jsonObject3.getString("cardNom");

						freeze_amount = jsonObject3.getString("freeze_amount");
					}
					JSONObject jsonObject4 = jsonObject2.getJSONObject("user");
					is_yeepay = jsonObject4.getString("is_yeepay");
					realname = jsonObject4.getString("realname");
					idcardnumber = jsonObject4.getString("idcardnumber");
					mobile = jsonObject4.getString("mobile");
					email = jsonObject4.getString("email");
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);

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
		mypost("http://wap.tianshijie.com.cn/appuser/accountrecharge");
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
