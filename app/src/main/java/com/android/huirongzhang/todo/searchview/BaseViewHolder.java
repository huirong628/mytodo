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

    /**
     * 抽象方法，绑定数据.
     * 让子类自行对数据和view进行绑定
     *
     * @param itemValue Item的数据
     * @param position  当前item的position
     */
    protected abstract void bindData(V itemValue, int position);

    /**
     * 用于传递数据和信息
     *
     * @param itemValue
     * @param position
     *
     */
    public void setData(V itemValue, int position) {
        Log.d(TAG, "setData()");
        bindData(itemValue, position);
    }
}