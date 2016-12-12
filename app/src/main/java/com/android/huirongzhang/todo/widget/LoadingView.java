package com.android.huirongzhang.todo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.lang.ref.WeakReference;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by HuirongZhang on 2016/10/9.
 */

public class LoadingView extends View {
    private static final String TAG = "LoadingView";
    private static final int DEFAULT_BG_OUTER = 0xfffde399; // 外部边框的背景颜色
    private static final String DEFAULT_WHITE = "#fffefd";
    private static final String DEFAULT_BG_FAN = "#fcce5b";  // 风扇 扇叶的颜色

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 600;

    //对外面的边框缓存
    private WeakReference<Bitmap> outBorderBitmapCache;

    //该view的大小
    private int mWidth, mHeight;

    //自定义路径
    private RectF outerCircle;
    private RectF outerRectangle;
    private RectF innerCircle;
    private RectF innerRectangle;
    private RectF fanWhiteRect;

    //外部圆半径 内部圆半径  风扇背景的半径
    private float outerRadius;
    private float innerRadius;
    private float fanBgRadius;

    //自定义画笔
    private Paint outerPaint;
    private Paint fanPaint;
    private Paint fanBgPaint;

    //电风扇 扇叶路径
    private Path mPath;
    private Path nPath;

    // 定义结束的属性动画
    private ValueAnimator progressAnimator;
    private ValueAnimator completedAnimator;

    //进度值
    private float maxProgress = 100;
    private float currentProgress;
    private float completedProgress;

    //计算时间增量和progress增量
    private long preTime;
    private long addTime;
    private float addProgress;
    private float preProgress;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValueAnimator();
        init();
    }

    private void init() {
        outerPaint = new Paint();
        outerPaint.setColor(DEFAULT_BG_OUTER);
        outerPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        fanPaint = new Paint();
        fanPaint.setAntiAlias(true);
        fanPaint.setStyle(Paint.Style.FILL);
        fanPaint.setColor(Color.parseColor(DEFAULT_WHITE));

        fanBgPaint = new Paint(fanPaint);
        fanBgPaint.setColor(Color.parseColor(DEFAULT_BG_FAN));

        preTime = System.currentTimeMillis();
    }

    private void initValueAnimator() {
        progressAnimator = ValueAnimator.ofFloat(0, 1);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentProgress = preProgress + addProgress * (float) animation.getAnimatedValue();
            }
        });

        completedAnimator = ValueAnimator.ofFloat(0, 1);
        completedAnimator.setDuration(500);
        completedAnimator.setInterpolator(new AccelerateInterpolator());
        completedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                completedProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    public void setCurrentProgress(int currentProgress) {
        addProgress = currentProgress / maxProgress - this.currentProgress;
        preProgress = this.currentProgress;
        long leftTime = 0;
        if (progressAnimator.getCurrentPlayTime() < addTime) {
            leftTime = addTime - progressAnimator.getCurrentPlayTime();
        }
        addTime = System.currentTimeMillis() - preTime + leftTime;
        preTime = System.currentTimeMillis();
        progressAnimator.setDuration(500);
        progressAnimator.start();
    }

    /**
     * 调用了两次
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure()");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 系统最终会调用该方法将测量后的宽和高设置进去
         */
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    /**
     *
     * @param widthMeasureSpec 包含具体的测量模式和大小这些信息
     * @return View最后想要绘制的宽值
     */
    private int measureWidth(int widthMeasureSpec) {
        int resultWidth;
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthSpecMode == EXACTLY) {
            resultWidth = widthSize;
        } else {
            resultWidth = DEFAULT_WIDTH;
            if (widthSpecMode == AT_MOST) {
                resultWidth = Math.min(widthSize, DEFAULT_WIDTH);
            }
        }
        return resultWidth;
    }

    private int measureHeight(int heightMeasureSpec) {
        int resultHeight;
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSpecMode == EXACTLY) {
            resultHeight = heightSize;
        } else {
            resultHeight = DEFAULT_HEIGHT;
            if (heightSpecMode == AT_MOST) {
                resultHeight = Math.min(heightSize, DEFAULT_HEIGHT);
            }
        }
        return resultHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        outerRadius = ((w / 10) < (h / 2)) ? w / 10 : h / 2;
        innerRadius = outerRadius * 0.8f;
        Log.d(TAG, "onSizeChanged(),mWidth = " + mWidth + ",mHeight =" + mHeight + ",outerRadius = " + outerRadius);
        outerRectangle = new RectF(outerRadius, 0, 7 * outerRadius, outerRadius * 2);
        outerCircle = new RectF(0, 0, outerRadius * 2, outerRadius * 2);

        fanWhiteRect = new RectF(7 * outerRadius, outerRadius, 9 * outerRadius, outerRadius * 3);
        fanBgRadius = outerRadius * 0.8f;

        mPath = new Path();

        nPath = new Path();
        //右边的路径
        mPath.moveTo(8 * outerRadius, fanBgRadius);
        mPath.cubicTo(8 * outerRadius + fanBgRadius / 3 * 2, fanBgRadius / 8 * 7, 8 * outerRadius + fanBgRadius / 10, fanBgRadius / 10, 8 * outerRadius, 7);

        //左边的路径
        nPath.moveTo(8 * outerRadius, fanBgRadius);
        nPath.cubicTo(8 * outerRadius - fanBgRadius / 3 * 2, fanBgRadius / 8 * 7, 8 * outerRadius - fanBgRadius / 10, fanBgRadius / 10, 8 * outerRadius, 7);
        mPath.addPath(nPath);
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
        //canvas和outBorderBitmap联系在一起，outBorderBitmap保存绘制在Canvas上的像素信息。
        canvas.drawBitmap(outBorderBitmap, 0, 0, outerPaint);

        drawInnerCircle(canvas);

        //风扇的白色背景
        canvas.drawArc(fanWhiteRect, 90, 360, true, fanPaint);

        //风扇的黄色背景
        canvas.save();
        canvas.scale(0.9f, 0.9f, 8 * outerRadius, 2 * outerRadius);
        canvas.drawArc(fanWhiteRect, 90, 360, true, fanBgPaint);
        canvas.restore();


        //移动到圆的中心
        canvas.translate(0, 2 * outerRadius);

        //画风扇页
        canvas.save();
        drawFan(canvas, true);
        canvas.restore();

        invalidate();
    }

    private void drawFan(Canvas canvas, boolean isNeedRotate) {
        canvas.save();
        if (isNeedRotate) {
            canvas.rotate(currentProgress * 360 * 5, 8 * outerRadius, 0);
        }
        for (float i = 0; i <= 270; i = i + 90) {
            canvas.rotate(i, 8 * outerRadius, 0);
            canvas.drawPath(mPath, fanPaint);
        }
        canvas.restore();
    }

    //进度条外面的背景
    private Bitmap getBitmap() {
        //java.lang.IllegalArgumentException: width and height must be > 0
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);//根据指定参数生成新位图，图片压缩质量参数。
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(mWidth / 10, mWidth / 10);
        canvas.drawRect(outerRectangle, outerPaint);
        canvas.drawArc(outerCircle, 90, 180, true, outerPaint);

        return bitmap;
    }

    private void drawInnerCircle(Canvas canvas) {

    }
}
