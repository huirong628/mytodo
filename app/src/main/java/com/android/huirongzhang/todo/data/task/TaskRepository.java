package com.android.huirongzhang.todo.data.task;

import android.support.annotation.NonNull;

import com.android.huirongzhang.todo.data.local.task.TaskLocalDataSource;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class TaskRepository implements TaskDataSource {

    private static TaskRepository INSTANCE = null;

    private final TaskDataSource mTaskLocalDataSource;

    // Prevent direct instantiation.
    private TaskRepository(@NonNull TaskDataSource taskDataSource) {
        mTaskLocalDataSource = taskDataSource;
    }

    public static TaskRepository getInstance(TaskDataSource taskDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TaskRepository(taskDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void saveTask(@NonNull Task task) {
        mTaskLocalDataSource.saveTask(task);
    }

    @Override
    public void getTasks(LoadTasksCallback callback, int folderId) {
        mTaskLocalDataSource.getTasks(callback, folderId);
    }

    @Override
    public void updateTask(String content, int taskId) {
        mTaskLocalDataSource.updateTask(content, taskId);
    }

    @Override
    public void deleteTask(List<Task> tasks) {
        mTaskLocalDataSource.deleteTask(tasks);
    }
}
