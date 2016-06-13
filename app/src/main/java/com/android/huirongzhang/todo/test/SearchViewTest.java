package com.android.huirongzhang.todo.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.searchview.BaseAdapter;
import com.android.huirongzhang.todo.searchview.SearchAdapter;
import com.android.huirongzhang.todo.searchview.SearchItem;
import com.android.huirongzhang.todo.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/5/24.
 */
public class SearchViewTest extends Activity {

    private ImageView mOpenSearch;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_search_view);

        mOpenSearch = (ImageView) findViewById(R.id.search_open);

        mSearchView = (SearchView) findViewById(R.id.search_view);

        mSearchView.setVersion(SearchView.VERSION_TOOLBAR);

        mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);

        mSearchView.setOnQueryChangeListener(new SearchView.OnQueryTextListener() {
            @Override
            public void onQueryTextChange(String newText) {

            }

            @Override
            public void onQueryTextSubmit(String query) {

            }
        });

        List<SearchItem> suggestionList = new ArrayList<>();
        suggestionList.add(new SearchItem("search1"));
        suggestionList.add(new SearchItem("search2"));
        suggestionList.add(new SearchItem("search3"));

        BaseAdapter<SearchItem> adapter = new SearchAdapter();
        adapter.refreshData(suggestionList);

        mSearchView.setSearchAdapter(adapter);

        mOpenSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchView.setVisibility(View.VISIBLE);
                mOpenSearch.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.getVisibility() == View.VISIBLE) {
            mSearchView.setVisibility(View.GONE);
            mOpenSearch.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
