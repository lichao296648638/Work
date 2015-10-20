package com.example.tianshijie1.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Yuanhuan extends ImageView {
	private Context context;
	private Paint paint;
	String yuanhuanjindu;
	public float jindu = 10.0f;

	public Yuanhuan(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.paint = new Paint();
		this.paint.setAntiAlias(true); // 消除锯齿
		this.paint.setStyle(Style.STROKE); // 绘制空心圆或 空心矩形

	}

	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		int width = canvas.getClipBounds().width();// 获取高度
		int center = width / 2;// 计算中心点
		int innerCircle = Math.round(center - dip2px(context, 5)); // 设置内圆半径
		int ringWidth = Math.round(dip2px(context, 5)); // 设置圆环宽度
		Paint p1 = new Paint();
		p1.setColor(Color.rgb(245, 216, 215));
		canvas.drawCircle(center, center, Math.round(center), p1);
		// 第一种方法绘制圆环
		// 绘制内圆
		Paint p = new Paint();
		p.setStyle(Paint.Style.FILL);
		p.setColor(Color.WHITE);
		p.setStrokeWidth(1);
		canvas.drawCircle(center, center, innerCircle, p);
		// 绘制圆环
		this.paint.setColor(Color.rgb(224, 81, 75));
		// this.paint.setARGB(255, 212 ,225, 233);
		this.paint.setStrokeWidth(ringWidth);

		RectF oval = new RectF();
		oval.top = dip2px(context, 3);
		oval.left = dip2px(context, 3);
		oval.right = width - dip2px(context, 3);
		oval.bottom = width - dip2px(context, 3);
		// 扫过的角度，也就是能力值，注意一定要用float计算，不然会算出0

		float sweepAngle = (jindu / 100f) * 360f;
		// 绘制能力值
		canvas.drawArc(oval, 270, sweepAngle, false, this.paint);

		super.onDraw(canvas);

	}

	/* 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
