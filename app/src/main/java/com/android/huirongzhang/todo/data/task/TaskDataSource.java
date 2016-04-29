package com.android.huirongzhang.todo.data.task;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public interface TaskDataSource {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    void saveTask(@NonNull Task task);

    void getTasks(LoadTasksCallback callback, int folderId);

    void updateTask(String content, int taskId);
}
