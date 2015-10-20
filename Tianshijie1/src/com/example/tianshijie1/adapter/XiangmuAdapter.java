package com.example.tianshijie1.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tianshijie1.R;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.bean.Mingxingxiangmu;
import com.example.tianshijie1.util.Yuanhuan;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class XiangmuAdapter extends BaseAdapter {
	private List<Mingxingxiangmu> list;
	private Context context;
	DisplayImageOptions options;

	int ivcollect;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public XiangmuAdapter(Context context, List<Mingxingxiangmu> list) {

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
				R.layout.activity_xiangmuguanli_item, null);
		Yuanhuan yu_jindu = (Yuanhuan) convertView.findViewById(R.id.yu_jindu);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_xiangqing = (TextView) convertView
				.findViewById(R.id.tv_xiangqing);
		TextView tv_shijian = (TextView) convertView
				.findViewById(R.id.tv_shijian);
		TextView tv_fenshu = (TextView) convertView
				.findViewById(R.id.tv_fenshu);
		TextView tv_zongjia = (TextView) convertView
				.findViewById(R.id.tv_zongjia);
		TextView tv_jindu = (TextView) convertView.findViewById(R.id.tv_jindu);
		TextView tv_mubiao = (TextView) convertView
				.findViewById(R.id.tv_mubiao);
		TextView tv_qitou = (TextView) convertView.findViewById(R.id.tv_qitou);
		TextView tv_shengyutime = (TextView) convertView
				.findViewById(R.id.tv_shengyutime);
		TextView tv_area = (TextView) convertView.findViewById(R.id.tv_area);
		ImageView iv_image = (ImageView) convertView
				.findViewById(R.id.iv_image);
		ImageView iv_rongzizhuangtai = (ImageView) convertView
				.findViewById(R.id.iv_rongzizhuangtai);

		final Mingxingxiangmu mingxingxiangmu = list.get(position);

		tv_name.setText(mingxingxiangmu.getName());
		tv_xiangqing.setText(mingxingxiangmu.getSummary());
		tv_jindu.setText(mingxingxiangmu.getJindu() + "%");
		tv_mubiao.setText("目标：" + mingxingxiangmu.getLoan_amount() + "万");
		String time = MessageAdapter.getStrTime(mingxingxiangmu.getNotime());
		if (time.equals("")) {
			tv_shijian.setText(time);
		}

		tv_fenshu.setText("份数：" + mingxingxiangmu.getCopy_number() + "份");
		tv_zongjia.setText("总价：" + mingxingxiangmu.getZongjia() + "万");

		if (mingxingxiangmu.getZongjia().equals("1.0E-4")) {
			tv_zongjia.setText("总价：" + "0.0001" + "万");
		}
		tv_qitou.setText("起投：" + mingxingxiangmu.getCopy_price() + "万");
		if (mingxingxiangmu.getCopy_price().equals("1.0E-4")) {
			tv_qitou.setText("起投：" + "0.0001" + "万");
		}
		tv_shengyutime.setText("剩余：" + mingxingxiangmu.getSy_time() + "天");

		if (mingxingxiangmu.getStatus_val().equals("众筹中")) {
			iv_rongzizhuangtai.setBackgroundResource(R.drawable.rongzhong);
		}
		if (mingxingxiangmu.getStatus_val().equals("众筹完成")) {
			iv_rongzizhuangtai.setBackgroundResource(R.drawable.rongcheng);
		}
		tv_area.setText("地区：" + mingxingxiangmu.getCity_val());
		float sweep = Float.parseFloat(mingxingxiangmu.getJindu());
		if (sweep > 100) {
			sweep = 100;
		}
		yu_jindu.jindu = sweep;

		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(mingxingxiangmu.getImage(), iv_image, options,
				animateFirstListener);
		// 标记图片视图，注意不能放在上面

		// 这是imageloader 中调用图片的方法

		return convertView;
	}
}
