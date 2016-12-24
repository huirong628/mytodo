package com.android.huirongzhang.todo.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by HuirongZhang
 * on 24/12/2016.
 * <p>
 * 上滑式控件
 * <p>
 * onTouch()  ,onClick(), onTouchEvent()
 * <p>
 * 基础只是恶补：
 * <p>
 * 1.translationX 和 translationY :这两个属性控制View所处的位置，它们的值是由layout容器设置的，是相对于坐标原点（0，0左上角）的一个偏移量。
 * 2.rotation, rotationX 和 rotationY：控制View绕着轴点（pivotX和pivotY）旋转。
 * 3.scaleX 和 scaleY：控制View基于pivotX和pivotY的缩放。
 * 4.pivotX 和 pivotY：旋转的轴点和缩放的基准点，默认是View的中心点。
 * 5.x 和 y：描述了view在其父容器中的最终位置，是左上角左标和偏移量（translationX，translationY）的和。
 * 6.aplha：透明度，1是完全不透明，0是完全透明。
 */

public class SlideUp implements View.OnTouchListener, ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private static final String TAG = SlideUp.class.getSimpleName();
    /**
     * SlideUpView所在的容器
     */
    private View mView;

    private float mViewHeight;

    private int mSlideDuration = 300;

    private boolean mHidden;//默认为false

    private float mTouchStartPosition;

    private float mViewStartPosition;

    private boolean mCanSlide = true;

    private float mSlideAnimateTo;

    private float mTouchableTop;

    /**
     * 属性动画，可以对任何对象做动画，甚至还可以没有对象。
     */
    private ValueAnimator mValueAnimator;

    public SlideUp(final View view) {
        this.mView = view;
        this.mTouchableTop = 300 * view.getResources().getDisplayMetrics().density;
        view.setOnTouchListener(this);
        createAnimation();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout(),mHidden = " + mHidden);
                if (mHidden) {
                    mViewHeight = view.getHeight();
                    hide();
                }
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG, "onTouch(),v =" + v + ",event = " + event.getAction());
        float touchedArea = event.getRawY() - mView.getTop();

        if (isAnimationRunning()) {
            return false;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mViewHeight = mView.getHeight();
                mTouchStartPosition = event.getRawY();
                mViewStartPosition = mView.getTranslationY();
                if (mTouchableTop < touchedArea) {
                    mCanSlide = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float move = event.getRawY() - mTouchStartPosition;
                float moveTo = mViewStartPosition + move;
                if (moveTo > 0 && mCanSlide) {
                    mView.setTranslationY(moveTo);
                }
                break;
            case MotionEvent.ACTION_UP:
                float slideAnimationFrom = mView.getTranslationY();
                boolean scrollableAreaConsumed = mView.getTranslationY() > mView.getHeight() / 5;
                if (scrollableAreaConsumed) {
                    mSlideAnimateTo = mView.getHeight();
                } else {
                    mSlideAnimateTo = 0;
                }
                mValueAnimator.setFloatValues(slideAnimationFrom, mSlideAnimateTo);
                mValueAnimator.start();
                break;
        }
        return true;
    }

    /**
     * 创建动画
     */
    private void createAnimation() {
        mValueAnimator = ValueAnimator.ofFloat();
        mValueAnimator.setDuration(mSlideDuration);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.addListener(this);
    }

    /**
     * @return 动画是否正在运行
     */
    public boolean isAnimationRunning() {
        return mValueAnimator != null && mValueAnimator.isRunning();
    }

    public void hide() {
        if (mView.getHeight() > 0) {
            mView.setTranslationY(mViewHeight);
            mView.setVisibility(View.GONE);
        } else {
            mHidden = true;
        }
    }

    /**
     * 显示
     * <p>
     * mView在Y方向的偏移过程，相对于mView的Top位置。
     * <p>
     * 开始的时候是隐藏的，此时与Top的距离为：mViewHeight
     * <p>
     * 最后的时候是显示的，此时与Top的距离为：0
     */
    public void animateIn() {
        Log.d(TAG, "animateIn()");
        mSlideAnimateTo = 0;
        /**
         * 开始偏移值
         */
        float startTranslationY = mView.getTranslationY();
        /**
         * 结束偏移值
         */
        float endTranslationY = 0;
        mValueAnimator.setFloatValues(startTranslationY, endTranslationY);
        mValueAnimator.start();
    }

    /**
     * 关闭
     * mView.getTranslationY() ：获取当前View的Y坐标
     * <p>
     * 开始的时候是显示的，此时与Top的距离为：0，即mView.getTranslationY() = 0，在Y方向上的偏移为0
     * <p>
     * 最后的时候是隐藏的，此时与Top的距离为：mView.getHeight();
     */
    public void animateOut() {
        Log.d(TAG, "animateOut()");
        mSlideAnimateTo = mViewHeight;
        /**
         * 开始偏移值
         */
        float startTranslationY = mView.getTranslationY();
        /**
         * 结束偏移值
         */
        float endTranslationY = mViewHeight;
        mValueAnimator.setFloatValues(startTranslationY, endTranslationY);
        mValueAnimator.start();
    }

    /**
     * <p>Notifies the occurrence of another frame of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float animatedValue = (float) animation.getAnimatedValue();
        Log.d(TAG, "onAnimationUpdate(),animatedValue = " + animatedValue);
        /**
         * animatedValue:The vertical position of this view relative to its top position,
         */
        mView.setTranslationY(animatedValue);
    }

    /**
     * <p>Notifies the start of the animation.</p>
     *
     * @param animation The started animation.
     */
    @Override
    public void onAnimationStart(Animator animation) {
        mView.setVisibility(View.VISIBLE);
    }

    /**
     * <p>Notifies the end of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.</p>
     *
     * @param animation The animation which reached its end.
     */
    @Override
    public void onAnimationEnd(Animator animation) {
       /* if (mSlideAnimateTo > 0) {
            mView.setVisibility(View.GONE);
        }*/
    }

    /**
     * <p>Notifies the cancellation of the animation. This callback is not invoked
     * for animations with repeat count set to INFINITE.</p>
     *
     * @param animation The animation which was canceled.
     */
    @Override
    public void onAnimationCancel(Animator animation) {

    }

    /**
     * <p>Notifies the repetition of the animation.</p>
     *
     * @param animation The animation which was repeated.
     */
    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
