package com.android.huirongzhang.todo.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.android.huirongzhang.todo.util.UIUtils;

/**
 * Created by HuirongZhang
 * on 02/03/2017.
 */

public class RotateView extends View {

	private static final int LARGE = 30;
	private static final int MIDDLE = 20;
	private static final int STROKE_WIDTH = 5;
	private Paint mCirclePaint;
	private Paint mLinePaint;
	private Paint mTextPaint;

	public RotateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mCirclePaint = new Paint();
		mCirclePaint.setColor(Color.BLACK);
		mCirclePaint.setStyle(Paint.Style.STROKE);
		mCirclePaint.setStrokeWidth(UIUtils.sp2px(getResources(), STROKE_WIDTH));

		mLinePaint = new Paint();
		mLinePaint.setColor(Color.BLACK);
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setStrokeWidth(UIUtils.sp2px(getResources(), 2));

		mTextPaint = new Paint();
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setStyle(Paint.Style.STROKE);
		mTextPaint.setTextSize(UIUtils.sp2px(getResources(), 22));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = width;
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/**
		 * 绘制圆盘
		 */
		drawCircle(canvas);

		/**
		 * 绘制圆盘上的刻度
		 *
		 * 总共是360，
		 * 每90一个长刻度，
		 * 每30一个中刻度
		 *
		 */
		drawLine(canvas);

		/**
		 * 绘制圆盘上的数字
		 */
		drawNum(canvas);
	}

	private void drawCircle(Canvas canvas) {
		float cx = getWidth() / 2;
		float cy = getHeight() / 2;
		float radius = getWidth() / 4;
		canvas.drawCircle(cx, cy, radius, mCirclePaint);
	}

	private void drawLine(Canvas canvas) {
		int startX = getWidth() / 2;
		int startY = getWidth() / 4;
		int stopX = startX;
		for (int i = 0; i < 360; i++) {
			if (i % 90 == 0) {
				canvas.save();
				canvas.rotate(i, getWidth() / 2, getHeight() / 2);
				int stopY = startY + (int) UIUtils.dp2px(getResources(), LARGE);
				canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
				canvas.restore();
			} else if (i % 30 == 0) {
				canvas.save();
				canvas.rotate(i, getWidth() / 2, getHeight() / 2);
				int stopY = startY + (int) UIUtils.dp2px(getResources(), MIDDLE);
				canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
				canvas.restore();
			}
		}
	}

	private void drawNum(Canvas canvas) {
		int startX = getWidth() / 2;
		for (int i = 0; i < 360; i++) {
			if (i % 90 == 0) {
				canvas.save();
				canvas.rotate(i, getWidth() / 2, getHeight() / 2);
				int startY = getWidth() / 4 + (int) UIUtils.dp2px(getResources(), LARGE + 10);
				canvas.drawText(i / 90 * 3 + "", startX, startY, mTextPaint);
				canvas.restore();
			} else if (i % 30 == 0) {
				canvas.save();
				canvas.rotate(i, getWidth() / 2, getHeight() / 2);
				int startY = getWidth() / 4 + (int) UIUtils.dp2px(getResources(), MIDDLE + 20);
				canvas.drawText(i / 30 + "", startX, startY, mTextPaint);
				canvas.restore();
			}
		}
	}
}
