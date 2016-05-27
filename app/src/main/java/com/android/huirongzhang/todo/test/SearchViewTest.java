package com.android.huirongzhang.todo.test;

import android.app.Activity;
import android.os.Bundle;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.searchview.SearchAdapter;
import com.android.huirongzhang.todo.searchview.SearchItem;
import com.android.huirongzhang.todo.searchview.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/5/24.
 */
public class SearchViewTest extends Activity {

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_search_view);

        mSearchView = (SearchView) findViewById(R.id.search_view);

        mSearchView.setVersion(SearchView.VERSION_TOOLBAR);

        mSearchView.setVersionMargins(SearchView.VERSION_MARGINS_TOOLBAR_BIG);

        mSearchView.setOnQueryChangeListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        List<SearchItem> suggestionList = new ArrayList<>();
        suggestionList.add(new SearchItem("search1"));
        suggestionList.add(new SearchItem("search2"));
        suggestionList.add(new SearchItem("search3"));

        SearchAdapter adapter = new SearchAdapter(this, suggestionList);

        mSearchView.setSearchAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView != null && mSearchView.isSearchOpen()) {
            mSearchView.close(true);
        } else {
            super.onBackPressed();
        }
    }
}
