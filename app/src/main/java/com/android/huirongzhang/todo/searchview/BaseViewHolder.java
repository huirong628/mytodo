package com.android.huirongzhang.todo.searchview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by zhanghuirong on 2016/6/13.
 */
public abstract class BaseViewHolder<V> extends RecyclerView.ViewHolder {

    private static final String TAG = "BaseViewHolder";

    public BaseViewHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
    }

    protected abstract void bindData(V itemValue, int position);

    public void setData(V itemValue, int position) {
        Log.d(TAG, "setData()");
        bindData(itemValue, position);
    }
}