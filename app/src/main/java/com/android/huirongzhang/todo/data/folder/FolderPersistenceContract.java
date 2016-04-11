package com.android.huirongzhang.todo.data.folder;

import android.provider.BaseColumns;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class FolderPersistenceContract {

    public static abstract class FolderEntry implements BaseColumns {
        public static final String TABLE_NAME = "folder";
        public static final String COLUMN_NAME_TITLE = "title";
    }
}
