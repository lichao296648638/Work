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
import com.example.tianshijie1.bean.Zijin;
import com.example.tianshijie1.util.PostUtil;

public class ZijinAdapter extends BaseAdapter {
	private List<Zijin> list;
	private Context context;
	private int xia = 0;

	public ZijinAdapter(Context context, List<Zijin> list) {

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
					R.layout.zijinjilu_item, null);
			viewHolder.tv_xuhao = (TextView) convertView
					.findViewById((R.id.tv_xuhao));
			viewHolder.tv_shijian = (TextView) convertView
					.findViewById((R.id.tv_shijian));
			viewHolder.tv_jine = (TextView) convertView
					.findViewById((R.id.tv_jine));
			viewHolder.tv_caozuo = (TextView) convertView
					.findViewById((R.id.tv_caozuo));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Zijin zijin = list.get(position);

		viewHolder.tv_caozuo.setText(zijin.getCaozuo());
		if (zijin.getCaozuo().equals("为自身账户体现 ")) {
			viewHolder.tv_caozuo.setText("提现成功");
		}
		viewHolder.tv_jine.setText(zijin.getJine());
		viewHolder.tv_xuhao.setText(zijin.getXuhao());
		viewHolder.tv_shijian.setText(getStrTime(zijin.getShijian()));
		return convertView;
	}

	public String getStrTime(String time) {
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
		private TextView tv_xuhao, tv_caozuo, tv_jine, tv_shijian;

	}
}
