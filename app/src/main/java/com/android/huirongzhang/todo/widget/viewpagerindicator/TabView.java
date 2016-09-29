package com.android.huirongzhang.todo.widget.viewpagerindicator;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by HuirongZhang on 2016/9/29.
 */

public class TabView extends TextView {
    private int mIndex;

    public TabView(Context context) {
        super(context);
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }
}
