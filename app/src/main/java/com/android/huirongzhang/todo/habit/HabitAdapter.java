package com.android.huirongzhang.todo.habit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/5/3.
 */
public class HabitAdapter extends BaseAdapter {
    private static String[] mData = new String[]{"按时做作业", "吃饭不挑食"};

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public Object getItem(int i) {
        return mData[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.habit_item, viewGroup, false);
            viewHolder.deleteView = (ImageView) view.findViewById(R.id.habit_delete);
            viewHolder.contentView = (TextView) view.findViewById(R.id.habit_content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        if (i == mData.length) {
//            viewHolder.deleteView.setVisibility(View.GONE);
//            viewHolder.contentView.setText("添加好习惯");
//        } else {
//            viewHolder.deleteView.setVisibility(View.VISIBLE);
//            viewHolder.contentView.setText(mData[i]);
//        }
        viewHolder.contentView.setText(mData[i]);
        return view;
    }

    private static class ViewHolder {
        ImageView deleteView;
        TextView contentView;
    }
}
