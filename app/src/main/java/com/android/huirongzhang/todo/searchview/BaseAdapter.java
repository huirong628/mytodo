package com.android.huirongzhang.todo.searchview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/6/13.
 */
public abstract class BaseAdapter<V> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "BaseAdapter";
    private List<V> mValueList;

    /**
     * 绑定Layout，新建一个我们自己写的RecyclerView.ViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        return createViewHolder(parent.getContext(), parent, viewType);
    }

    /**
     * 进行数据和视图绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        ((BaseViewHolder) holder).setData(mValueList.get(position), position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount()");
        return mValueList == null ? 0 : mValueList.size();
    }

    public void refreshData(List<V> valueList) {
        Log.d(TAG, "refreshData()");
        this.mValueList = valueList;
        notifyDataSetChanged();
    }

    protected abstract BaseViewHolder createViewHolder(Context context, ViewGroup parent, int viewType);
}
