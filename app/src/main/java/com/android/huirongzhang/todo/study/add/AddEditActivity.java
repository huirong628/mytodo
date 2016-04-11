package com.android.huirongzhang.todo.study.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.huirongzhang.todo.ActivityUtils;
import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_STUDY = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        AddEditFragment addEditStudyFragment = (AddEditFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (addEditStudyFragment == null) {
            addEditStudyFragment = AddEditFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), addEditStudyFragment, R.id.contentFrame);
        }
        // Create the presenter
        new AddEditPresenter(addEditStudyFragment);
    }
}
