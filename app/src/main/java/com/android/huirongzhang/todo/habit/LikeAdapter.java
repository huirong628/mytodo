package com.android.huirongzhang.todo.habit;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/5/3.
 */
public class LikeAdapter extends BaseAdapter {
    private String[] mDate = new String[3];
    private Context mContext;

    public LikeAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDate.length;
    }

    @Override
    public Object getItem(int i) {
        return mDate[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.like_item, viewGroup, false);
            viewHolder.dateView = (TextView) view.findViewById(R.id.like_date);
            viewHolder.memberView = (TextView) view.findViewById(R.id.like_member);
            viewHolder.reasonView = (TextView) view.findViewById(R.id.like_reason);
            viewHolder.lineView = view.findViewById(R.id.line);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.dateView.setText("2016/04/16");
        if (i == mDate.length - 1) {
            viewHolder.lineView.setVisibility(View.GONE);
        } else {
            viewHolder.lineView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private static class ViewHolder {
        TextView dateView;
        TextView memberView;
        TextView reasonView;
        View lineView;
    }
}
