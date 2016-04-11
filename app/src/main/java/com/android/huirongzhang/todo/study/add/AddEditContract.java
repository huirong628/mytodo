package com.android.huirongzhang.todo.study.add;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;
import com.android.huirongzhang.todo.study.StudyFilterType;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public interface AddEditContract {
    interface View extends BaseView<Presenter> {

        void showStudyList();
    }

    interface Presenter extends BasePresenter {

        void addStudyTask(String description);
    }
}
