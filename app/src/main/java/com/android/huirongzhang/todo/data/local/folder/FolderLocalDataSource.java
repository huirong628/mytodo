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
        values.put(FolderEntry.COLUMN_NAME_TITLE, folder.getTitle());
        values.put(FolderEntry.COLUMN_NAME_COUNT, folder.getCount());

        db.insert(FolderEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void getFolders(LoadFoldersCallback callback) {
        List<Folder> folders = new ArrayList<Folder>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                FolderEntry.COLUMN_NAME_ENTRY_ID,
                FolderEntry.COLUMN_NAME_TITLE,
                FolderEntry.COLUMN_NAME_COUNT
        };

        Cursor c = db.query(
                FolderEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int itemId = c.getInt(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_ENTRY_ID));
                String title = c.getString(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_TITLE));
                int count = c.getInt(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_COUNT));
                Folder folder = new Folder(itemId, title);
                folder.setCount(count);
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

    @Override
    public void updateFolder(@NonNull int id, String title) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FolderEntry.COLUMN_NAME_ENTRY_ID, id);
        values.put(FolderEntry.COLUMN_NAME_TITLE, title);
        String whereClause = FolderEntry.COLUMN_NAME_ENTRY_ID + "=" + id;
        String[] whereArgs = null;
        db.update(FolderEntry.TABLE_NAME, values, whereClause, whereArgs);

        db.close();
    }

    @Override
    public void updateFolder(@NonNull int id, int type, int taskCount) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FolderEntry.COLUMN_NAME_ENTRY_ID, id);
        if (type == 0) {//0代表删除操作
            values.put(FolderEntry.COLUMN_NAME_COUNT, taskCount);//删除个数问题,重新获取id文件夹下task的数量
        } else {//1代表增加操作
            values.put(FolderEntry.COLUMN_NAME_COUNT, getTaskCount(id) + 1);
        }
        String whereClause = FolderEntry.COLUMN_NAME_ENTRY_ID + "=" + id;
        String[] whereArgs = null;
        db.update(FolderEntry.TABLE_NAME, values, whereClause, whereArgs);

        db.close();
    }

    @Override
    public void deleteFolder(@NonNull List<Folder> folders) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String whereClause = FolderEntry.COLUMN_NAME_ENTRY_ID + "=?";
        int length = folders.size();
        // String[] whereArgs = new String[]{};//数组初始化
        for (int i = 0; i < length; i++) {
//            whereArgs[i] = folders.get(i).getId() + "";
            String[] whereArgs = new String[]{folders.get(i).getId() + ""};
            db.delete(FolderEntry.TABLE_NAME, whereClause, whereArgs);
        }
        db.close();
    }

    @Override
    public String getFolderTitle(int folderId) {
        String folderTitle = "";
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                FolderEntry.COLUMN_NAME_TITLE
        };

        String selection = FolderEntry.COLUMN_NAME_ENTRY_ID + "=?";

        String[] selectionArgs = new String[]{folderId + ""};

        Cursor c = db.query(
                FolderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                folderTitle = c.getString(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_TITLE));
            }
        }
        if (c != null) {
            c.close();
        }
        return folderTitle;
    }

    private int getTaskCount(int id) {
        int taskCount = 0;
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                FolderEntry.COLUMN_NAME_COUNT
        };

        String selection = FolderEntry.COLUMN_NAME_ENTRY_ID + "=?";

        String[] selectionArgs = new String[]{id + ""};

        Cursor c = db.query(
                FolderEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                taskCount = c.getInt(c.getColumnIndexOrThrow(FolderEntry.COLUMN_NAME_COUNT));
            }
        }
        if (c != null) {
            c.close();
        }

        // db.close();
        return taskCount;
    }
/**
 * 04-27 14:05:14.114 8111-8111/com.android.huirongzhang.todo E/AndroidRuntime: FATAL EXCEPTION: main
 Process: com.android.huirongzhang.todo, PID: 8111
 java.lang.IllegalArgumentException: Too many bind arguments.  2 arguments were provided but the statement needs 1 arguments.
 at android.database.sqlite.SQLiteProgram.<init>(SQLiteProgram.java:68)
 at android.database.sqlite.SQLiteStatement.<init>(SQLiteStatement.java:31)
 at android.database.sqlite.SQLiteDatabase.delete(SQLiteDatabase.java:1494)
 at com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource.deleteFolder(FolderLocalDataSource.java:108)
 at com.android.huirongzhang.todo.data.folder.FolderRepository.deleteFolder(FolderRepository.java:47)
 at com.android.huirongzhang.todo.folder.FolderPresenter.deleteFolder(FolderPresenter.java:66)
 at com.android.huirongzhang.todo.folder.FolderFragment.onClick(FolderFragment.java:96)
 at android.view.View.performClick(View.java:4456)
 at android.view.View$PerformClick.run(View.java:18482)
 at android.os.Handler.handleCallback(Handler.java:733)
 at android.os.Handler.dispatchMessage(Handler.java:95)
 at android.os.Looper.loop(Looper.java:136)
 at android.app.ActivityThread.main(ActivityThread.java:5097)
 at java.lang.reflect.Method.invokeNative(Native Method)
 at java.lang.reflect.Method.invoke(Method.java:515)
 at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:785)
 at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:601)
 at dalvik.system.NativeStart.main(Native Method)
 *
 */

/**
 * 发生了死循环
 *
 * 06-16 11:42:11.368 18679-18679/com.android.huirongzhang.todo E/AndroidRuntime: FATAL EXCEPTION: main
 Process: com.android.huirongzhang.todo, PID: 18679
 java.lang.StackOverflowError
 at java.util.HashMap.secondaryHash(HashMap.java:350)
 at java.util.LinkedHashMap.get(LinkedHashMap.java:251)
 at android.util.LruCache.get(LruCache.java:118)
 at android.database.sqlite.SQLiteConnection.acquirePreparedStatement(SQLiteConnection.java:877)
 at android.database.sqlite.SQLiteConnection.executeForLong(SQLiteConnection.java:591)
 at android.database.sqlite.SQLiteConnection.setPageSize(SQLiteConnection.java:251)
 at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:213)
 at android.database.sqlite.SQLiteConnection.open(SQLiteConnection.java:193)
 at android.database.sqlite.SQLiteConnectionPool.openConnectionLocked(SQLiteConnectionPool.java:463)
 at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:185)
 at android.database.sqlite.SQLiteConnectionPool.open(SQLiteConnectionPool.java:177)
 at android.database.sqlite.SQLiteDatabase.openInner(SQLiteDatabase.java:804)
 at android.database.sqlite.SQLiteDatabase.open(SQLiteDatabase.java:789)
 at android.database.sqlite.SQLiteDatabase.openDatabase(SQLiteDatabase.java:694)
 at android.app.ContextImpl.openOrCreateDatabase(ContextImpl.java:1073)
 at android.content.ContextWrapper.openOrCreateDatabase(ContextWrapper.java:256)
 at android.database.sqlite.SQLiteOpenHelper.getDatabaseLocked(SQLiteOpenHelper.java:224)
 at android.database.sqlite.SQLiteOpenHelper.getReadableDatabase(SQLiteOpenHelper.java:188)
 at com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource.getFolders(FolderLocalDataSource.java:54)
 at com.android.huirongzhang.todo.data.folder.FolderRepository.getFolders(FolderRepository.java:37)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:85)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:44)
 at com.android.huirongzhang.todo.folder.FolderPresenter.start(FolderPresenter.java:27)
 at com.android.huirongzhang.todo.folder.FolderFragment.showNoFolders(FolderFragment.java:201)
 at com.android.huirongzhang.todo.folder.FolderPresenter$1.onDataNotAvailable(FolderPresenter.java:94)
 at com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource.getFolders(FolderLocalDataSource.java:83)
 at com.android.huirongzhang.todo.data.folder.FolderRepository.getFolders(FolderRepository.java:37)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:85)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:44)
 at com.android.huirongzhang.todo.folder.FolderPresenter.start(FolderPresenter.java:27)
 at com.android.huirongzhang.todo.folder.FolderFragment.showNoFolders(FolderFragment.java:201)
 at com.android.huirongzhang.todo.folder.FolderPresenter$1.onDataNotAvailable(FolderPresenter.java:94)
 at com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource.getFolders(FolderLocalDataSource.java:83)
 at com.android.huirongzhang.todo.data.folder.FolderRepository.getFolders(FolderRepository.java:37)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:85)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:44)
 at com.android.huirongzhang.todo.folder.FolderPresenter.start(FolderPresenter.java:27)
 at com.android.huirongzhang.todo.folder.FolderFragment.showNoFolders(FolderFragment.java:201)
 at com.android.huirongzhang.todo.folder.FolderPresenter$1.onDataNotAvailable(FolderPresenter.java:94)
 at com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource.getFolders(FolderLocalDataSource.java:83)
 at com.android.huirongzhang.todo.data.folder.FolderRepository.getFolders(FolderRepository.java:37)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:85)
 at com.android.huirongzhang.todo.folder.FolderPresenter.loadFolders(FolderPresenter.java:44)
 at com.android.huirongzhang.todo.folder.FolderPresenter.start(F

 */
}

