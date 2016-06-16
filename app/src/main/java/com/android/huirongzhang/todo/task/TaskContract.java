package com.android.huirongzhang.todo.task;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;
import com.android.huirongzhang.todo.data.task.Task;

import java.util.List;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public interface TaskContract {

    interface View extends BaseView<Presenter> {

        void showNoTasks();

        void showTasks(List<Task> tasks);

        void showFilteringPopUpMenu();

        void showAddTask();

        void showTaskDetails(Task task);
    }

    interface Presenter extends BasePresenter {

        void loadTasks(int folderId);

        void setAction(TaskFilterType requestType);

        void openTaskDetails(Task task);

        void deleteTask(List<Task> tasks, int folderId);

        String getFolderTitle(int folderId);
    }
}
