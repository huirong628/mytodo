package com.android.huirongzhang.todo.folder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.huirongzhang.todo.BasePresenter;
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

    public FolderAdapter(ArrayList<Folder> folders, FolderItemListener itemListener) {
        setList(folders);
        mItemListener = itemListener;
    }

    public void replaceData(List<Folder> folders) {
        setList(folders);
        notifyDataSetChanged();
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
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.folder_item, viewGroup, false);
            viewHolder.name = (TextView) view.findViewById(R.id.folder_name);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        final Folder folder = (Folder) getItem(i);

        viewHolder.name.setText(folder.getTitle());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemListener.onFolderClick(folder);
            }
        });
        return view;
    }

    private static class ViewHolder {
        TextView name;
    }
}
