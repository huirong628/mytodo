package com.android.huirongzhang.todo.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.task.Task;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class TaskAdapter extends BaseAdapter {

    private List<Task> mTasks;

    public TaskAdapter(List<Task> tasks) {
        setData(tasks);
    }

    public void setData(List<Task> tasks) {
        mTasks = tasks;
        notifyDataSetChanged();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.folder_item, viewGroup, false);
        }

        final Task task = (Task) getItem(i);

        TextView titleTV = (TextView) rowView.findViewById(R.id.folder_name);
        titleTV.setText(task.getContent());

        return rowView;
    }
}
