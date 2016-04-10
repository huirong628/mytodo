package com.android.huirongzhang.todo.study;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public interface StudyContract {

    interface View extends BaseView<Presenter> {

        void showNoTasks();

        void showTasks();

        void showFilteringPopUpMenu();

    }

    interface Presenter extends BasePresenter {

        void loadTasks();
    }
}
