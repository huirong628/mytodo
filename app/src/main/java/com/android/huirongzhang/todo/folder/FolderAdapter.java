package com.android.huirongzhang.todo.folder;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/12.
 */
public class FolderAdapter extends BaseAdapter {

    private List<Folder> mFolders;

    private FolderItemListener mItemListener;

    private boolean mEditMode = false;

    public FolderAdapter(ArrayList<Folder> folders) {
        setList(folders);
    }

    public void replaceData(List<Folder> folders) {
        setList(folders);
        notifyDataSetChanged();
    }

    public void showEditMode(boolean editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public void setItemListener(FolderItemListener itemListener) {
        mItemListener = itemListener;
    }

    private void setList(List<Folder> folders) {
        mFolders = folders;
    }

    @Override
    public int getCount() {
        return mFolders.size();
    }

    @Override
    public Object getItem(int i) {
        return mFolders.get(i);
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.folder_item, viewGroup, false);
            viewHolder.editView = view.findViewById(R.id.folder_cb_layout);
            viewHolder.edit = (CheckBox) view.findViewById(R.id.folder_cb);
            viewHolder.name = (TextView) view.findViewById(R.id.folder_name);
            viewHolder.num = (TextView) view.findViewById(R.id.task_num);
            viewHolder.chevronRight = (ImageView) view.findViewById(R.id.chevron_right);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Folder folder = (Folder) getItem(i);

        viewHolder.name.setText(folder.getTitle());

        if (mEditMode) {
            //重新设置布局
            view.setClickable(false);
            viewHolder.name.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 0), 0, 0, 0);
            viewHolder.editView.setVisibility(View.VISIBLE);
            viewHolder.chevronRight.setVisibility(View.INVISIBLE);
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //delete folder
                    List<Folder> folders = new ArrayList<Folder>();
                    if (viewHolder.edit.isChecked()) {
                        folders.add(folder);
                    }
                    mItemListener.onFolderDelete(folders);
                }
            });
            viewHolder.name.setClickable(true);
            viewHolder.name.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //rename folder
                    mItemListener.onFolderUpdate(folder);
                }
            });
        } else {
            view.setClickable(true);
            viewHolder.name.setClickable(false);
            viewHolder.editView.setVisibility(View.GONE);
            viewHolder.chevronRight.setVisibility(View.VISIBLE);
            viewHolder.name.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 16), 0, 0, 0);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //open folder
                    mItemListener.onFolderClick(folder);
                }
            });
        }
        return view;
    }

    private static class ViewHolder {
        View editView;
        CheckBox edit;
        TextView name;
        TextView num;
        ImageView chevronRight;
    }
}
