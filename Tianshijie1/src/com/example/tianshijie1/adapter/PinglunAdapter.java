package com.example.tianshijie1.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tianshijie1.R;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.bean.Pinglun;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class PinglunAdapter extends BaseAdapter {
	private List<Pinglun> list;
	private Context context;
	DisplayImageOptions options;

	int ivcollect;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public PinglunAdapter(Context context, List<Pinglun> list) {

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
					R.layout.activity_pinglun_item, null);
			viewHolder.tv_name = (TextView) convertView
					.findViewById((R.id.tv_name));
			viewHolder.tv_time = (TextView) convertView
					.findViewById((R.id.tv_time));

			viewHolder.tv_pinglun = (TextView) convertView
					.findViewById((R.id.tv_pinglun));
			viewHolder.iv_pinglun = (ImageView) convertView
					.findViewById((R.id.iv_pinglun));
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final Pinglun pinglun = list.get(position);
		viewHolder.tv_name.setText(pinglun.getUsername());

		viewHolder.tv_time.setText(MessageAdapter.getStrTime(pinglun
				.getDateline()));
		int chang = pinglun.getPinglunxiangmu().length();
		SpannableStringBuilder style = new SpannableStringBuilder("#"
				+ pinglun.getPinglunxiangmu() + "#     " + pinglun.getContent());

		style.setSpan(new ForegroundColorSpan(Color.RED), 0, chang + 2,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		viewHolder.tv_pinglun.setText(style);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(pinglun.getAvatar(), viewHolder.iv_pinglun,
				options, animateFirstListener);
		// 标记图片视图，注意不能放在上面

		// 这是imageloader 中调用图片的方法

		return convertView;
	}

	public class ViewHolder {
		private TextView tv_name, tv_time, tv_pinglun;
		private ImageView iv_pinglun;
	}
}
