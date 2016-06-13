package com.android.huirongzhang.todo.searchview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/5/24.
 */
public class SearchAdapter extends BaseAdapter<SearchItem> {

    private static final String TAG = "SearchAdapter";

    @Override
    protected BaseViewHolder createViewHolder(Context context, ViewGroup parent, int viewType) {
        Log.d(TAG, "createViewHolder()");
        return new ResultViewHolder(context, parent, viewType);
    }

    public class ResultViewHolder extends BaseViewHolder<SearchItem> {

        private final ImageView icon_left;
        private final ImageView icon_right;
        private final TextView text;

        public ResultViewHolder(Context context, ViewGroup parent, int viewType) {
            super(context, parent, R.layout.search_item);
            icon_left = (ImageView) itemView.findViewById(R.id.imageView_item_icon_left);
            icon_right = (ImageView) itemView.findViewById(R.id.imageView_item_icon_right);
            text = (TextView) itemView.findViewById(R.id.textView_item_text);
        }

        @Override
        protected void bindData(SearchItem itemValue, int position) {
            Log.d(TAG, "bindData()");
            text.setText(itemValue.getText());
            icon_left.setImageResource(itemValue.getIcon());
        }
    }
}