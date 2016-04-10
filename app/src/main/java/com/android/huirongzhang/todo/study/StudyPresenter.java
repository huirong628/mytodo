package com.android.huirongzhang.todo.study;

import android.view.View;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class StudyPresenter implements StudyContract.Presenter {

    private StudyContract.View mStudyView = null;

    public void setView(StudyContract.View studyView) {
        mStudyView = studyView;
        mStudyView.setPresenter(this);
    }

    @Override
    public void start() {
        loadTasks();
    }


    @Override
    public void loadTasks() {
        processTasks();
    }

    private void processTasks() {
        if (true) {
            processEmptyTasks();
        } else {
            mStudyView.showTasks();
        }
    }

    private void processEmptyTasks() {
        mStudyView.showNoTasks();
    }
}
