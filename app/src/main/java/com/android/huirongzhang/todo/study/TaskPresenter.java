package com.android.huirongzhang.todo.study;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class TaskPresenter implements TaskContract.Presenter {

    private TaskContract.View mTaskView = null;

    public void setView(TaskContract.View taskView) {
        mTaskView = taskView;
        mTaskView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks();
    }


    @Override
    public void loadTasks() {
        processTasks();
    }

    @Override
    public void setAction(TaskFilterType requestType) {
        if (requestType == TaskFilterType.TASK_ADD) {
            mTaskView.showAddTask();
        }
    }

    private void processTasks() {
        if (true) {
            processEmptyTasks();
        } else {
            mTaskView.showTasks();
        }
    }

    private void processEmptyTasks() {
        mTaskView.showNoTasks();
    }
}
