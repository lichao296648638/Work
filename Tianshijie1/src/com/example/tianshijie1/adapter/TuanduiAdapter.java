package com.example.tianshijie1.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tianshijie1.R;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.bean.Tuandui;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class TuanduiAdapter extends BaseAdapter {
	private List<Tuandui> list;
	private Context context;
	DisplayImageOptions options;

	int ivcollect;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public TuanduiAdapter(Context context, List<Tuandui> list) {
		this.context = context;
		this.list = list;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_background)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_background)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_background)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(360, false)).build();
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
		ViewHolder viewHolder;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragmenttuantui_item, null);
			viewHolder.tv_name = (TextView) convertView
					.findViewById((R.id.tv_name));
			viewHolder.tv_jieshao = (TextView) convertView
					.findViewById((R.id.tv_jieshao));
			viewHolder.tv_zhiwei = (TextView) convertView
					.findViewById((R.id.tv_zhiwei));
			viewHolder.iv_touxiang = (ImageView) convertView
					.findViewById((R.id.iv_touxiang));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Tuandui tuandui = list.get(position);
		viewHolder.tv_name.setText(tuandui.getName());
		viewHolder.tv_zhiwei.setText("职位：" + tuandui.getZhiwei());
		viewHolder.tv_jieshao.setText(tuandui.getJianjie());
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tuandui.getIv_touxiang(),
				viewHolder.iv_touxiang, options, animateFirstListener);
		// 标记图片视图，注意不能放在上面
		// 这是imageloader 中调用图片的方法
		return convertView;
	}

	public class ViewHolder {
		private TextView tv_name, tv_jieshao, tv_zhiwei;
		private ImageView iv_touxiang;
	}
}
