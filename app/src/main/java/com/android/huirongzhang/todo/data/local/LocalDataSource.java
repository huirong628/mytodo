package com.android.huirongzhang.todo.data.local;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public abstract class LocalDataSource {
    protected DBHelper mDBHelper;

    public LocalDataSource(@NonNull Context context) {
        mDBHelper = new DBHelper(context);
    }
}
