package com.android.huirongzhang.todo.task.add;

import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.data.task.TaskDataSource;
import com.android.huirongzhang.todo.data.task.TaskRepository;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditPresenter implements AddEditContract.Presenter {
    private AddEditContract.View mAddEditView;

    private TaskDataSource mTaskDataSource;

    public AddEditPresenter(AddEditFragment addEditStudyFragment, TaskDataSource taskDataSource) {
        mAddEditView = addEditStudyFragment;
        mTaskDataSource = taskDataSource;
        mAddEditView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addTask(String description, int folderId) {
        //save to DB
        Task task = new Task(description, folderId);
        mTaskDataSource.saveTask(task);

        // After an edit, go back to the list.
        mAddEditView.showTaskList();
    }
}
