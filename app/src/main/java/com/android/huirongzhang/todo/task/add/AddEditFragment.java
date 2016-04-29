package com.android.huirongzhang.todo.task.add;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.task.Task;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class AddEditFragment extends Fragment implements AddEditContract.View {
    public static final String ARGUMENT_FOLDER_ID = "FOLDER_ID";
    public static final String ARGUMENT_TASK_ID = "TASK_ID";
    public static final String ARGUMENT_TASK_CONTENT = "TASK_CONTENT";

    private AddEditContract.Presenter mPresenter;

    private EditText mTaskContent;

    private int mFolderId;

    private int mTaskId;

    public static AddEditFragment newInstance() {
        return new AddEditFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_edit_task_fragment, container, false);
        mTaskContent = (EditText) root.findViewById(R.id.task_content);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFolderId();
        setTask();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.study_add_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void setPresenter(AddEditContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_task_add:
                if (mTaskId != 0) {
                    mPresenter.updateTask(mTaskContent.getText().toString(), mTaskId);
                } else {
                    mPresenter.addTask(mTaskContent.getText().toString(), mFolderId);
                }
                break;
        }
        return true;
    }

    @Override
    public void showTaskList() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private void setFolderId() {
        if (getArguments() != null && getArguments().containsKey(ARGUMENT_FOLDER_ID)) {
            mFolderId = getArguments().getInt(ARGUMENT_FOLDER_ID);
        }
    }

    private void setTask() {
        if (getArguments() != null && getArguments().containsKey(ARGUMENT_TASK_ID)) {
            mTaskId = getArguments().getInt(ARGUMENT_TASK_ID);
            String taskContent = getArguments().getString(ARGUMENT_TASK_CONTENT);
            mTaskContent.setText(taskContent);
            mTaskContent.setSelection(mTaskContent.getText().length());
        }
    }
}
