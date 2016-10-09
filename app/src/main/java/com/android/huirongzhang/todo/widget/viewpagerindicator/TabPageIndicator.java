package com.android.huirongzhang.todo.widget.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import static android.widget.FrameLayout.LayoutParams.*;

/**
 * Created by HuirongZhang on 2016/9/29.
 */

public class TabPageIndicator extends HorizontalScrollView implements PageIndicator {
    private static final String TAG = "TabPageIndicator";

    private final IcsLinearLayout mTabLayout;

    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mListener;

    private int mMaxTabWidth;
    private int mSelectedTabIndex;

    private VelocityTracker mVelocityTracker;

    public TabPageIndicator(Context context) {
        this(context, null);
    }

    public TabPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTabLayout = new IcsLinearLayout(context, null);
        addView(mTabLayout, new LayoutParams(WRAP_CONTENT, MATCH_PARENT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mListener != null) {
            mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (mListener != null) {
            mListener.onPageScrollStateChanged(state);
        }
    }

    /**
     * Bind the indicator to a ViewPager
     *
     * @param viewPager
     */
    @Override
    public void setViewPager(ViewPager viewPager) {
        if (mViewPager == viewPager) {
            return;
        }

        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(null);
        }

        mViewPager = viewPager;
        //viewPager.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * Set the current page of both the ViewPager and indicator.
     *
     * @param item
     */
    @Override
    public void setCurrentItem(int item) {
        if (mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        mSelectedTabIndex = item;
        mViewPager.setCurrentItem(item);
    }

    @Override
    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();
        PagerAdapter adapter = mViewPager.getAdapter();
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = adapter.getPageTitle(i);
            addTab(i, title);
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    /**
     * Set a page listener which will receive forward events.
     *
     * @param listener
     */
    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mListener = listener;
    }

    private void addTab(int position, CharSequence title) {
        final TabView tabView = new TabView(getContext());
        tabView.setIndex(position);
        tabView.setText(title);
        tabView.setGravity(Gravity.CENTER);
        tabView.setOnClickListener(mTabClickListener);
        mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, MATCH_PARENT, 1));
    }

    private final OnClickListener mTabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            TabView tabView = (TabView) v;
            final int oldSelected = mViewPager.getCurrentItem();
            final int newSelected = tabView.getIndex();
            mViewPager.setCurrentItem(newSelected);
            if (oldSelected == newSelected) {

            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        mVelocityTracker.computeCurrentVelocity(1000);
        int xVelocity = (int) mVelocityTracker.getXVelocity();
        int yVelocity = (int) mVelocityTracker.getYVelocity();
        Log.d(TAG, "xVelocity = " + xVelocity + ",yVelocity = " + yVelocity);
        return super.onTouchEvent(ev);
    }
}