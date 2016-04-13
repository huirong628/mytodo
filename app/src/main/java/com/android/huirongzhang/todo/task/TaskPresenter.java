package com.android.huirongzhang.todo.task;

import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.data.task.TaskDataSource;

import java.util.List;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class TaskPresenter implements TaskContract.Presenter {

    private TaskContract.View mTaskView = null;

    private TaskDataSource mTaskDataSource;

    public TaskPresenter(TaskContract.View taskView, TaskDataSource taskDataSource) {
        mTaskView = taskView;
        mTaskDataSource = taskDataSource;
        mTaskView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks();
    }

    @Override
    public void loadTasks() {
        // processTasks();
        //loadTasks(forceUpdate || mFirstLoad, true);
        loadTasks(true);
    }

    @Override
    public void setAction(TaskFilterType requestType) {
        if (requestType == TaskFilterType.TASK_ADD) {
            mTaskView.showAddTask();
        }
    }

    public void loadTasks(boolean forceUpdate) {
        mTaskDataSource.getTasks(new TaskDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                processTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            processEmptyTasks();
        } else {
            mTaskView.showTasks(tasks);
        }
    }

    private void processEmptyTasks() {
        mTaskView.showNoTasks();
    }
}
