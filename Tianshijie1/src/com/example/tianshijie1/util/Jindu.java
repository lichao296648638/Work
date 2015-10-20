package com.example.tianshijie1.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tianshijie1.R;

public class Jindu extends LinearLayout {
	TextView tv_jindu_paichi, tv_rongzijindu, tv_shu;
	ImageView iv_jindu;
	RelativeLayout rl_jinrutiao;
	Context context;
	int wid1;

	public Jindu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Jindu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.jindutiao, this);
		tv_jindu_paichi = (TextView) findViewById(R.id.tv_jindu_paichi);
		tv_rongzijindu = (TextView) findViewById(R.id.tv_rongzijindu);
		iv_jindu = (ImageView) findViewById(R.id.iv_jindu);
		rl_jinrutiao = (RelativeLayout) findViewById(R.id.rl_jinrutiao);
		tv_shu = (TextView) findViewById(R.id.tv_shu);

	}

	/**
	 * 设置进度条宽度
	 * 
	 */
	public void TVJindu(int wid) {
		tv_rongzijindu.setText("融资进度：" + wid + "%");
		if (wid > 90) {
			wid1 = 85;
			iv_jindu.setVisibility(View.GONE);
		} else {
			wid1 = wid;
		}
		float fwid = (wid * 3.28f) * 10 / 10;
		float fwid1 = (wid1 * 3.28f) * 10 / 10;

		LinearLayout.LayoutParams linearParams2 = (LinearLayout.LayoutParams) tv_jindu_paichi
				.getLayoutParams();
		linearParams2.width = dip2px(context, fwid1 - 70);
		tv_shu.setText("融资进度：" + wid);
		LinearLayout.LayoutParams linearParams1 = (LinearLayout.LayoutParams) rl_jinrutiao
				.getLayoutParams();
		if (fwid > 327) {
			linearParams1.width = -1;
		} else {
			linearParams1.width = dip2px(context, fwid);
		}

	}

	public static int dip2px(Context context, double dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
