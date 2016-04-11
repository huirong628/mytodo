package com.android.huirongzhang.todo.study.add;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditPresenter implements AddEditContract.Presenter {
    private AddEditContract.View mAddEditView;

    public AddEditPresenter(AddEditStudyFragment addEditStudyFragment) {
        mAddEditView = addEditStudyFragment;
        mAddEditView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addStudyTask(String description) {
        //save to DB
        mAddEditView.showStudyList();// After an edit, go back to the list.
    }
}
