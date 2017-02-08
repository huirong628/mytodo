package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.huirongzhang.todo.R;


/**
 * Created by HuirongZhang
 * on 08/02/2017.
 */

public class PieChart extends View {

    private boolean mVisible;
    private Paint mPiePaint;

    public PieChart(Context context) {
        this(context, null);
    }

    public PieChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PieChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PieChartAppearance, defStyleAttr, defStyleRes);
        mVisible = a.getBoolean(R.styleable.PieChartAppearance_visible, false);
        a.recycle();
        init();
    }

    /**
     * 事件监听
     */
    public interface OnActionListener {
        void onAction(PieChart pieChart);
    }

    /**
     * 初始化放在构造函数中调用，onDraw()方法中调用会显著降低性能，同时UI显示滞慢。
     * <p>
     * Create Drawing Objects，Canvas and Paint.
     */
    private void init() {
        mPiePaint = new Paint();
        mPiePaint.setColor(0xff101010);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);

        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height = View.MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //drawing text,lines,bitmaps,and so on.
        /*canvas.drawArc();
        canvas.drawOval();
		canvas.drawRect();*/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
