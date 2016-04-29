package com.android.huirongzhang.todo.data.local.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.android.huirongzhang.todo.data.local.LocalDataSource;
import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.data.task.TaskDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
//        values.put(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_CONTENT, task.getContent());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_TYPE, task.getType());
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public void getTasks(LoadTasksCallback callback, int folderId) {
        List<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] projection = {
                TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID,
                TaskContract.TaskEntry.COLUMN_NAME_CONTENT,
                TaskContract.TaskEntry.COLUMN_NAME_DATE,
                TaskContract.TaskEntry.COLUMN_NAME_TYPE,
        };

        String selection = TaskContract.TaskEntry.COLUMN_NAME_TYPE + " = " + folderId;
        String orderBy = TaskContract.TaskEntry.COLUMN_NAME_DATE + " desc";
        Cursor c = db.query(
                TaskContract.TaskEntry.TABLE_NAME, projection, selection, null, null, null, orderBy);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                int itemId = c.getInt(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID));
                String content = c.getString(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_CONTENT));
                String date = c.getString(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_DATE));
                int type = c.getInt(c.getColumnIndexOrThrow(TaskContract.TaskEntry.COLUMN_NAME_TYPE));
                Task task = new Task(itemId, content, type);
                task.setDate(date);
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

    @Override
    public void updateTask(String content, int taskId) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_NAME_CONTENT, content);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID, taskId);
        values.put(TaskContract.TaskEntry.COLUMN_NAME_DATE, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String whereClause = TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " = " + taskId;

        db.update(TaskContract.TaskEntry.TABLE_NAME, values, whereClause, null);

        db.close();
    }

    @Override
    public void deleteTask(List<Task> tasks) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String whereClause = TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID + "=?";
        int length = tasks.size();
        for (int i = 0; i < length; i++) {
            String[] whereArgs = new String[]{tasks.get(i).getId() + ""};
            db.delete(TaskContract.TaskEntry.TABLE_NAME, whereClause, whereArgs);
        }
        db.close();
    }
}
