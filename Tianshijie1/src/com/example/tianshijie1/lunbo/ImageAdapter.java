package com.example.tianshijie1.lunbo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.tianshijie1.MainActivity;
import com.example.tianshijie1.R;
import com.example.tianshijie1.application.AnimateFirstDisplayListener;
import com.example.tianshijie1.fragement.ContentFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

@SuppressWarnings("deprecation")
public class ImageAdapter extends BaseAdapter {
	public static ArrayList<String> imageUrls;// 图片地址list
	public Context context;
	private ImageAdapter self;
	Intent intent;
	public static ImageView imageView;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public ImageAdapter(ArrayList<String> imageUrls, Context context) {
		ImageAdapter.imageUrls = imageUrls;
		this.context = context;
		this.self = this;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.zhanwei_background)// 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.zhanwei_background)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.zhanwei_background)// 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 是否緩存都內存中
				.cacheOnDisc(true)// 是否緩存到sd卡上
				.displayer(new RoundedBitmapDisplayer(0, false)).build();

	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {
		return imageUrls.get(position % imageUrls.size());
	}

	public long getItemId(int position) {
		return position;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 0: {
					self.notifyDataSetChanged();
				}
					break;
				}
				super.handleMessage(msg);
			} catch (Exception e) {
			}
		}
	};

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_putin3_item, null); // 实例化convertView
			Gallery.LayoutParams params = new Gallery.LayoutParams(
					Gallery.LayoutParams.MATCH_PARENT,
					Gallery.LayoutParams.MATCH_PARENT);
			convertView.setLayoutParams(params);
			// image = ((MainActivity) context).imagesCache.get(imageUrls
			// .get(position % imageUrls.size())); // 从缓存中读取图片
			// if (image == null) {
			// // 当缓存中没有要使用的图片时，先显示默认的图片
			// image = ((MainActivity) context).imagesCache
			// .get("background_non_load");
			// // 异步加载图片
			// LoadImageTask task = new LoadImageTask(convertView);
			// task.execute(imageUrls.get(position % imageUrls.size()));
			// }
			convertView.setTag(imageUrls);

		} else {
			convertView.getTag();
			// image = (Bitmap) convertView.getTag();
		}

		imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (position == 0) {
			imageLoader.displayImage(ImageAdapter.imageUrls.get(position),
					imageView, options, animateFirstListener);
		} else {
			imageLoader.displayImage(ImageAdapter.imageUrls.get(position % 3),
					imageView, options, animateFirstListener);
		}

		// imageView.setImageBitmap(image);// ImageResource(imageUrls[position %
		// imageUrls.size()]);
		// 设置缩放比例：保持原�?
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		ContentFragment.changePointView(position % imageUrls.size());
		/*
		 * imageView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * //((String) imageView).setSpan(new URLSpan("http://www.36939.net/"),
		 * 13, 15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		 * 
		 * 
		 * } });
		 */
		return convertView;

	}

	// // 加载图片的异步任�?
	// class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
	// private View resultView;
	//
	// LoadImageTask(View resultView) {
	// this.resultView = resultView;
	// }
	//
	// // doInBackground完成后才会被调用
	// protected void onPostExecute(Bitmap bitmap) {
	// // 调用setTag保存图片以便于自动更新图�?
	// resultView.setTag(bitmap);
	// }
	//
	// // 从网上下载图�?
	// protected Bitmap doInBackground(String... params) {
	// Bitmap image = null;
	// try {
	// // new URL对象 把网�?传入
	// URL url = new URL(params[0]);
	// // 取得链接
	// URLConnection conn = url.openConnection();
	// conn.connect();
	// // 取得返回的InputStream
	// InputStream is = conn.getInputStream();
	// // 将InputStream变为Bitmap
	// try {
	// image = BitmapFactory.decodeStream(is);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// is.close();
	// ((MainActivity) context).imagesCache.put(params[0], image); //
	// 把下载好的图片保存到缓存�?
	// Message m = new Message();
	// m.what = 0;
	// mHandler.sendMessage(m);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return image;
	// }
	// }
}
