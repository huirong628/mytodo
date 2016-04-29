package com.android.huirongzhang.todo.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.huirongzhang.todo.ActivityUtils;
import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class TaskAdapter extends BaseAdapter {

    private List<Task> mTasks;

    private boolean mEditMode = false;

    private TaskItemListener mItemListener;

    private static HashMap<Integer, Boolean> mIsSelected;//保存是否选中的状态

    //delete task
    private List<Task> mDeleteTasks = null;

    public TaskAdapter(List<Task> tasks) {
        setData(tasks);
    }

    public void setData(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
    }

    public void showEditMode(boolean editMode) {
        mEditMode = editMode;
        //每次进入编辑状态都进行初始化
        mIsSelected = new HashMap<Integer, Boolean>();
        mDeleteTasks = new ArrayList<Task>();
        for (int i = 0; i < mTasks.size(); i++) {
            mIsSelected.put(mTasks.get(i).getId(), false);
        }
        notifyDataSetChanged();
    }

    public void setItemListener(TaskItemListener itemListener) {

        mItemListener = itemListener;
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int i) {
        return mTasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convert, ViewGroup viewGroup) {
        View view = convert;
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
            viewHolder.editView = view.findViewById(R.id.task_cb_layout);
            viewHolder.editCBView = (CheckBox) view.findViewById(R.id.task_cb);
            viewHolder.contentView = (TextView) view.findViewById(R.id.task_content);
            viewHolder.dateView = (TextView) view.findViewById(R.id.task_date);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Task task = (Task) getItem(i);

        viewHolder.contentView.setText(task.getContent());
        viewHolder.dateView.setText(task.getDate());


        if (mEditMode) {
            //重新设置布局
            view.setClickable(false);
            viewHolder.contentView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 0), 0, 0, 0);
            viewHolder.dateView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 0), 0, 0, 0);
            viewHolder.editView.setVisibility(View.VISIBLE);
            viewHolder.editCBView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsSelected.get(task.getId())) {
                        mIsSelected.put(task.getId(), false);
                        mDeleteTasks.remove(task);
                    } else {
                        mIsSelected.put(task.getId(), true);
                        mDeleteTasks.add(task);
                    }

                    mItemListener.onTaskDelete(mDeleteTasks);
                }
            });
            viewHolder.editCBView.setChecked(mIsSelected.get(task.getId()));
        } else {
            view.setClickable(true);
            viewHolder.contentView.setClickable(false);
            viewHolder.editView.setVisibility(View.GONE);
            viewHolder.contentView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 16), 0, 0, 0);
            viewHolder.dateView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 16), 0, 0, 0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open folder
                    mItemListener.onTaskClick(task);
                }
            });
        }
        return view;
    }

    private static class ViewHolder {
        View editView;
        CheckBox editCBView;
        TextView contentView;
        TextView dateView;
    }
}
