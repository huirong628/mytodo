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
    }

    interface Presenter extends BasePresenter {

        void loadTasks();

        void setAction(TaskFilterType requestType);
    }
}
