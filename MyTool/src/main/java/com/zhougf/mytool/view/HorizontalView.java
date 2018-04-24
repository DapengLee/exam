package com.zhougf.mytool.view;

import com.zhougf.mytool.tools.Out;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class HorizontalView extends LinearLayout {
	private static final String TAG = "Scroller";

	private OnRightListener onRightListener;

	private Scroller mScroller;

	private float last_position = 0;

	private float start_position = 0;

	// private int direction = 0; // 1代表向上，2代表向下。
	// private boolean is_scroll_top = true;
	// private boolean is_scroll_bottom = true;

	private int direction = 0; // 3代表左，4代表右。
	private boolean is_scroll_left = true;
	private boolean is_scroll_right = true;

	public HorizontalView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
	}

	// 调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy);
	}

	// 调用此方法滚动到目标位置
	public void smoothScrollTo(int fx, int fy, int drution) {
		int dx = fx - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		System.out.println("dx:" + dx + "  fx:" + fx);
		smoothScrollBy(dx, dy, drution);
	}

	// 调用此方法设置滚动的相对偏移
	public void smoothScrollBy(int dx, int dy) {

		// 设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
				dy, 1000);
		invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}

	public void smoothScrollBy(int dx, int dy, int drution) {

		// 设置mScroller的滚动偏移量
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx,
				dy, drution);
		invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
	}

	@Override
	public void computeScroll() {

		// 先判断mScroller滚动是否完成
		if (mScroller.computeScrollOffset()) {

			// 这里调用View的scrollTo()完成实际的滚动
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

			// 必须调用该方法，否则不一定能看到滚动效果
			postInvalidate();
		}
		super.computeScroll();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		System.out.println("HorizontalView.dispatchTouchEvent...");
		// return true;
		return super.dispatchTouchEvent(ev);
	}

	private float xStart, xEnd, yStart, yEnd;
	private int flag = 0;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		System.out.println("HorizontalView.onInterceptTouchEvent...");
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			flag = 0;
			xStart = ev.getX();
			yStart = ev.getY();
			xEnd = 0;
			yEnd = 0;

			direction = 0;
			is_scroll_left = true;
			is_scroll_right = true;
			start_position = ev.getX();
			last_position = ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if (xEnd == 0) {
				xEnd = ev.getX();
				yEnd = ev.getY();
				float x = Math.abs(xEnd - xStart);
				float y = Math.abs(yEnd - yStart);
				System.out.println("x:" + x + "  y:" + y);
				if (x > y && xEnd > xStart) {
					flag = 1;
				} else {
					flag = 2;
				}
			} else {
				xEnd = ev.getX();
				yEnd = ev.getY();
			}
			break;
		case MotionEvent.ACTION_UP:
			flag = 0;
			break;
		default:
			break;
		}

		System.out.println("flag=" + flag);
		if (flag == 1) {
			System.out.println("flag == 1");
			return true;
		} else if (flag == 2) {
			return super.onInterceptTouchEvent(ev);
		} else {
			return super.onInterceptTouchEvent(ev);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x = event.getX();
		System.out.println("x:" + x + "  :" + action);
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			direction = 0;
			is_scroll_left = true;
			is_scroll_right = true;
			start_position = x;
			last_position = x;
			break;
		case MotionEvent.ACTION_MOVE:
			if (x > start_position) {
				smoothScrollTo((int) (start_position - x), 0, 0);
			} else {
				if (last_position > start_position) {
					smoothScrollTo((int) (start_position - last_position), 0, 0);
				}
				start_position = x;
			}
			last_position = x;
			break;
		case MotionEvent.ACTION_UP:
			int xAbs = Math.abs(mScroller.getFinalX());
			int width = getWidth();
			System.out.println("ACTION_UP.x:" + xAbs + "  width:" + width);
			if (xAbs > width / 10) {
				smoothScrollTo(-width, 0, 500);
				if (onRightListener != null) {
					onRightListener.onFinish();
				}
			} else {
				smoothScrollTo(0, 0, 300);
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	public void left() {
		smoothScrollTo(0, 0, 0);
	}

	public void left(int drution) {
		int x = mScroller.getFinalX();
		Out.println("left.x:" + x);
		if (x == -getWidth()) {
			smoothScrollTo(0, 0, drution);
		}
	}

	public void right() {
		smoothScrollTo(-getWidth(), 0, 0);
		if (onRightListener != null) {
			onRightListener.onFinish();
		}
	}

	public void right(int drution) {
		int x = mScroller.getFinalX();
		Out.println("right1.x:" + x);
		if (x == 0) {
			smoothScrollTo(-getWidth(), 0, drution);
			if (onRightListener != null) {
				onRightListener.onFinish();
			}
		}
	}

	public void setOnRightListener(OnRightListener listener) {
		onRightListener = listener;
	}

	public interface OnRightListener {
		public void onFinish();
	}
}
