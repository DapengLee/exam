package com.zhougf.mytool.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zhougf.mytool.R;

public class TiShiView extends TextView {
	private int width;
	private int height;
	private Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息

	private int num;
	private int direction = 0;
	private float jianju = 20;
	private float radius = 4;
	private int selectColor;
	private int unSelectColor;

	private int index = 0;
	private static final int RIGHT = 0;
	private static final int CENTER = 1;

	// public enum DirectionEnum {
	// right, center;
	// }

	public TiShiView(Context context) {
		super(context);

	}

	public TiShiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setAntiAlias(true);// 设置抗锯齿效�?
		// TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组
		// 在使用完成后，一定要调用recycle方法
		// 属�?的名称是styleable中的名称+“_属�?名称
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.TiShiView);
		// int textColor = array
		// .getColor(R.styleable.MyView_textColor, 0XFF00FF00); // 提供默认值，放置未指�?
		// float textSize = array.getDimension(R.styleable.MyView_textSize, 36);
		num = array.getInteger(R.styleable.TiShiView_num, 1);
		direction = array.getInteger(R.styleable.TiShiView_direction, RIGHT);
		jianju = array.getDimension(R.styleable.TiShiView_jianju, 20);
		radius = array.getDimension(R.styleable.TiShiView_radius, 4);
		selectColor = array.getColor(R.styleable.TiShiView_selectColor,
				0xffffffff);
		unSelectColor = array.getColor(R.styleable.TiShiView_unSelectColor,
				0xffaaaaaa);

		// mPaint.setColor(textColor);
		// mPaint.setTextSize(textSize);

		array.recycle(); // �?��要调用，否则这次的设定会对下次的使用造成影响
	}

	public void setIndex(int index) {
		this.index = index;
		invalidate();
	}

	public void setNum(int num) {
		this.num = num;
		invalidate();
	}

	public void setRadius(float radius) {
		this.radius = radius;
		invalidate();
	}

	// public void setColor(int selectColor, int unSelectColor) {
	// this.selectColor = selectColor;
	// this.unSelectColor = unSelectColor;
	// }

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		width = getWidth();
		height = getHeight();

		if (width > 0 && height > 0) {
			switch (direction) {
				case RIGHT:
					drawRight(canvas);
					break;
				case CENTER:
					drawCenter(canvas);
					break;
				default:
					drawRight(canvas);
					break;
			}

		}

		// if (width > 0 && height > 0) {
		// int jianju = width / (num * 2);
		//
		// mPaint.setColor(unSelectColor);
		// for (int i = 0; i < num; i++) {
		// canvas.drawCircle((i * 2 + 1) * jianju, height / 2, radius,
		// mPaint);
		// }
		// mPaint.setColor(selectColor);
		// canvas.drawCircle((index * 2 + 1) * jianju, height / 2, radius,
		// mPaint);
		// }
	}

	private void drawRight(Canvas canvas) {
		for (int i = 0; i < num; i++) {
			if (i == num - index - 1) {
				mPaint.setColor(selectColor);
				canvas.drawCircle(width - (num - index - 1 + 1) * jianju,
						height / 2, radius, mPaint);
			} else {
				mPaint.setColor(unSelectColor);
				canvas.drawCircle(width - (i + 1) * jianju, height / 2, radius,
						mPaint);
			}
		}
	}

	private void drawCenter(Canvas canvas) {
		for (int i = 0; i < num; i++) {
			if (i == index) {
				mPaint.setColor(selectColor);
			} else {
				mPaint.setColor(unSelectColor);
			}
			canvas.drawCircle((width - (num - 1) * jianju) / 2 + i * jianju,
					height / 2, radius, mPaint);
		}
	}

}
