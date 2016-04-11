package com.android.huirongzhang.todo.task;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public interface TaskContract {

    interface View extends BaseView<Presenter> {

        void showNoTasks();

        void showTasks();

        void showFilteringPopUpMenu();

        void showAddTask();
    }

    interface Presenter extends BasePresenter {

        void loadTasks();

        void setAction(TaskFilterType requestType);
    }
}
