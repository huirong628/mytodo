package com.android.huirongzhang.todo.study.add;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditPresenter implements AddEditContract.Presenter {
    private AddEditContract.View mAddEditView;

    public AddEditPresenter(AddEditFragment addEditStudyFragment) {
        mAddEditView = addEditStudyFragment;
        mAddEditView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addTask(String description) {
        //save to DB
        mAddEditView.showTaskList();// After an edit, go back to the list.
    }
}
