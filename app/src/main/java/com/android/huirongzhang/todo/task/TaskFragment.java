package com.android.huirongzhang.todo.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.data.task.Task;
import com.android.huirongzhang.todo.task.add.AddEditActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class TaskFragment extends Fragment implements TaskContract.View, View.OnClickListener {

    public static final String ARGUMENT_FOLDER_ID = "FOLDER_ID";

    private TaskContract.Presenter mPresenter;

    private LinearLayout mTasksView;

    private View mNoTasksView;

    private int mFolderId;

    private TaskAdapter mListAdapter;

    private ImageView mCreateView;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    public TaskFragment() {
        //
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListAdapter = new TaskAdapter(new ArrayList<Task>(0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_fragment, container, false);

        mCreateView = (ImageView) root.findViewById(R.id.task_create);
        mCreateView.setOnClickListener(this);

        //set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.tasks_list);
        mTasksView = (LinearLayout) root.findViewById(R.id.tasks);
        listView.setAdapter(mListAdapter);
        //set up no tasks view
        mNoTasksView = root.findViewById(R.id.noTasks);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //get task type
        setFolderId();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadTasks(mFolderId);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.task_create) {
            mPresenter.setAction(TaskFilterType.TASK_ADD);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_task_filter:
                showFilteringPopUpMenu();
                break;
        }
        return true;
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(R.id.menu_task_filter));
        popup.getMenuInflater().inflate(R.menu.menu_study_filter_tasks, popup.getMenu());
        setIconEnable(popup);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_folder_task_add:
                        mPresenter.setAction(TaskFilterType.TASK_ADD);
                        break;
                    case R.id.menu_study_task_completed:
                        mPresenter.setAction(TaskFilterType.TASK_COMPLETED);
                        break;
                    case R.id.menu_study_task_delete:
                        mPresenter.setAction(TaskFilterType.TASK_DELETE);
                        break;
                    default:
                        break;
                }
                //mPresenter.loadTasks(false);
                return true;
            }
        });

        popup.show();
    }

    ////使用反射,强制显示菜单图标。
    private void setIconEnable(PopupMenu menu) {
        try {
            Field field = menu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper mHelper = (MenuPopupHelper) field.get(menu);
            mHelper.setForceShowIcon(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPresenter(TaskContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNoTasks() {
        mTasksView.setVisibility(View.GONE);
        mNoTasksView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksView.setVisibility(View.GONE);
        mListAdapter.setData(tasks);
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getActivity(), AddEditActivity.class);
        intent.putExtra(AddEditActivity.EXTRA_FOLDER_ID, mFolderId);
        startActivityForResult(intent, AddEditActivity.REQUEST_ADD_TASK);
    }

    private void setFolderId() {
        if (getArguments() != null && getArguments().containsKey(ARGUMENT_FOLDER_ID)) {
            mFolderId = getArguments().getInt(ARGUMENT_FOLDER_ID);
        }
    }
}
