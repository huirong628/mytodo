package com.android.huirongzhang.todo.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.huirongzhang.todo.data.local.folder.FolderContract;
import com.android.huirongzhang.todo.data.local.task.TaskContract;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public final class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Todo.db";//database name

    private static final int DATABASE_VERSION = 1;//database version

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_TABLE_FOLDER =
            "CREATE TABLE " + FolderContract.FolderEntry.TABLE_NAME + " (" +
                    FolderContract.FolderEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    FolderContract.FolderEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    private static final String SQL_CREATE_TABLE_TASK =
            "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                    TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID + INTEGER_TYPE + " PRIMARY KEY," +
                    TaskContract.TaskEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COLUMN_NAME_TYPE + TEXT_TYPE +
                    " )";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FOLDER);
        db.execSQL(SQL_CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
