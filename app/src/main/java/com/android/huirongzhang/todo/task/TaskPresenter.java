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

    }

    @Override
    public void loadTasks(int folderId) {
        // processTasks();
        //loadTasks(forceUpdate || mFirstLoad, true);
        loadTasks(true, folderId);
    }

    @Override
    public void setAction(TaskFilterType requestType) {
        if (requestType == TaskFilterType.TASK_ADD) {
            mTaskView.showAddTask();
        }
    }

    @Override
    public void openTaskDetails(Task task) {
        //
        mTaskView.showTaskDetails(task);
    }

    public void loadTasks(boolean forceUpdate, int folderId) {
        mTaskDataSource.getTasks(new TaskDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                mTaskView.showTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                mTaskView.showNoTasks();
            }
        }, folderId);
    }
}
