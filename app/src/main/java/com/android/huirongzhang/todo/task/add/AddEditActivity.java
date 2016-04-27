package com.android.huirongzhang.todo.task.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.huirongzhang.todo.ActivityUtils;
import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.folder.FolderRepository;
import com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource;
import com.android.huirongzhang.todo.data.local.task.TaskLocalDataSource;
import com.android.huirongzhang.todo.data.task.TaskRepository;
import com.android.huirongzhang.todo.task.TaskFragment;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_TASK = 1;

    public static final String EXTRA_FOLDER_ID = "FOLDER_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        AddEditFragment addEditStudyFragment = (AddEditFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addEditStudyFragment == null) {
            addEditStudyFragment = AddEditFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditStudyFragment, R.id.contentFrame);

            if (getIntent().hasExtra(AddEditActivity.EXTRA_FOLDER_ID)) {
                int folderId = getIntent().getIntExtra(EXTRA_FOLDER_ID, 0);
                Bundle bundle = new Bundle();
                bundle.putInt(AddEditFragment.ARGUMENT_FOLDER_ID, folderId);
                addEditStudyFragment.setArguments(bundle);
            } else {
                //
            }
        }
        // Create the presenter
        new AddEditPresenter(addEditStudyFragment, TaskRepository.getInstance(TaskLocalDataSource.getInstance(this)), FolderRepository.getInstance(FolderLocalDataSource.getInstance(this)));

    }
}
