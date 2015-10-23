package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.umeng.analytics.MobclickAgent;

public class YugouRengouActivity extends Activity {
	String type;
	Mingxingxiangmu mingxingxiangmu;
	private TextView tv_yugou, tv_name, tv_xiangmu, tv_zhuangtai, tv_meifen,
			tv_zongjine, tv_ygxianshi, textview1;
	private TextView tv_fenshu;
	private ImageView iv_jian, iv_jia, iv_backyugourengou, iv_tongyi;
	private Button btn_queren;
	float a2, b2;
	int xieyi = 1;
	Handler handler;
	String info, status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_yugou_rengou);
		type = getIntent().getStringExtra("type");
		mingxingxiangmu = (Mingxingxiangmu) getIntent().getSerializableExtra(
				"muMing");
		init();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:

					if (btn_queren.getText().toString().equals("确认预购")) {
						if (info.equals("预约跟投成功")) {
							Toast.makeText(YugouRengouActivity.this, info,
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent();
							intent.setClass(YugouRengouActivity.this,
									XiangmuguanliActivity.class);
							startActivity(intent);
							finish();
						}
					} else {

						if (status.equals("2")) {
							Toast.makeText(YugouRengouActivity.this, info,
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent();
							intent.setClass(YugouRengouActivity.this,
									ZhanghuguanliActivity.class);
							startActivity(intent);
							finish();
						}
					}
					if (btn_queren.getText().toString().equals("确认认购")) {
						if (info.equals("您的账户余额不足，请先充值")) {
							Toast.makeText(YugouRengouActivity.this, info,
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent();
							intent.setClass(YugouRengouActivity.this,
									ZhanghuguanliActivity.class);
							startActivity(intent);
							finish();
						} else if (info.equals("认购成功")) {
							Toast.makeText(YugouRengouActivity.this, info,
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent();
							intent.setClass(YugouRengouActivity.this,
									XiangmuguanliActivity.class);
							intent.putExtra("yema", "1");
							startActivity(intent);
							finish();
						} else if (info.equals("您没有同步易宝，请先同步易宝")) {
							final AlertDialog dlg = new AlertDialog.Builder(
									YugouRengouActivity.this).create();
							LayoutInflater inflater = LayoutInflater
									.from(YugouRengouActivity.this);
							inflater.inflate(R.layout.idqueren, null);
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
							tv_neirong.setText("正式认购需同步易宝，是否同步易宝？");
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
									intent.setClass(YugouRengouActivity.this,
											ZhanghuguanliActivity.class);
									startActivity(intent);
									finish();
									dlg.dismiss();
								}
							});
							dlg.setCanceledOnTouchOutside(true);

						}

					}
					break;
				}
				super.handleMessage(msg);
			}

		};
	}

	private void init() {
		// TODO Auto-generated method stub
		tv_yugou = (TextView) findViewById(R.id.tv_yugou);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_xiangmu = (TextView) findViewById(R.id.tv_xiangmu);
		tv_zhuangtai = (TextView) findViewById(R.id.tv_zhuangtai);
		tv_meifen = (TextView) findViewById(R.id.tv_meifen);
		tv_fenshu = (TextView) findViewById(R.id.tv_fenshu);
		tv_zongjine = (TextView) findViewById(R.id.tv_zongjine);
		tv_ygxianshi = (TextView) findViewById(R.id.tv_ygxianshi);
		textview1 = (TextView) findViewById(R.id.textview1);
		iv_jian = (ImageView) findViewById(R.id.iv_jian);
		iv_jia = (ImageView) findViewById(R.id.iv_jia);
		iv_backyugourengou = (ImageView) findViewById(R.id.iv_backyugourengou);
		iv_tongyi = (ImageView) findViewById(R.id.iv_tongyi);
		btn_queren = (Button) findViewById(R.id.btn_queren);
		fuzhi();
		iv_backyugourengou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_fenshu.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int fenshu = Integer.parseInt(tv_fenshu.getText().toString());

				if (fenshu > 200) {
					tv_fenshu.setText("200");
					iv_jia.setBackgroundResource(R.drawable.jia_1);
				}
				if (fenshu > 1) {
					iv_jian.setBackgroundResource(R.drawable.jian_2);
				}
			}
		});
	}

	private void fuzhi() {
		// TODO Auto-generated method stub
		tv_yugou.setText(type);
		btn_queren.setText("确认" + type);
		if (type.equals("认购")) {
			tv_ygxianshi.setVisibility(View.INVISIBLE);
		}
		tv_name.setText("真实姓名：" + MainActivity.REALNAME);
		tv_xiangmu.setText("投资项目：" + mingxingxiangmu.getName());
		tv_zhuangtai.setText("投资状态：" + mingxingxiangmu.getStatus_val());
		a2 = Float.parseFloat(mingxingxiangmu.getCopy_price());
		b2 = Float.parseFloat(mingxingxiangmu.getCopy_yuan_price());
		if (btn_queren.getText().toString().equals("确认预购")) {
			tv_meifen.setText("每份金额：" + a2 + "万元");
		} else {
			tv_meifen.setText("每份金额：" + b2 + "元");
		}

		iv_jia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = Integer.parseInt(tv_fenshu.getText().toString());
				if (i >= 1) {
					i = i + 1;
					tv_fenshu.setText(i + "");
					iv_jian.setBackgroundResource(R.drawable.jian_2);
					if (btn_queren.getText().toString().equals("确认预购")) {

						tv_zongjine.setText("总金额：" + i * a2 + "万元");
					} else {
						tv_zongjine.setText("总金额：" + i * b2 + "元");

					}

				}
			}
		});
		iv_jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = Integer.parseInt(tv_fenshu.getText().toString());
				if (i > 1) {
					i = i - 1;
					tv_fenshu.setText(i + "");
					if (btn_queren.getText().toString().equals("确认预购")) {
						tv_zongjine.setText("总金额：" + i * a2 + "万元");
					} else {
						tv_zongjine.setText("总金额：" + i * b2 + "元");
					}
				} else {
					iv_jian.setBackgroundResource(R.drawable.jian_1);
				}
			}
		});
		int i = Integer.parseInt(tv_fenshu.getText().toString());
		if (btn_queren.getText().toString().equals("确认预购")) {
			tv_zongjine.setText("总金额：" + i * a2 + "万元");
		} else {
			tv_zongjine.setText("总金额：" + i * b2 + "元");
		}
		textview1.setText(getClickableSpan());
		textview1.setMovementMethod(LinkMovementMethod.getInstance());
		iv_tongyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (xieyi == 1) {
					iv_tongyi.setBackgroundResource(R.drawable.sf_weixuanzhong);
					btn_queren.setBackgroundResource(R.drawable.shape5);

					xieyi = 0;
				} else {
					iv_tongyi.setBackgroundResource(R.drawable.xuangzhong_hong);
					btn_queren.setBackgroundResource(R.drawable.shape4);
					xieyi = 1;
				}
			}
		});
		btn_queren.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (xieyi == 0) {

				} else {

					new Thread() {
						public void run() {
							NameValuePair pair1 = new BasicNameValuePair("uid",
									LoginActivity.UID);
							NameValuePair pair2 = new BasicNameValuePair("pid",
									XiangqingFeishitiActivity.PID);
							NameValuePair pair3 = new BasicNameValuePair(
									"type", "1");
							NameValuePair pair5 = new BasicNameValuePair(
									"copy", tv_fenshu.getText().toString());
							NameValuePair pair6 = new BasicNameValuePair(
									"xieyi", "1");
							final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
							pairList.add(pair1);
							pairList.add(pair2);
							pairList.add(pair3);
							if (btn_queren.getText().toString().equals("确认预购")) {
								NameValuePair pair4 = new BasicNameValuePair(
										"copy_price", "" + a2);
								pairList.add(pair4);
							} else {
								NameValuePair pair4 = new BasicNameValuePair(
										"copy_price", "" + b2);
								pairList.add(pair4);
							}

							pairList.add(pair5);
							pairList.add(pair6);
							PostUtil postUtil = new PostUtil();
							String url1 = null;
							if (btn_queren.getText().toString().equals("确认预购")) {
								url1 = "http://wap.tianshijie.com.cn/appproject/invest";
							} else {
								url1 = "http://wap.tianshijie.com.cn/apppay/confirminvest";
							}
							String result = postUtil.DoPostNew(pairList, url1);
							/**
							 * BugStart
							 * Bug编号：BUG4
							 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
							 * 修复人：李超
							 * 修复日期：2015-10-23
							 */
							if(result == null){
								CToast.makeText(YugouRengouActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
								return;
							}
							//BugEnd
							Log.v("url", "1" + result);
							try {
								JSONObject jsonObject = new JSONObject(result);
								info = jsonObject.getString("info");
								status = jsonObject.getString("status");
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
			}

		});
	}

	private CharSequence getClickableSpan() {
		// TODO Auto-generated method stub
		View.OnClickListener l = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(YugouRengouActivity.this, WebviewActivity.class);
				intent.putExtra("title", "天使街投资人风险揭示书");
				intent.putExtra("url",
						"http://wap.tianshijie.com.cn/apphelp/fx");
				startActivity(intent);

			}
		};
		View.OnClickListener e = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(YugouRengouActivity.this, WebviewActivity.class);
				intent.putExtra("title", "在线投资服务条款");
				intent.putExtra("url",
						"http://wap.tianshijie.com.cn/apphelp/fwtk");
				startActivity(intent);

			}
		};
		View.OnClickListener b = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(YugouRengouActivity.this, WebviewActivity.class);
				intent.putExtra("title", "保密及非规避协议");
				intent.putExtra("url",
						"http://wap.tianshijie.com.cn/apphelp/bm");
				startActivity(intent);

			}
		};

		SpannableString spanableInfo = new SpannableString(
				"我已认真完整的阅读并接受《天使街投资人风险揭示书》、《在线投资服务条款》和《保密及非规避协议》，并愿意自己承担投资带来的风险");
		int start = 13;
		int end = 24;
		spanableInfo.setSpan(new Clickable(l), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanableInfo.setSpan(new Clickable(e), 27, 35,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanableInfo.setSpan(new Clickable(b), 38, 46,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		return spanableInfo;
	}

	/**
	 * 内部类，用于截获点击富文本后的事件
	 * 
	 * @author pro
	 * 
	 */
	class Clickable extends ClickableSpan implements OnClickListener {
		private final View.OnClickListener mListener;

		public Clickable(View.OnClickListener l) {
			mListener = l;

		}

		@Override
		public void onClick(View v) {
			mListener.onClick(v);
			Log.v("456", "456");
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(Color.parseColor("#333333"));
			ds.setUnderlineText(false); // 去除超链接的下划线
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.yugou_rengou, menu);
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
