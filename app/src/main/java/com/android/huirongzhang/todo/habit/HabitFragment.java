package com.android.huirongzhang.todo.habit;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/5/3.
 */
public class HabitFragment extends Fragment implements View.OnClickListener {

    private ListView mListView;
    // private HabitAdapter mHabitAdapter;
    //private RewardAdapter mRewardAdapter;
    private LikeAdapter mLikeAdapter;

    public static HabitFragment newInstance() {
        return new HabitFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  mHabitAdapter = new HabitAdapter();
        // mRewardAdapter = new RewardAdapter(getContext());
        mLikeAdapter = new LikeAdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.habit_fragment, container, false);

        mListView = (ListView) root.findViewById(R.id.habit_list);
        mListView.setDividerHeight(0);
        // mListView.setAdapter(mRewardAdapter);
        //   mListView.setAdapter(mHabitAdapter);
        mListView.setAdapter(mLikeAdapter);

        TextView mAddHabit = (TextView) root.findViewById(R.id.add_habit);
        mAddHabit.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mAddHabit.setOnClickListener(this);
        mAddHabit.setVisibility(View.GONE);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_habit) {
            showAddHabitDialog();
        }
    }

    private void showAddHabitDialog() {

    }
}
