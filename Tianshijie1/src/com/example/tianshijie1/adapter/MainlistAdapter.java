package com.example.tianshijie1.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;
import com.example.tianshijie1.util.Yuanhuan;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class MainlistAdapter extends BaseAdapter {
	private List<Mingxingxiangmu> list;
	private Context context;
	DisplayImageOptions options;
	int leibie;
	int ivcollect = 0;
	int shou;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public MainlistAdapter(Context context, List<Mingxingxiangmu> list,
			int leibie) {
		this.leibie = leibie;
		this.context = context;
		this.list = list;

		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_background)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_background)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_background)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(0, false)).build();
	}

	public MainlistAdapter(Context context, List<Mingxingxiangmu> list,
			int leibie, int shou) {
		this.leibie = leibie;
		this.context = context;
		this.list = list;
		this.shou = shou;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_background)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_background)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_background)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(0, false)).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		convertView = LayoutInflater.from(context).inflate(
				R.layout.test_layout, null);

		Yuanhuan yu_jindu = (Yuanhuan) convertView.findViewById(R.id.yu_jindu);
		LinearLayout ll_collect = (LinearLayout) convertView
				.findViewById(R.id.ll_collect);
		if (leibie == 1) {
			ll_collect.setVisibility(View.GONE);
		}
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_xiangqing = (TextView) convertView
				.findViewById(R.id.tv_xiangqing);
		TextView tv_jindu = (TextView) convertView.findViewById(R.id.tv_jindu);
		TextView tv_mubiao = (TextView) convertView
				.findViewById(R.id.tv_mubiao);
		TextView tv_qitou = (TextView) convertView.findViewById(R.id.tv_qitou);
		TextView tv_shengyutime = (TextView) convertView
				.findViewById(R.id.tv_shengyutime);
		TextView tv_area = (TextView) convertView.findViewById(R.id.tv_area);
		final TextView tv_listcollect = (TextView) convertView
				.findViewById(R.id.tv_listcollect);
		ImageView iv_image = (ImageView) convertView
				.findViewById(R.id.iv_image);
		ImageView iv_rongzizhuangtai = (ImageView) convertView
				.findViewById(R.id.iv_rongzizhuangtai);
		final ImageView iv_listcollect = (ImageView) convertView
				.findViewById(R.id.iv_listcollect);
		final Mingxingxiangmu mingxingxiangmu = list.get(position);
		tv_name.setText(mingxingxiangmu.getName());
		tv_xiangqing.setText(mingxingxiangmu.getSummary());
		tv_jindu.setText(mingxingxiangmu.getJindu() + "%");
		tv_mubiao.setText("目标：" + mingxingxiangmu.getLoan_amount() + "万");
		tv_qitou.setText("起投：" + mingxingxiangmu.getCopy_price() + "万");
		tv_shengyutime.setText("剩余：" + mingxingxiangmu.getSy_time() + "天");

		if (leibie == 0) {
			if (mingxingxiangmu.getIs_sc().equals("1")) {
				iv_listcollect
						.setBackgroundResource(R.drawable.collect_xuanzhong);
			} else {
				iv_listcollect.setBackgroundResource(R.drawable.xin_wxh_hui);

			}

		}

		if (mingxingxiangmu.getStatus_val().equals("众筹中")) {
			iv_rongzizhuangtai.setBackgroundResource(R.drawable.rongzhong);
		}
		if (mingxingxiangmu.getStatus_val().equals("众筹完成")) {
			iv_rongzizhuangtai.setBackgroundResource(R.drawable.rongcheng);
		}
		ll_collect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread() {
					public void run() {
						final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
						NameValuePair pair1 = new BasicNameValuePair("pid",
								list.get(position).getId());
						NameValuePair pair2 = new BasicNameValuePair("uid",
								LoginActivity.UID);
						pairList.add(pair1);
						pairList.add(pair2);
						PostUtil postUtil = new PostUtil();
						String url1 = "http://wap.tianshijie.com.cn/appproject/favorite";
						String result = postUtil.DoPostNew(pairList, url1);
						/**
						 * BugStart
						 * Bug编号：BUG4
						 * Bug描述：针对最初的BUG2和BUG3的统一处理，会因为没有网络获取不到数据数据产生nullpointer
						 * 修复人：李超
						 * 修复日期：2015-10-23
						 */
						if(result == null){
							CToast.makeText(context, context.getResources().getText(R.string.toast_error_network), 3000).show();
							return;
						}
						//BugEnd
						Log.v("url", "1" + result);
						try {
							JSONObject jsonObject = new JSONObject(result);
							String info = jsonObject.getString("info");
							if (shou != 1) {
								Intent intent = new Intent(); // Itent就是我们要发送的内容
								intent.putExtra("pid", list.get(position)
										.getId());
								intent.setAction("shuaxin"); // 设置你这个广播的action
								context.sendBroadcast(intent); // 发送广播
							} else {
								Intent intent = new Intent(); // Itent就是我们要发送的内容
								intent.putExtra("pid", list.get(position)
										.getId());
								intent.setAction("shuaxinlist"); // 设置你这个广播的action
								context.sendBroadcast(intent); // 发送广播
							}
							Looper.prepare();
							Toast.makeText(context, info, Toast.LENGTH_SHORT)
									.show();
							Looper.loop();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}.start();
				ivcollect = Integer.parseInt(list.get(position).getIs_sc());
				if (ivcollect == 0) {
					iv_listcollect
							.setBackgroundResource(R.drawable.collect_xuanzhong);
					int tvcollectnum = Integer.parseInt(tv_listcollect
							.getText().toString());
					tv_listcollect.setText(tvcollectnum + 1 + "");
					list.get(position).setIs_sc("1");
					list.get(position).setCollect(tvcollectnum + 1 + "");
				} else if (ivcollect == 1) {
					iv_listcollect
							.setBackgroundResource(R.drawable.xin_wxh_hui);
					int tvcollectnum = Integer.parseInt(tv_listcollect
							.getText().toString());
					tv_listcollect.setText(tvcollectnum - 1 + "");
					list.get(position).setIs_sc("0");
					list.get(position).setCollect(tvcollectnum - 1 + "");
				}
			}
		});
		tv_area.setText("地区：" + mingxingxiangmu.getCity_val());
		tv_listcollect.setText(mingxingxiangmu.getCollect());
		if (mingxingxiangmu.getJindu() != null) {
			float sweep = Float.parseFloat(mingxingxiangmu.getJindu());
			if (sweep > 100) {
				sweep = 100;
			}
			yu_jindu.jindu = sweep;
		}

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(mingxingxiangmu.getImage(), iv_image, options,
				animateFirstListener);
		// 标记图片视图，注意不能放在上面
		// 这是imageloader 中调用图片的方法

		return convertView;
	}
}
