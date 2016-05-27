package com.android.huirongzhang.todo.searchview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.huirongzhang.todo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/5/24.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ResultViewHolder> {

    private static final String TAG = "SearchAdapter";

    private List<SearchItem> mSuggestionsList = new ArrayList<>();
    ;
    private final SearchHistoryTable mHistoryDatabase;
    private List<SearchItem> mResultList = new ArrayList<>();
    ;

    public SearchAdapter(Context context, List<SearchItem> suggestionsList) {
        mSuggestionsList = suggestionsList;
        mHistoryDatabase = new SearchHistoryTable(context);
        mResultList = mSuggestionsList;
    }

    @Override
    public SearchAdapter.ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.search_item, parent, false);
        Log.d(TAG, "onCreateViewHolder,view = " + view);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ResultViewHolder holder, int position) {
        SearchItem item = mResultList.get(position);

        holder.text.setText(item.getText());
        holder.icon_left.setImageResource(item.getIcon());
        // holder.icon_right.setImageResource(item.getIcon());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView icon_left;
        public final ImageView icon_right;
        public final TextView text;

        public ResultViewHolder(View itemView) {
            super(itemView);
            icon_left = (ImageView) itemView.findViewById(R.id.imageView_item_icon_left);
            icon_right = (ImageView) itemView.findViewById(R.id.imageView_item_icon_right);
            text = (TextView) itemView.findViewById(R.id.textView_item_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //预留接口
        }
    }
}
