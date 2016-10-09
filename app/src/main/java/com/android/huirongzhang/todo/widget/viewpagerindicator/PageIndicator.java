package com.android.huirongzhang.todo.widget.viewpagerindicator;

import android.support.v4.view.ViewPager;

/**
 * Created by HuirongZhang on 2016/9/29.
 */

public interface PageIndicator extends ViewPager.OnPageChangeListener {

    /**
     * Bind the indicator to a ViewPager
     *
     * @param viewPager
     */
    void setViewPager(ViewPager viewPager);

    /**
     * Set the current page of both the ViewPager and indicator.
     *
     * @param item
     */
    void setCurrentItem(int item);


    /**
     * Notify the indicator that the fragment list has changed.
     */
    void notifyDataSetChanged();

    /**
     * Set a page listener which will receive forward events.
     *
     * @param listener
     */
    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

}
