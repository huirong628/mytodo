package com.android.huirongzhang.todo.data.local.folder;

import android.provider.BaseColumns;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public final class FolderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FolderContract() {

    }

    /* Inner class that defines the table contents */
    public static abstract class FolderEntry implements BaseColumns {
        public static final String TABLE_NAME = "folder";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_COUNT = "count";
    }
}
