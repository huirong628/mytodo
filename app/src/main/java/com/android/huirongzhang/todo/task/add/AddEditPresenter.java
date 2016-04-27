package com.android.huirongzhang.todo.task.add;

import com.android.huirongzhang.todo.data.folder.FolderDataSource;
import com.android.huirongzhang.todo.data.folder.FolderRepository;
import com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource;
import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.data.task.TaskDataSource;
import com.android.huirongzhang.todo.data.task.TaskRepository;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditPresenter implements AddEditContract.Presenter {
    private AddEditContract.View mAddEditView;

    private TaskDataSource mTaskDataSource;

    private FolderDataSource mFolderDataSource;

    public AddEditPresenter(AddEditFragment addEditStudyFragment, TaskDataSource taskDataSource, FolderDataSource folderDataSource) {
        mAddEditView = addEditStudyFragment;
        mTaskDataSource = taskDataSource;
        mAddEditView.setPresenter(this);
        mFolderDataSource = folderDataSource;
    }

    @Override
    public void start() {

    }

    @Override
    public void addTask(String description, int folderId) {
        //save to DB
        Task task = new Task(description, folderId);
        mTaskDataSource.saveTask(task);

        updateFolder(folderId);

        // After an edit, go back to the list.
        mAddEditView.showTaskList();
    }

    //更新folder的字段count
    private void updateFolder(int folderId) {
        mFolderDataSource.updateFolder(folderId);
    }
}
