package com.example.tianshijie1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.example.tianshijie1.Forgetpassword1Activity.ThreadShow;
import com.example.tianshijie1.adapter.DongtaiAdapter;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.application.MyApplication;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.bean.MyMessage;
import com.example.tianshijie1.scv.ObservableScrollView;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.Jindu;
import com.example.tianshijie1.util.LvHeightUtil;
import com.example.tianshijie1.util.PostUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class XiangmushitiActivity extends Activity implements
		ObservableScrollView.Callbacks {
	private ObservableScrollView mObservableScrollView;
	private View mPlaceholderView;
	private LinearLayout ll_can;
	String result, info;
	Handler handler, myHandler;
	Mingxingxiangmu muMingxingxiangmu;
	SnsPostListener mSnsPostListener;
	boolean stopThread = false;
	int yanzheng = 0;
	// 其他控件声明
	private TextView tv_xiangmuming, tv_collectnum2, tv_name2, tv_location2,
			tv_mubiao2, tv_yichou, tv_fenshu, tv_qitou, tv_ranggu, tv_sytime,
			tv_rengou, tv_yuetan, tv_pinglun;
	private RelativeLayout rl_mingxing2, rl_collect2;
	private ImageView iv_banner2, iv_rongzizhuangtai2, iv_collect2,
			iv_backxiangmushiti, iv_fenxiang;
	int ivcollect2 = 0;
	private Jindu jindushi;
	private WebView wv_jieshao;
	private ListView lv_dongtai;
	MyMessage myMessage;
	List<MyMessage> listmymessages;
	private DongtaiAdapter dongtaiAdapter;
	/**
	 * 在家两个ImageLoader
	 */
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_xiangmushiti);
		XiangqingFeishitiActivity.PID = getIntent().getStringExtra("id");
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		String appID = "wx65372604b1fda5da";
		String appSecret = "77c6d2daeb2f9d7a4e2c6f814eb9fac3";

		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(XiangmushitiActivity.this,
				appID, appSecret);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(
				XiangmushitiActivity.this, appID, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_background)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_background)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_background)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(0, false)).build();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					tv_pinglun.setText("评论" + "("
							+ muMingxingxiangmu.getPinglunnum() + ")");
					Log.v("123", muMingxingxiangmu.getIs_sc());
					tv_collectnum2.setText(muMingxingxiangmu.getCollect());
					tv_name2.setText(muMingxingxiangmu.getName());
					tv_xiangmuming.setText(muMingxingxiangmu.getName());
					tv_location2.setText(muMingxingxiangmu.getCity_val());
					tv_sytime.setText("剩余：" + muMingxingxiangmu.getSy_time()
							+ "天");
					tv_ranggu.setText("让股：" + muMingxingxiangmu.getLoan_share()
							+ "%");
					if (muMingxingxiangmu.getIs_sc().equals("1")) {
						iv_collect2
								.setBackgroundResource(R.drawable.collect_xuanzhong);
						ivcollect2 = 1;
					}

					tv_qitou.setText("起投：" + muMingxingxiangmu.getCopy_price()
							+ "万");
					tv_fenshu.setText("份数："
							+ muMingxingxiangmu.getCopy_number());

					tv_fenshu.setText("份数："
							+ muMingxingxiangmu.getCopy_number());

					Log.v("tv", tv_yichou.getText().toString());
					tv_mubiao2.setText("目标："
							+ muMingxingxiangmu.getLoan_amount() + "万");
					ImageLoader imageLoader2 = ImageLoader.getInstance();
					imageLoader2.displayImage(muMingxingxiangmu.getBanner(),
							iv_banner2, options, animateFirstListener);
					Log.v("mu", muMingxingxiangmu.getStatus_val());
					if (muMingxingxiangmu.getStatus_val().equals("预热中")) {
						tv_rengou.setText("预购");
						tv_yichou.setText("已筹："
								+ muMingxingxiangmu.getGet_amount() + "万");
					}
					if (muMingxingxiangmu.getStatus_val().equals("众筹中")) {
						iv_rongzizhuangtai2
								.setBackgroundResource(R.drawable.rongzhong);
						tv_yichou.setText("已筹："
								+ muMingxingxiangmu.getInvent_amount() + "万");
					}
					if (muMingxingxiangmu.getStatus_val().equals("众筹完成")) {
						tv_rengou.setTextColor(Color.parseColor("#d1d1d1"));
						tv_yuetan.setTextColor(Color.parseColor("#d1d1d1"));
						tv_yichou.setText("已筹："
								+ muMingxingxiangmu.getInvent_amount() + "万");
						iv_rongzizhuangtai2
								.setBackgroundResource(R.drawable.rongcheng);
					}
					tv_rengou.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (muMingxingxiangmu.getStatus_val().equals("预热中")) {
								Intent intent = new Intent();
								intent.setClass(XiangmushitiActivity.this,
										YugouRengouActivity.class);
								intent.putExtra("type", "预购");
								intent.putExtra("muMing", muMingxingxiangmu);
								startActivity(intent);
							}
							if (muMingxingxiangmu.getStatus_val().equals("众筹中")) {
								Intent intent = new Intent();
								intent.setClass(XiangmushitiActivity.this,
										YugouRengouActivity.class);
								intent.putExtra("type", "认购");
								intent.putExtra("muMing", muMingxingxiangmu);
								startActivity(intent);
							}
							if (muMingxingxiangmu.getStatus_val()
									.equals("众筹完成")) {
								tv_rengou.setTextColor(Color
										.parseColor("#d1d1d1"));
								tv_yuetan.setTextColor(Color
										.parseColor("#d1d1d1"));
								Toast.makeText(XiangmushitiActivity.this,
										"项目众筹已结束", Toast.LENGTH_LONG).show();
							}

						}
					});
					break;
				case 3:
					dongtaiAdapter = new DongtaiAdapter(
							XiangmushitiActivity.this, listmymessages);
					lv_dongtai.setAdapter(dongtaiAdapter);
					LvHeightUtil.setListViewHeightBasedOnChildren(lv_dongtai);
					break;
				case 4:
					stopThread = true;
					yanzheng = 0;
					break;

				}
				super.handleMessage(msg);
			}

		};
		myHandler = new Handler() {
			public void handleMessage(Message msg2) {
				switch (msg2.what) {
				case 2:
					int tvjindu = Integer
							.parseInt(muMingxingxiangmu.getJindu());
					Log.v("tvjindu", tvjindu + "");
					jindushi.TVJindu(tvjindu);
					break;
				}
				super.handleMessage(msg2);
			}
		};
		new Thread() {
			public void run() {
				NameValuePair pair1 = new BasicNameValuePair("pid",
						XiangqingFeishitiActivity.PID);
				NameValuePair pair2 = new BasicNameValuePair("uid",
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
					CToast.makeText(XiangmushitiActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
					return;
				}
				//BugEnd
				Log.v("url", "1" + result);

				try {
					muMingxingxiangmu = new Mingxingxiangmu();
					JSONObject jsonObject = new JSONObject(result);
					JSONObject jsonObject2 = jsonObject.getJSONObject("data");
					JSONArray jsonArray = jsonObject2.getJSONArray("project");
					JSONObject jsonObject3 = jsonArray.getJSONObject(0);
					muMingxingxiangmu.setBanner(MainActivity.PJURl
							+ jsonObject3.getString("appbanner"));
					muMingxingxiangmu.setId(jsonObject3.getString("id"));
					muMingxingxiangmu.setCity_val(jsonObject3
							.getString("city_val"));
					muMingxingxiangmu.setGet_amount(jsonObject3
							.getString("get_amount"));
					muMingxingxiangmu.setInvent_amount(jsonObject3
							.getString("invent_amount"));
					muMingxingxiangmu.setCollect(jsonObject3
							.getString("collect"));
					muMingxingxiangmu.setCopy_price(jsonObject3
							.getString("copy_price"));
					muMingxingxiangmu.setCopy_yuan_price(jsonObject3
							.getString("copy_yuan_price"));
					muMingxingxiangmu.setStatus_val(jsonObject3
							.getString("status_val"));
					muMingxingxiangmu.setUrl(jsonObject3.getString("url"));
					muMingxingxiangmu.setName(jsonObject3.getString("name"));
					muMingxingxiangmu.setSy_time(jsonObject3
							.getString("sy_time"));
					muMingxingxiangmu.setIs_sc(jsonObject3.getString("is_sc"));
					muMingxingxiangmu.setCopy_number(jsonObject3
							.getString("copy_number"));
					muMingxingxiangmu.setLoan_amount(jsonObject3
							.getString("loan_amount"));
					muMingxingxiangmu.setLoan_share(jsonObject3
							.getString("loan_share"));
					muMingxingxiangmu.setJindu(jsonObject3.getString("jindu"));
					muMingxingxiangmu.setUser_id(jsonObject3
							.getString("user_id"));
					muMingxingxiangmu.setPinglunnum(jsonObject3
							.getString("com_count"));
					JSONArray jsonArray2 = jsonObject3.getJSONArray("dongtai");
					if (jsonArray2.length() != 0) {
						for (int i = 0; i < jsonArray2.length(); i++) {
							myMessage = new MyMessage();
							JSONObject jsonObject4 = jsonArray2
									.getJSONObject(i);
							myMessage.setMsgfrom(jsonObject4
									.getString("content"));
							myMessage.setDateline(jsonObject4
									.getString("addtime"));

							listmymessages.add(myMessage);
							Message message = new Message();
							message.what = 3;
							handler.sendMessage(message);
						}
					}

					Message msg = Message.obtain();
					msg.what = 1;
					handler.sendMessage(msg);
					Message message2 = new Message();
					message2.what = 2;
					myHandler.sendMessage(message2);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub

		mObservableScrollView = (ObservableScrollView) findViewById(R.id.scroll_view);
		mObservableScrollView.setCallbacks(this);
		mObservableScrollView.smoothScrollTo(0, 20);
		mPlaceholderView = findViewById(R.id.placeholder);
		ll_can = (LinearLayout) findViewById(R.id.ll_can);
		ll_can.setFocusable(false);
		mObservableScrollView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						onScrollChanged(mObservableScrollView.getScrollY());
					}
				});
		initall();

	}

	/**
	 * 初始化项目中的其他控件
	 */
	private void initall() {
		// TODO Auto-generated method stub
		tv_xiangmuming = (TextView) findViewById(R.id.tv_xiangmuming);
		tv_collectnum2 = (TextView) findViewById(R.id.tv_collectnum2);
		tv_name2 = (TextView) findViewById(R.id.tv_name2);
		tv_location2 = (TextView) findViewById(R.id.tv_location2);
		jindushi = (Jindu) findViewById(R.id.jindushi);
		tv_mubiao2 = (TextView) findViewById(R.id.tv_mubiao2);
		tv_yichou = (TextView) findViewById(R.id.tv_yichou);
		tv_fenshu = (TextView) findViewById(R.id.tv_fenshu);
		tv_qitou = (TextView) findViewById(R.id.tv_qitou);
		tv_ranggu = (TextView) findViewById(R.id.tv_ranggu);
		tv_yuetan = (TextView) findViewById(R.id.tv_yuetan);
		tv_pinglun = (TextView) findViewById(R.id.tv_pinglun);
		tv_sytime = (TextView) findViewById(R.id.tv_sytime);
		tv_rengou = (TextView) findViewById(R.id.tv_rengou);
		wv_jieshao = (WebView) findViewById(R.id.wv_jieshao);
		lv_dongtai = (ListView) findViewById(R.id.lv_dongtai);
		listmymessages = new ArrayList<MyMessage>();
		TextView emptyView = new TextView(XiangmushitiActivity.this);
		emptyView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		emptyView.setGravity(Gravity.CENTER);
		emptyView.setText("该项目暂时还没有任何动态。");
		emptyView.setVisibility(View.GONE);
		((ViewGroup) lv_dongtai.getParent()).addView(emptyView);
		lv_dongtai.setEmptyView(emptyView);
		rl_mingxing2 = (RelativeLayout) findViewById(R.id.rl_mingxing2);
		rl_collect2 = (RelativeLayout) findViewById(R.id.rl_collect2);
		iv_banner2 = (ImageView) findViewById(R.id.iv_banner2);
		iv_rongzizhuangtai2 = (ImageView) findViewById(R.id.iv_rongzizhuangtai2);
		iv_collect2 = (ImageView) findViewById(R.id.iv_collect2);
		iv_backxiangmushiti = (ImageView) findViewById(R.id.iv_backxiangmushiti);
		iv_fenxiang = (ImageView) findViewById(R.id.iv_fenxiang);
		wv_jieshao.getSettings().setJavaScriptEnabled(true);
		wv_jieshao.setWebViewClient(new webViewClient());
		final String id = XiangqingFeishitiActivity.PID;
		String http = "http://wap.tianshijie.com.cn/appproject/info/pid/";
		String url = http + id;
		wv_jieshao.loadUrl(url);
		rl_collect2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (yanzheng == 0) {
					stopThread = false;
					new Thread(new ThreadShow()).start();
					yanzheng = 1;
					new Thread() {
						public void run() {
							final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
							NameValuePair pair1 = new BasicNameValuePair("pid",
									muMingxingxiangmu.getId());
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
								CToast.makeText(XiangmushitiActivity.this, getResources().getText(R.string.toast_error_network), 3000).show();
								return;
							}
							//BugEnd
							Log.v("url", "1" + result);
							try {
								JSONObject jsonObject = new JSONObject(result);
								info = jsonObject.getString("info");
								Intent intent = new Intent(); // Itent就是我们要发送的内容
								intent.setAction("shuaxin"); // 设置你这个广播的action
								intent.putExtra("pid",
										muMingxingxiangmu.getId());
								sendBroadcast(intent); // 发送广播
								Intent intent2 = new Intent(); // Itent就是我们要发送的内容
								intent2.setAction("shuaxin2"); // 设置你这个广播的action
								intent2.putExtra("title", getIntent()
										.getStringExtra("title"));
								sendBroadcast(intent2); // 发送广播
								Looper.prepare();
								Toast.makeText(XiangmushitiActivity.this, info,
										Toast.LENGTH_SHORT).show();
								Looper.loop();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}.start();
					if (ivcollect2 == 0) {
						ivcollect2 = 1;
						iv_collect2
								.setBackgroundResource(R.drawable.collect_xuanzhong);

						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						tv_collectnum2.setText(tvcollectnum + 1 + "");
					} else if (ivcollect2 == 1) {
						iv_collect2
								.setBackgroundResource(R.drawable.xin_wxz_bai);
						ivcollect2 = 0;
						int tvcollectnum = Integer.parseInt(tv_collectnum2
								.getText().toString());
						tv_collectnum2.setText(tvcollectnum - 1 + "");
					}

				}
			}
		});
		iv_backxiangmushiti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_yuetan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!muMingxingxiangmu.getStatus_val().equals("众筹完成")) {
					Intent intent = new Intent();
					intent.setClass(XiangmushitiActivity.this,
							YuetanActivity.class);
					intent.putExtra("pid", muMingxingxiangmu.getId());
					intent.putExtra("uid", muMingxingxiangmu.getUser_id());
					startActivity(intent);
				}
			}
		});
		tv_pinglun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(XiangmushitiActivity.this,
						PinglunActivity.class);
				intent.putExtra("pid", muMingxingxiangmu.getId());
				intent.putExtra("name", muMingxingxiangmu.getName());
				startActivity(intent);
			}
		});
		iv_fenxiang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				com.umeng.socialize.utils.Log.LOG = true;
				final String id = XiangqingFeishitiActivity.PID;
				String url = muMingxingxiangmu.getUrl();
				// 设置微信好友分享内容
				WeiXinShareContent weixinContent = new WeiXinShareContent();
				// 设置分享文字
				weixinContent.setShareContent(muMingxingxiangmu.getName()
						+ "\n" + url);
				// 设置title
				weixinContent.setTitle(muMingxingxiangmu.getName());
				// 设置分享图片
				mController.setShareMedia(new UMImage(
						XiangmushitiActivity.this, muMingxingxiangmu
								.getBanner()));
				mController.setShareMedia(weixinContent);
				// // 设置分享内容
				// mController.setShareContent(muMingxingxiangmu.getName() +
				// "\n"
				// + url);
				// // 设置分享图片, 参数2为图片的url地址
				// mController
				// .setShareMedia(new UMImage(
				// XiangqingFeishitiActivity.this,
				// R.drawable.ic_launcher));
				// 设置微信朋友圈分享内容
				CircleShareContent circleMedia = new CircleShareContent();
				circleMedia.setShareContent(muMingxingxiangmu.getName() + "\n"
						+ url);
				// 设置朋友圈title
				circleMedia.setTitle(muMingxingxiangmu.getName() + "-朋友圈");
				circleMedia.setShareImage(new UMImage(
						XiangmushitiActivity.this, muMingxingxiangmu
								.getBanner()));
				circleMedia.setTargetUrl(url);
				mController.setShareMedia(circleMedia);
				mController.openShare(XiangmushitiActivity.this, false);
				mController.registerListener(mSnsPostListener);
			}
		});

		mSnsPostListener = new SnsPostListener() {

			@Override
			public void onStart() {

			}

			@Override
			public void onComplete(SHARE_MEDIA platform, int stCode,
					SocializeEntity entity) {
				if (stCode == 200) {

					Toast.makeText(XiangmushitiActivity.this, "分享成功",
							Toast.LENGTH_SHORT).show();
					Log.v("f", "1");

				} else {
					Toast.makeText(XiangmushitiActivity.this, "分享失败",
							Toast.LENGTH_SHORT).show();
				}
			}
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.xiangmushiti, menu);
		return true;
	}

	class webViewClient extends WebViewClient {

		// 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			Log.v("url", url);
			wv_jieshao.loadUrl(url);
			return true;

		}
	}

	@Override
	public void onScrollChanged(int scrollY) {
		ll_can.setTranslationY(Math.max(mPlaceholderView.getTop(), scrollY));

	}

	@Override
	public void onDownMotionEvent() {
	}

	@Override
	public void onUpOrCancelMotionEvent() {
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

	// 线程类
	class ThreadShow implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!stopThread) {
				try {
					Thread.sleep(6000);

					Message message = new Message();
					message.what = 4;
					handler.sendMessage(message);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}
		}
	}
}
