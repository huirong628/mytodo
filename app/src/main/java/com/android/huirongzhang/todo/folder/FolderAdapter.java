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

    public FolderAdapter(ArrayList<Folder> folders) {
        setList(folders);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            rowView = inflater.inflate(R.layout.folder_item, viewGroup, false);
        }

        final Folder folder = (Folder) getItem(i);

        TextView titleTV = (TextView) rowView.findViewById(R.id.folder_name);
        titleTV.setText(folder.getTitle());
        return rowView;
    }
}
