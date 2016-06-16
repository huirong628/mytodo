package com.android.huirongzhang.todo.task;

import com.android.huirongzhang.todo.data.folder.FolderDataSource;
import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.data.task.TaskDataSource;

import java.util.List;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class TaskPresenter implements TaskContract.Presenter {

    private TaskContract.View mTaskView = null;

    private TaskDataSource mTaskDataSource;

    private FolderDataSource mFolderDataSource;

    private int mTaskCount = 0;

    public TaskPresenter(TaskContract.View taskView, TaskDataSource taskDataSource, FolderDataSource folderDataSource) {
        mTaskView = taskView;
        mTaskDataSource = taskDataSource;
        mFolderDataSource = folderDataSource;
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

    @Override
    public void deleteTask(List<Task> tasks, int folderId) {
        mTaskDataSource.deleteTask(tasks);
        loadTasks(true, folderId);
        updateFolder(folderId);//更新数量
    }

    @Override
    public String getFolderTitle(int folderId) {
        return mFolderDataSource.getFolderTitle(folderId);
    }

    public void loadTasks(boolean forceUpdate, int folderId) {
        mTaskDataSource.getTasks(new TaskDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                mTaskCount = tasks.size();
                mTaskView.showTasks(tasks);
            }

            @Override
            public void onDataNotAvailable() {
                mTaskCount = 0;
                mTaskView.showNoTasks();
            }
        }, folderId);
    }

    //更新folder的字段count
    private void updateFolder(final int folderId) {
        mFolderDataSource.updateFolder(folderId, 0, mTaskCount);
    }


}
