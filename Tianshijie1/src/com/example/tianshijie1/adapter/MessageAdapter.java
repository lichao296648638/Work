package com.example.tianshijie1.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.bean.MyMessage;
import com.example.tianshijie1.util.CToast;
import com.example.tianshijie1.util.PostUtil;

public class MessageAdapter extends BaseAdapter {
	private List<MyMessage> list;
	private Context context;
	private int xia = 0;

	public MessageAdapter(Context context, List<MyMessage> list) {

		this.context = context;
		this.list = list;
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
		final ViewHolder viewHolder;

		viewHolder = new ViewHolder();
		convertView = LayoutInflater.from(context).inflate(
				R.layout.activity_my_message_item, null);
		viewHolder.tv_msgfrom = (TextView) convertView
				.findViewById((R.id.tv_msgfrom));
		viewHolder.rl_touch = (RelativeLayout) convertView
				.findViewById((R.id.rl_touch));
		viewHolder.tv_shijian = (TextView) convertView
				.findViewById((R.id.tv_shijian));
		viewHolder.tv_xuxian = (TextView) convertView
				.findViewById((R.id.tv_xuxian));
		viewHolder.tv_shixianxian = (TextView) convertView
				.findViewById((R.id.tv_shixianxian));
		viewHolder.tv_neirong = (TextView) convertView
				.findViewById((R.id.tv_neirong));
		viewHolder.iv_yidu = (ImageView) convertView
				.findViewById((R.id.iv_yidu));
		viewHolder.iv_xia = (ImageView) convertView.findViewById((R.id.iv_xia));
		viewHolder.rl_xianyin = (RelativeLayout) convertView
				.findViewById((R.id.rl_xianyin));
		convertView.setTag(viewHolder);

		final MyMessage myMessage = list.get(position);
		if (myMessage.getIsread().equals("1")) {
			viewHolder.iv_yidu.setVisibility(View.INVISIBLE);
			viewHolder.tv_msgfrom.setTextColor(Color.parseColor("#cccccc"));
			viewHolder.tv_shijian.setTextColor(Color.parseColor("#cccccc"));

		}
		viewHolder.tv_msgfrom.setText(myMessage.getMsgfrom());
		viewHolder.tv_shijian.setText(getStrTime(myMessage.getDateline()));
		viewHolder.tv_neirong.setText(myMessage.getSubject());
		viewHolder.rl_touch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (xia == 0) {
					viewHolder.rl_xianyin.setVisibility(View.VISIBLE);
					viewHolder.tv_shixianxian.setVisibility(View.GONE);
					viewHolder.tv_xuxian.setVisibility(View.VISIBLE);
					viewHolder.iv_xia
							.setBackgroundResource(R.drawable.xiaoxi_shang);
					viewHolder.iv_yidu.setVisibility(View.INVISIBLE);
					viewHolder.tv_msgfrom.setTextColor(Color
							.parseColor("#cccccc"));
					viewHolder.tv_shijian.setTextColor(Color
							.parseColor("#cccccc"));
					mypost(myMessage.getPmid());
					xia = 1;
				} else {
					viewHolder.tv_shixianxian.setVisibility(View.VISIBLE);
					viewHolder.tv_xuxian.setVisibility(View.GONE);
					viewHolder.rl_xianyin.setVisibility(View.GONE);
					viewHolder.iv_xia
							.setBackgroundResource(R.drawable.xiaoxi_xia);
					xia = 0;
				}
			}
		});

		return convertView;
	}

	private void mypost(final String pmid) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				NameValuePair pair2 = new BasicNameValuePair("uid",
						LoginActivity.UID);
				NameValuePair pair1 = new BasicNameValuePair("pmid", pmid);
				final List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair2);
				pairList.add(pair1);
				PostUtil postUtil = new PostUtil();
				String url1 = "http://wap.tianshijie.com.cn/appuser/sendmymessage";
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
				Log.v("url", result);
				try {
					JSONObject jsonObject = new JSONObject(result);
					String info = jsonObject.getString("info");
					if (info.equals("读取成功")) {
						Intent mIntent = new Intent("messagenum");
						mIntent.putExtra("shuliang", "jianyi");
						// 发送广播
						context.sendBroadcast(mIntent);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}.start();
	}

	public static String getStrTime(String time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = null;
		if (time.equals("")) {
			return "";
		}
		if (time.equals(null)) {
			return "";
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long loc_time = Long.valueOf(time);
		re_StrTime = sdf.format(new Date(loc_time * 1000L));
		return re_StrTime;
	}

	public class ViewHolder {
		private TextView tv_msgfrom, tv_shijian, tv_xuxian, tv_shixianxian,
				tv_neirong;
		private ImageView iv_yidu, iv_xia;
		private RelativeLayout rl_xianyin, rl_touch;
	}
}
