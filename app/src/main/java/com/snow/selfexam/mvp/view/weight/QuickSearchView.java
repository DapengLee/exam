package com.snow.selfexam.mvp.view.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义view实现界面左侧字母的索引
 *
 * @author 李大鹏
 * @version 1.0
 * @date 2016/1/11
 */

/*
 *  此类的介绍
 * 1. 显示26个字母
 * 		1). 得到视图的宽高, 并计算出itemWidth/itemHeight: onMeasure()
 * 		2). 遍历indexArr, 计算word的坐标, 并绘制出来 : onDraw();
 * 2. 让操作(down,move,up)的字母变色显示
 * 		1). 得到正在操作的字母的下标 : onTouchEvent()
 * 		2). 强制重绘 : 调用invalidate()
 * 3. 当操作的字母改变时, 提示文本更新显示
 * 		1). 使用自定义监听器: 监听器的定义和使用
 * 		2). 提示字母显示2s才自动消失 :  handler
 * 4. 显示列表
 */

public final class QuickSearchView extends View {
	private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
			"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };
	private float viewWidth; // 视图的宽度
	private float viewHeight; // 视图的高度
	private float itemWidth; // 每一个字母的宽度
	private float itemheight;//每一个字母的高度

	//字体的颜色
	private String color = "#8B7D6B";

	private int indexDraw = -1;

	private Paint paint;
	private Paint paintCircle;

	/**
	 * 构造方法初始化数据
	 * @param context
	 * @param attrs
	 */
	public QuickSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint();
		paint.setAntiAlias(true);
		paintCircle = new Paint();
		paintCircle.setAntiAlias(true);//设置抗锯齿(圆滑)
		paintCircle.setColor(Color.parseColor("#EE7600"));//字母的颜色
	}

	/**
	 * 重写测量布局的方法
	 * @param widthMeasureSpec  视图的高度
	 * @param heightMeasureSpec 视图的宽度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 获取视高度图的
		viewWidth = getMeasuredWidth();
		viewHeight = getMeasuredHeight();

		// 每一个字母的高度和宽度
		itemWidth = viewWidth;
		itemheight = viewHeight / 26;

	}

	/**
	 * 这个方法比较重要把26个字母画在屏幕的左侧
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		// 设置字体的大小
		paint.setTextSize(20);

		// 遍历indexArr
		for (int i = 0; i < 26; i++) {
			//touchIndex所对应的字母为灰色, 其它的为白色
			paint.setColor(Color.parseColor(color));
			String word = indexArr[i];

			//计算word的坐标,
			//计算文本的宽高
			Rect bounds = new Rect();
			paint.getTextBounds(word, 0, word.length(), bounds);
			float wordX = bounds.width();
			float wordY = bounds.height();

			float drawX = itemWidth / 2 - wordX / 2;
			float drawY = itemheight / 2 + wordY / 2 + i * itemheight;


			if (i == indexDraw) {
				canvas.drawCircle(itemWidth / 2, itemheight / 2 + i
						* itemheight, (float) (itemWidth / 4), paintCircle);
				paint.setColor(Color.WHITE);
			}

			//并绘制出来
			canvas.drawText(word, drawX, drawY, paint);
		}
	}

	private float lastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float eventY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 当下的时候字体颜色
			color = "#000000";
		case MotionEvent.ACTION_MOVE: // 当移动的时候

			int index = (int) (eventY / itemheight);
			if (indexDraw != index) {
				indexDraw = index;

				if (onWordLinaever != null && (index >= 0 && index <26)){	
					onWordLinaever.onWord(indexArr[index]);
				}
				//强制重绘
				invalidate();
			}

			break;
		case MotionEvent.ACTION_UP: //当手指离开的时候全为白色
			color = "#8B7D6B";
			invalidate();
			break;

		default:
			break;
		}
		return true;
	}

	// 自定义字母改变的时候的监听
	public interface OnWordLinaever{
		public void onWord(String word);
	}

	private OnWordLinaever onWordLinaever;
	public void setOnWordLinaever(OnWordLinaever onWordLinaever) {
		this.onWordLinaever = onWordLinaever;
	}
}
