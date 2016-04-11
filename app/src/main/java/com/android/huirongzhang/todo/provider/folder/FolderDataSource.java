package com.android.huirongzhang.todo.provider.folder;

import com.android.huirongzhang.todo.provider.DBHelper;
import com.android.huirongzhang.todo.data.local.DataSource;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class FolderDataSource implements DataSource {

    private DBHelper mDBHelper;

//    @Override
//    public void save(Folder folder) {
//        SQLiteDatabase db = mDBHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(FolderContract.FolderEntry._ID, folder.getId());
//        values.put(FolderContract.FolderEntry.COLUMN_NAME_TITLE, folder.getTitle());
//
//        db.insert(FolderContract.FolderEntry.TABLE_NAME, null, values);
//
//        db.close();
//    }

    @Override
    public void save() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void query() {

    }
}
