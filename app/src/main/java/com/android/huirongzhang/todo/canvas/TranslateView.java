package com.android.huirongzhang.todo.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.android.huirongzhang.todo.util.UIUtils;

/**
 * Created by HuirongZhang
 * on 02/03/2017.
 */

public class TranslateView extends View {
	private static final int DEFAULT_MIN_SIZE = 100;
	private static final int DEFAULT_COUNT = 7;
	/**
	 * 刻度尺高度
	 */
	private static final int DIVIDING_RULE_HEIGHT = 90;

	private static final int LARGE = 50;
	private static final int MIDDLE = 60;
	private static final int SMALL = 70;

	private static final int STROKE_WIDTH = 5;

	private int mDividingRuleHeight = 0;

	private Rect mOutRect;
	/**
	 * 绘制外围的Paint
	 */
	private Paint mRectPaint;
	/**
	 * 绘制刻度线的Paint
	 */
	private Paint mLinePaint;
	/**
	 * 绘制刻度线上方数字的Paint
	 */
	private Paint mTextPaint;

	public TranslateView(Context context, AttributeSet attrs) {
		super(context, attrs);

		//一般在构造函数中进行初始化工作，如：初始化Paint,Rect

		init();
	}

	private void init() {
		mDividingRuleHeight = (int) UIUtils.dp2px(getResources(), DIVIDING_RULE_HEIGHT);

		//mOutRect = new Rect();

		mRectPaint = new Paint();
		mRectPaint.setStyle(Paint.Style.STROKE);
		mRectPaint.setColor(Color.BLACK);
		mRectPaint.setStrokeWidth(STROKE_WIDTH);

		mLinePaint = new Paint();
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setColor(Color.BLACK);
		mLinePaint.setStrokeWidth(STROKE_WIDTH);

		mTextPaint = new Paint();
		mTextPaint.setStyle(Paint.Style.STROKE);
		mTextPaint.setColor(Color.BLACK);
		mTextPaint.setTextSize(UIUtils.sp2px(getResources(), 22));

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
	}

	private int measure(int measureSpec) {
		int result = (int) UIUtils.dp2px(getResources(), DEFAULT_MIN_SIZE);
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else {
			if (mode == MeasureSpec.AT_MOST) {
				result = Math.min(result, size);
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		/**
		 * 分三步来绘制刻度尺：
		 *
		 * 1.绘制尺子的外围
		 * 2.绘制尺子的刻度线
		 * 3.绘制尺子上的数字
		 *
		 */

		drawRect(canvas);

		drawLines(canvas);

		drawNum(canvas);

		/**
		 * test translate
		 */

	}

	/**
	 * 绘制尺子外围
	 */
	private void drawRect(Canvas canvas) {
		mOutRect = new Rect();
		int leftMargin = (int) UIUtils.dp2px(getResources(), 10);
		int topMargin = (int) UIUtils.dp2px(getResources(), 10);
		mOutRect.left = leftMargin;
		mOutRect.top = topMargin;
		mOutRect.right = getMeasuredWidth() - leftMargin;
		mOutRect.bottom = mDividingRuleHeight;
		canvas.drawRect(mOutRect, mRectPaint);
	}

	/**
	 * 绘制尺子上的刻度线
	 * 有三类刻度线：长，中，短
	 */
	private void drawLines(Canvas canvas) {
		/**
		 * save the normal starting coordinate system
		 */
		canvas.save();//Pushes the current state onto the stack.

		//[[start  transform
		float dx = (int) UIUtils.dp2px(getResources(), 5);
		float dy = 0;
		canvas.translate(dx, dy);//执行平移操作

		for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
			int startX = (int) UIUtils.dp2px(getResources(), 20);
			int startY = mDividingRuleHeight;
			int stopX = startX;
			int stopY = 0;
			if (i % 10 == 0) {
				stopY = (int) UIUtils.dp2px(getResources(), LARGE);
			} else if (i % 5 == 0) {
				stopY = (int) UIUtils.dp2px(getResources(), MIDDLE);
			} else {
				stopY = (int) UIUtils.dp2px(getResources(), SMALL);
			}
			canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
			dx = (int) UIUtils.dp2px(getResources(), 5);
			canvas.translate(dx, dy);
		}

		//end transform]]
		/**
		 * restore the state we saved before transforming
		 */
		canvas.restore();//Pops the top state on the stack, restoring the context to that state.
	}

	/**
	 * 绘制刻度线上面的数字
	 */
	private void drawNum(Canvas canvas) {
		float dx = (int) UIUtils.dp2px(getResources(), 5);
		float dy = 0;
		canvas.translate(dx, dy);//执行平移操作
		for (int i = 0; i <= DEFAULT_COUNT * 10; i++) {
			int startX = (int) UIUtils.dp2px(getResources(), 16);
			int startY = (int) UIUtils.dp2px(getResources(), 40);
			if (i % 10 == 0) {
				canvas.drawText(i / 10 + "", startX, startY, mTextPaint);
			}
			dx = (int) UIUtils.dp2px(getResources(), 5);
			canvas.translate(dx, dy);
		}
	}
}
