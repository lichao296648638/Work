package com.example.tianshijie1.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import u.aly.v;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tianshijie1.LoginActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.adapter.MessageAdapter.ViewHolder;
import com.example.tianshijie1.bean.MyMessage;
import com.example.tianshijie1.util.PostUtil;

public class DongtaiAdapter extends BaseAdapter {
	private List<MyMessage> list;
	private Context context;
	private int xia = 0;

	public DongtaiAdapter(Context context, List<MyMessage> list) {
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
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.dongtai, null);
			viewHolder.tv_dongtai = (TextView) convertView
					.findViewById((R.id.tv_dongtai));
			viewHolder.iv_dongtai = (ImageView) convertView
					.findViewById((R.id.iv_dongtai));
			viewHolder.tv_shijian = (TextView) convertView
					.findViewById((R.id.tv_shijian));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			viewHolder.iv_dongtai
					.setBackgroundResource(R.drawable.img_dongtai_hong);
			viewHolder.tv_dongtai.setTextColor(Color.parseColor("#e1504a"));
			viewHolder.tv_shijian.setTextColor(Color.parseColor("#e1504a"));

		}
		final MyMessage myMessage = list.get(position);
		viewHolder.tv_dongtai.setText(myMessage.getMsgfrom());
		viewHolder.tv_shijian.setText(getStrTime(myMessage.getDateline()));
		return convertView;
	}

	public static String getStrTime(String time) {
		String re_StrTime = null;
		SimpleDateFormat sdf = null;
		if (time.equals("")) {
			return "";
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long loc_time = Long.valueOf(time);
		re_StrTime = sdf.format(new Date(loc_time * 1000L));
		return re_StrTime;
	}

	public class ViewHolder {
		private TextView tv_dongtai, tv_shijian;
		private ImageView iv_dongtai;
	}
}
