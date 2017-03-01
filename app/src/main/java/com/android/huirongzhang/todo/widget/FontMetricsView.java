package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.util.UIUtils;

/**
 * Created by HuirongZhang
 * on 01/03/2017.
 */

public class FontMetricsView extends View {
	private static final String TAG = "FontMetricsView";
	private static final int DEFAULT_MIN_SIZE = 200;
	private static final int PURPLE = Color.parseColor("#9315db");
	private static final int ORANGE = Color.parseColor("#ff8a00");
	private String mText;
	private TextPaint mTextPaint;
	private Paint mLinePaint;
	private Paint mRectPaint;
	private Rect mBounds;

	private static final float STROKE_WIDTH = 4.0f;
	public final static int DEFAULT_FONT_SIZE_PX = 100;

	public FontMetricsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mText = "11111";
		mTextPaint = new TextPaint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setTextSize(UIUtils.dp2px(getResources(), DEFAULT_FONT_SIZE_PX));
		mTextPaint.setColor(Color.BLACK);

		mLinePaint = new Paint();
		mLinePaint.setColor(Color.RED);
		mLinePaint.setStyle(Paint.Style.STROKE);
		mLinePaint.setStrokeWidth(STROKE_WIDTH);

		mRectPaint = new Paint();
		mRectPaint.setColor(Color.BLACK);
		mRectPaint.setStyle(Paint.Style.STROKE);
		mRectPaint.setStrokeWidth(STROKE_WIDTH);

		mBounds = new Rect();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = measure(widthMeasureSpec);
		int height = measure(heightMeasureSpec);

		setMeasuredDimension(width, height);
	}

	private int measure(int measureSpec) {
		int result = (int) (DEFAULT_MIN_SIZE * getResources().getDisplayMetrics().density);
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
		Log.d(TAG, "width = " + getWidth() + ",height = " + getHeight());
		Log.d(TAG, "MeasuredWidth = " + getMeasuredWidth() + ",MeasuredHeight = " + getMeasuredHeight());
		Log.d(TAG, "padding:" + getPaddingLeft() + "," + getPaddingTop() + "," + getPaddingRight() + "," + getPaddingBottom());

		Log.d(TAG, "canvas: width = " + canvas.getWidth() + ",height = " + canvas.getHeight());

		int verticalAdjustment = this.getHeight() / 2;
		Log.d(TAG, "verticalAdjustment = " + verticalAdjustment);

		canvas.translate(0, verticalAdjustment + 150);

		float startX = getPaddingLeft();
		float startY = getPaddingTop();

		float stopX = getMeasuredWidth();
		float stopY = 0;

		//draw text
		canvas.drawText(mText, 0, 0, mTextPaint);

		//draw line

		startX = 0;

		//baseLine,
		//从哪里开始绘制文本，哪里就是（0，0）
		startY = 0;
		stopY = startY;
		mLinePaint.setColor(Color.RED);
		Log.d(TAG, "startX, startY, stopX, stopY = " + startX + "," + startY + "," + stopX + "," + stopY);//0.0,0.0,1440.0,0.0
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

		//top
		startY = mTextPaint.getFontMetrics().top;
		stopY = startY;
		mLinePaint.setColor(PURPLE);
		Log.d(TAG, "top: startX, startY, stopX, stopY = " + startX + "," + startY + "," + stopX + "," + stopY);
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

		//ascent
		startY = mTextPaint.getFontMetrics().ascent;
		stopY = startY;
		mLinePaint.setColor(Color.GREEN);
		Log.d(TAG, "ascent: startX, startY, stopX, stopY = " + startX + "," + startY + "," + stopX + "," + stopY);
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

		//descent
		startY = mTextPaint.getFontMetrics().descent;
		stopY = startY;
		mLinePaint.setColor(Color.BLUE);
		Log.d(TAG, "descent: startX, startY, stopX, stopY = " + startX + "," + startY + "," + stopX + "," + stopY);
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

		//bottom
		startY = mTextPaint.getFontMetrics().bottom;
		stopY = startY;
		mLinePaint.setColor(ORANGE);
		Log.d(TAG, "bottom: startX, startY, stopX, stopY = " + startX + "," + startY + "," + stopX + "," + stopY);
		canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);

		//bounds
		mTextPaint.getTextBounds(mText, 0, mText.length(), mBounds);
		mLinePaint.setColor(Color.BLACK);
		float dx = getPaddingLeft();
		//canvas.drawRect(mBounds.left + dx, mBounds.top, mBounds.right + dx, mBounds.bottom, mRectPaint);
	}
}
