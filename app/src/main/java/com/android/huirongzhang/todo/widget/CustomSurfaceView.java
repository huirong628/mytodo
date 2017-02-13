package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by HuirongZhang
 * on 10/02/2017.
 * 可以在主线程之外的线程中向屏幕绘图上。
 * 这样可以避免画图任务繁重的时候造成主线程阻塞，从而提高了程序的反应速度。
 * 在游戏开发中多用到SurfaceView，游戏中的背景、人物、动画等等尽量在画布canvas中画出。
 */

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CustomSurfaceView";

    private Paint mPaintOut;
    private Paint mPaintInt;
    private Thread mDrawThread;

    public CustomSurfaceView(Context context) {
        this(context, null);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPaintOut = new Paint();
        mPaintOut.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintOut.setColor(Color.GREEN);

        mPaintInt = new Paint();
        mPaintInt.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintInt.setColor(Color.RED);

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated");
        //一般在这里调用画图的线程
        mDrawThread = new DrawThread(holder);
        mDrawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed");
        //一般在这里将画图的线程停止或者释放
        mDrawThread = null;
    }

    class DrawThread extends Thread {
        SurfaceHolder holder;

        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            Canvas canvas = holder.lockCanvas();
            try {
                int w = canvas.getWidth();
                int h = canvas.getHeight();
                Log.d(TAG, "surfaceCreated,w = " + w + ",h =" + h);
                canvas.drawColor(Color.GRAY);
                canvas.drawCircle(w / 2, h / 2, w / 2, mPaintOut);
                canvas.drawCircle(w / 2, h / 2, w / 4, mPaintInt);
            } catch (Exception e) {

            } finally {
                if (canvas != null) {
                    //结束锁定，并提交改变，然后显示图形。
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}