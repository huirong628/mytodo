package com.android.huirongzhang.todo.task.add;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public interface AddEditContract {
    interface View extends BaseView<Presenter> {

        void showTaskList();
    }

    interface Presenter extends BasePresenter {

        void addTask(String description, int folderId);
    }
}
