package com.example.tianshijie1.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class MyApplication extends Application {

	// 程序退出标记

	private static boolean isProgramExit = false;

	public void setExit(boolean exit) {

		isProgramExit = exit;

	}

	public boolean isExit() {

		return isProgramExit;

	}

	@Override
	public void onCreate() {
		super.onCreate();

		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "imageloader/Cache");
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.threadPriority(Thread.NORM_PRIORITY - 2)// 设置线程的优先级
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置缓存文件的名字
				.discCacheFileCount(60)// 缓存文件的最大个数
				.discCache(new UnlimitedDiscCache(cacheDir)) // 你可以传入自己的磁盘缓存
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.writeDebugLogs() // 打印log信息
				.threadPoolSize(3).build();

		// Initialize ImageLoader with configuration
		ImageLoader.getInstance().init(config);
	}

}
