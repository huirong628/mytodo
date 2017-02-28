package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by HuirongZhang
 * on 28/02/2017.
 */

public class DonutProgressBarView extends View {

	private static final String TAG = DonutProgressBarView.class.getSimpleName();

	private int min_size = 100;

	private final int default_inner_background_color = Color.TRANSPARENT;
	private final int default_finished_color = Color.rgb(66, 145, 241);
	private final int default_unfinished_color = Color.rgb(204, 204, 204);
	private final int text_color = Color.rgb(66, 145, 241);

	private Paint finishedPaint;
	private Paint unfinishedPaint;
	private Paint innerCirclePaint;
	private Paint textPaint;

	private RectF finishedOuterRect = new RectF();
	private RectF unfinishedOuterRect = new RectF();

	private final float default_stroke_width;

	public DonutProgressBarView(Context context) {
		this(context, null);
	}

	public DonutProgressBarView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public DonutProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public DonutProgressBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		default_stroke_width = dp2px(getResources(), 10);
		initPainter();
	}

	private void initPainter() {
		innerCirclePaint = new Paint();
		innerCirclePaint.setColor(default_inner_background_color);
		innerCirclePaint.setAntiAlias(true);//图像边缘相对清晰一点,去掉锯齿痕迹

		finishedPaint = new Paint();
		finishedPaint.setColor(default_finished_color);
		finishedPaint.setStyle(Paint.Style.STROKE);
		finishedPaint.setAntiAlias(true);
		finishedPaint.setStrokeWidth(default_stroke_width);

		unfinishedPaint = new Paint();
		unfinishedPaint.setColor(default_unfinished_color);
		unfinishedPaint.setStyle(Paint.Style.STROKE);
		unfinishedPaint.setAntiAlias(true);
		unfinishedPaint.setStrokeWidth(default_stroke_width);

		textPaint = new Paint();
		textPaint.setColor(text_color);
		textPaint.setTextSize(sp2px(getResources(), 14));
		textPaint.setAntiAlias(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		Log.d(TAG, "width = " + measure(widthMeasureSpec) + ", height = " + measure(heightMeasureSpec));
		setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
	}

	private int measure(int measureSpec) {
		int result = (int) (getResources().getDisplayMetrics().density * min_size);//这里要用像素
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
		Log.d(TAG, "onDraw()");
		super.onDraw(canvas);
		float delta = default_stroke_width;

		//	float innerCircleRadius = getWidth() / 2f;
		//	canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, innerCircleRadius, innerCirclePaint);

		finishedOuterRect.set(delta, delta, getWidth() - delta, getRight() - delta);
		canvas.drawArc(finishedOuterRect, getStartingDegree(), getProgressAngle(), false, finishedPaint);

		unfinishedOuterRect.set(delta, delta, getWidth() - delta, getRight() - delta);
		canvas.drawArc(unfinishedOuterRect, getStartingDegree() + getProgressAngle(), 360 - getProgressAngle(), false, unfinishedPaint);

		String text = getProgress() + "%";
		float textHeight = textPaint.descent() + textPaint.ascent();
		canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2, (getHeight() - textHeight) / 2, textPaint);
	}

	private int getStartingDegree() {
		return 180;
	}

	private float getProgressAngle() {
		int max = 100;
		return getProgress() / (float) max * 360f;
	}

	private float getProgress() {
		return 10;
	}

	public static float dp2px(Resources resources, float dp) {
		final float scale = resources.getDisplayMetrics().density;
		return dp * scale + 0.5f;
	}

	public static float sp2px(Resources resources, float sp) {
		final float scale = resources.getDisplayMetrics().scaledDensity;
		return sp * scale;
	}
}
