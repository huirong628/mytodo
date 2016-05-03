package com.android.huirongzhang.todo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhanghuirong on 2016/5/3.
 */
public class TextView extends View {
    public TextView(Context context) {
        this(context, null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
