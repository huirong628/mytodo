package com.android.huirongzhang.todo.data.local.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.data.local.LocalDataSource;
import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.data.task.TaskDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class TaskLocalDataSource extends LocalDataSource implements TaskDataSource {

    private static TaskLocalDataSource INSTANCE;

    private TaskLocalDataSource(@NonNull Context context) {
        super(context);
    }

    public static TaskLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TaskLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_CONTENT, task.getContent());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TYPE, task.getType());
        db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void getTasks(LoadTasksCallback callback) {
        List<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskContract.TaskEntry.COLUMN_NAME_CONTENT,
                TaskContract.TaskEntry.COLUMN_NAME_TYPE,
        };

        Cursor c = db.query(
                TaskContract.TaskEntry.TABLE_NAME, projection, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String itemId = c.getString(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID));
                String content = c.getString(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_CONTENT));
                String type = c.getString(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_TYPE));
                Task task = new Task(itemId, content, type);
                tasks.add(task);
            }
        }
        if (c != null) {
            c.close();
        }

        db.close();

        if (tasks.isEmpty()) {
            // This will be called if the table is new or just empty.
            callback.onDataNotAvailable();
        } else {
            callback.onTasksLoaded(tasks);
        }
    }
}
