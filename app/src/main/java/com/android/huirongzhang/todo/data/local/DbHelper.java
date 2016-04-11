package com.android.huirongzhang.todo.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.huirongzhang.todo.data.folder.FolderPersistenceContract;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Todo.db";//database name

    private static final int DATABASE_VERSION = 1;//database version

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String SQL_CREATE_TABLE_FOLDER =
            "CREATE TABLE " + FolderPersistenceContract.FolderEntry.TABLE_NAME + " (" +
                    FolderPersistenceContract.FolderEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    FolderPersistenceContract.FolderEntry.COLUMN_NAME_TITLE + TEXT_TYPE +
                    " )";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FOLDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
