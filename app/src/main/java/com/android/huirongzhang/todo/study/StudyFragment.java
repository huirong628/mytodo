package com.android.huirongzhang.todo.study;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

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
        ;

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
}
