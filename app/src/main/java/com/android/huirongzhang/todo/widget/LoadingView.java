package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

import static android.view.View.MeasureSpec.AT_MOST;

/**
 * Created by HuirongZhang on 2016/10/9.
 */

public class LoadingView extends View {
    private static final String TAG = "LoadingView";
    private static final int DEFAULT_BG_OUTER = 0xfffde399; // 外部边框的背景颜色
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 600;

    //对外面的边框缓存
    private WeakReference<Bitmap> outBorderBitmapCache;

    //该view的大小
    private int mWidth, mHeight;

    //自定义路径
    private RectF outerCircle;
    private RectF outerRectangle;

    //外部圆半径 内部圆半径  风扇背景的半径
    private float outerRadius;
    private float innerRadius;
    private float fanBgRadius;

    //自定义画笔
    private Paint outerPaint;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        outerPaint = new Paint();
        outerPaint.setColor(Color.RED);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeWidth(5);
    }

    /**
     * 调用了两次
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure()");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //对应wrap_content
        if (widthSpecMode == AT_MOST) {
            widthSize = Math.min(widthSize, DEFAULT_WIDTH);
        }

        if (heightSpecMode == AT_MOST) {
            heightSize = Math.min(heightSize, DEFAULT_HEIGHT);
        }

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        outerRadius = ((w / 10) < (h / 2)) ? w / 10 : h / 2;
        Log.d(TAG, "onSizeChanged(),mWidth = " + mWidth + ",mHeight =" + mHeight + ",outerRadius = " + outerRadius);
        outerRectangle = new RectF(0, 0, 8 * outerRadius, outerRadius * 2);
        outerCircle = new RectF(-outerRadius, 0, outerRadius, outerRadius * 2);
    }

    /**
     * 1.先从缓存中取，如果有直接取出来用，如果没有则新建并放入缓存中。
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw()");
        super.onDraw(canvas);
        Bitmap outBorderBitmap = outBorderBitmapCache == null ? null : outBorderBitmapCache.get();

        if (outBorderBitmap == null || outBorderBitmap.isRecycled()) {
            outBorderBitmap = getBitmap();
            outBorderBitmapCache = new WeakReference<Bitmap>(outBorderBitmap);
        }
        canvas.drawBitmap(outBorderBitmap, 0, 0, outerPaint);
    }

    //进度条外面的背景
    private Bitmap getBitmap() {
        //java.lang.IllegalArgumentException: width and height must be > 0
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);//根据指定参数生成新位图，图片压缩质量参数。
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(mWidth / 10, mHeight / 4);
        canvas.drawArc(outerCircle, 90, 180, true, outerPaint);
        canvas.drawRect(outerRectangle, outerPaint);
        return bitmap;
    }
}
