package com.android.huirongzhang.todo.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.huirongzhang.todo.ActivityUtils;
import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.data.task.Task;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class TaskAdapter extends BaseAdapter {

    private List<Task> mTasks;

    private boolean mEditMode = false;

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


        if (mEditMode) {
            //重新设置布局
            view.setClickable(false);
            viewHolder.contentView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 0), 0, 0, 0);
            viewHolder.dateView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 0), 0, 0, 0);
            viewHolder.editView.setVisibility(View.VISIBLE);
            viewHolder.editCBView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (mIsSelected.get(folder.getId())) {
//                        mIsSelected.put(folder.getId(), false);
//                        mDeleteFolders.remove(folder);
//                    } else {
//                        mIsSelected.put(folder.getId(), true);
//                        mDeleteFolders.add(folder);
//                    }
//
//                    mItemListener.onFolderDelete(mDeleteFolders);
                }
            });
            //  viewHolder.editCBView.setChecked(mIsSelected.get(folder.getId()));
            viewHolder.contentView.setClickable(true);
            viewHolder.contentView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //rename folder
                    //  mItemListener.onFolderUpdate(folder);
                }
            });
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
                    // mItemListener.onFolderClick(folder);
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
