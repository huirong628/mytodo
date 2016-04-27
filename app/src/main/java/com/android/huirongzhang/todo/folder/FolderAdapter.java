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
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/12.
 */
public class FolderAdapter extends BaseAdapter {

    private List<Folder> mFolders;

    private FolderItemListener mItemListener;

    private boolean mEditMode = false;

    private static HashMap<Integer, Boolean> mIsSelected;//保存是否选中的状态

    //delete folder
    private List<Folder> mDeleteFolders = null;

    public FolderAdapter(ArrayList<Folder> folders) {
        setList(folders);
    }

    public void replaceData(List<Folder> folders) {
        setList(folders);
        notifyDataSetChanged();
    }

    public void showEditMode(boolean editMode) {
        mEditMode = editMode;
        //每次进入编辑状态都进行初始化
        mIsSelected = new HashMap<Integer, Boolean>();
        mDeleteFolders = new ArrayList<Folder>();
        for (int i = 0; i < mFolders.size(); i++) {
            mIsSelected.put(mFolders.get(i).getId(), false);
        }
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
            viewHolder.editCBView = (CheckBox) view.findViewById(R.id.folder_cb);
            viewHolder.nameView = (TextView) view.findViewById(R.id.folder_name);
            viewHolder.countView = (TextView) view.findViewById(R.id.task_count);
            viewHolder.chevronRightView = (ImageView) view.findViewById(R.id.chevron_right);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Folder folder = (Folder) getItem(i);

        viewHolder.nameView.setText(folder.getTitle());
        viewHolder.countView.setText(folder.getCount() + "");

        if (mEditMode) {
            //重新设置布局
            view.setClickable(false);
            viewHolder.nameView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 0), 0, 0, 0);
            viewHolder.editView.setVisibility(View.VISIBLE);
            viewHolder.chevronRightView.setVisibility(View.INVISIBLE);
            viewHolder.editCBView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsSelected.get(folder.getId())) {
                        mIsSelected.put(folder.getId(), false);
                        mDeleteFolders.remove(folder);
                    } else {
                        mIsSelected.put(folder.getId(), true);
                        mDeleteFolders.add(folder);
                    }

                    mItemListener.onFolderDelete(mDeleteFolders);
                }
            });
            viewHolder.editCBView.setChecked(mIsSelected.get(folder.getId()));
            viewHolder.nameView.setClickable(true);
            viewHolder.nameView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //rename folder
                    mItemListener.onFolderUpdate(folder);
                }
            });
        } else {
            view.setClickable(true);
            viewHolder.nameView.setClickable(false);
            viewHolder.editView.setVisibility(View.GONE);
            viewHolder.chevronRightView.setVisibility(View.VISIBLE);
            viewHolder.nameView.setPadding(ActivityUtils.dip2px(viewGroup.getContext(), 16), 0, 0, 0);

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
        CheckBox editCBView;
        TextView nameView;
        TextView countView;
        ImageView chevronRightView;
    }

    /**
     * 是否选中
     * 获取当前状态
     *
     */
}
