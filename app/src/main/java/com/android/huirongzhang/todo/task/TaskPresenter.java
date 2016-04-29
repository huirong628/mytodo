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
        updateFolder(folderId);//更新数量
        loadTasks(true, folderId);
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

    //更新folder的字段count
    private void updateFolder(int folderId) {
        mFolderDataSource.updateFolder(folderId, 0);
    }
}
