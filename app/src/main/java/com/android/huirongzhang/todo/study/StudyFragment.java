package com.android.huirongzhang.todo.study;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.study.add.AddEditStudyActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class StudyFragment extends Fragment implements StudyContract.View {

    private StudyContract.Presenter mPresenter;

    private LinearLayout mTasksView;

    private View mNoTasksVIew;

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
        View root = inflater.inflate(R.layout.study_fragment, container, false);

        //set up tasks view
        ListView listView = (ListView) root.findViewById(R.id.tasks_list);
        mTasksView = (LinearLayout) root.findViewById(R.id.tasks);

        //set up no tasks view
        mNoTasksVIew = root.findViewById(R.id.noTasks);

        //set up floating action button
        // FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_tasks);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.study_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_study_filter:
                showFilteringPopUpMenu();
                break;
        }
        return true;
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(R.id.menu_study_filter));
        popup.getMenuInflater().inflate(R.menu.menu_study_filter_tasks, popup.getMenu());
        setIconEnable(popup);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_study_task_add:
                        mPresenter.setAction(StudyFilterType.STUDY_ADD);
                        break;
                    case R.id.menu_study_task_completed:
                        mPresenter.setAction(StudyFilterType.STUDY_COMPLETED);
                        break;
                    case R.id.menu_study_task_delete:
                        mPresenter.setAction(StudyFilterType.STUDY_DELETE);
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
    public void setPresenter(StudyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNoTasks() {
        mTasksView.setVisibility(View.GONE);
        mNoTasksVIew.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTasks() {
        mTasksView.setVisibility(View.VISIBLE);
        mNoTasksVIew.setVisibility(View.GONE);
    }

    @Override
    public void showAddStudy() {
        Intent intent = new Intent(getActivity(), AddEditStudyActivity.class);
        startActivityForResult(intent, AddEditStudyActivity.REQUEST_ADD_STUDY);
    }
}
