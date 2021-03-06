package com.android.huirongzhang.todo.task;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.android.huirongzhang.todo.ActivityUtils;
import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.folder.FolderRepository;
import com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource;
import com.android.huirongzhang.todo.data.local.task.TaskLocalDataSource;
import com.android.huirongzhang.todo.data.task.TaskRepository;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_FOLDER_ID = "FOLDER_ID";
    public static final String EXTRA_FOLDER_TITLE = "FOLDER_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // Get the requested folder id

        TaskFragment taskFragment = null;//(TaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (taskFragment == null) {
            taskFragment = TaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), taskFragment, R.id.contentFrame);
            if (getIntent().hasExtra(TaskActivity.EXTRA_FOLDER_ID)) {
                int folderId = getIntent().getIntExtra(EXTRA_FOLDER_ID, 0);
                String title = getIntent().getStringExtra(EXTRA_FOLDER_TITLE);
                actionBar.setTitle(title);
                Bundle bundle = new Bundle();
                bundle.putInt(TaskFragment.ARGUMENT_FOLDER_ID, folderId);
                taskFragment.setArguments(bundle);
            } else {
                actionBar.setTitle("");
            }
        }

        new TaskPresenter(taskFragment, TaskRepository.getInstance(TaskLocalDataSource.getInstance(this)), FolderRepository.getInstance(FolderLocalDataSource.getInstance(this)));

        // Create the presenter
//        new TaskDetailPresenter(
//                taskId,
//                Injection.provideTasksRepository(getApplicationContext()),
//                taskDetailFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
