package com.android.huirongzhang.todo.data.local.task;

import android.provider.BaseColumns;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public final class TaskContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TaskContract() {

    }

    /* Inner class that defines the table contents */
    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_TYPE = "type";
    }
}
