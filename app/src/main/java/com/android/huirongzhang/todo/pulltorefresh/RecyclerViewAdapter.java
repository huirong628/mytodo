package com.android.huirongzhang.todo.pulltorefresh;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by zhanghuirong on 2016/4/15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;

    /*自定义adapter
     */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mViewAdapter;

    //头部
    private ArrayList<View> mHeaderViews = new ArrayList<>();

    //尾部
    private ArrayList<View> mFooterViews = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int headerViewsCountCount = getHeaderViewsCount();
        if (viewType < TYPE_HEADER_VIEW + headerViewsCountCount) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < Integer.MAX_VALUE / 2) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mViewAdapter.onCreateViewHolder(parent, viewType - Integer.MAX_VALUE / 2);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();
        if (position >= headerViewsCountCount && position < headerViewsCountCount + mViewAdapter.getItemCount()) {
            mViewAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mViewAdapter.getItemCount() + getHeaderViewsCount() + getFooterViewsCount();
    }

    public RecyclerViewAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        setAdapter(adapter);
    }

    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mViewAdapter = adapter;
        //为该adapter注册一个观察者，
        mViewAdapter.registerAdapterDataObserver(mDataObserver);

        //
        notifyItemRangeInserted(getHeaderViewsCount(), mViewAdapter.getItemCount());

    }

    public RecyclerView.Adapter getAdapter() {
        return mViewAdapter;
    }

    //[[头部view操作
    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        mHeaderViews.add(header);
        this.notifyDataSetChanged();
    }

    public void removeHeaderView(View header) {
        mHeaderViews.remove(header);
        this.notifyDataSetChanged();
    }

    public View getHeaderView() {
        return getHeaderViewsCount() > 0 ? mHeaderViews.get(0) : null;
    }

    public boolean isHeaderView(int position) {
        return getHeaderViewsCount() > 0 && position == 0;
    }
    //头部view操作]]

    //[[尾部view操作
    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }
        mFooterViews.add(footer);
        this.notifyDataSetChanged();
    }

    public void removeFooterView(View footer) {
        mFooterViews.remove(footer);
        this.notifyDataSetChanged();
    }

    public View getFooterView() {
        return getFooterViewsCount() > 0 ? mFooterViews.get(0) : null;
    }

    public boolean isFooterView(int position) {
        int lastPosition = getItemCount() - 1;
        return getFooterViewsCount() > 0 && position == lastPosition;

    }
    //尾部view操作]]

    //Observer class for watching changes to an {@link Adapter}.
    //为所有具体观察者定一个接口，在得到主题通知时，更新自己。
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        //mDataObserver作为观察者，当接收到数据发生变化的通知时，通知UI进行更新。
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
