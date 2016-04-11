package com.android.huirongzhang.todo.data.local;

import android.support.annotation.NonNull;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public interface DataSource {
    void save();

    void update();

    void delete();

    void query();
}
