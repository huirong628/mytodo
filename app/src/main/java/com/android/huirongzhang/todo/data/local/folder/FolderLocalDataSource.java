package com.android.huirongzhang.todo.data.local.folder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.data.folder.FolderDataSource;
import com.android.huirongzhang.todo.data.local.DBHelper;
import com.android.huirongzhang.todo.data.local.folder.FolderContract.FolderEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/12.
 */
public class FolderLocalDataSource implements FolderDataSource {

    private static FolderLocalDataSource INSTANCE;

    private DBHelper mDBHelper;

    // Prevent direct instantiation.
    private FolderLocalDataSource(@NonNull Context context) {
        mDBHelper = new DBHelper(context);
    }

    public static FolderLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FolderLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void saveFolder(@NonNull Folder folder) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FolderEntry.COLUMN_NAME_ENTRY_ID, folder.getId());
        values.put(FolderEntry.COLUMN_NAME_TITLE, folder.getTitle());

        db.insert(FolderEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void getFolders(LoadFoldersCallback callback) {
        List<Folder> folders = new ArrayList<Folder>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                FolderEntry.COLUMN_NAME_ENTRY_ID,
                FolderEntry.COLUMN_NAME_TITLE
        };

        Cursor c = db.query(
                FolderEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_TITLE));
                Folder folder = new Folder(itemId, title);
                folders.add(folder);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (folders.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onFoldersLoaded(folders);
        }
    }
}
