package com.android.huirongzhang.todo.task;

import com.android.huirongzhang.todo.data.task.Task;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/29.
 */
public interface TaskItemListener {
    void onTaskClick(Task task);

    void onTaskDelete(List<Task> tasks);

    void onTaskUpdate(Task task);
}
