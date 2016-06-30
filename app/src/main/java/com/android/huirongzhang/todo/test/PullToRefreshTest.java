package com.android.huirongzhang.todo.test;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.pulltorefresh.PullToRefreshBase;
import com.android.huirongzhang.todo.pulltorefresh.PullToRefreshListView;

/**
 * Created by zhanghuirong on 2016/6/28.
 */
public class PullToRefreshTest extends Activity {

    static final String[] FRUITS = new String[]{"Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple"};
    private PullToRefreshListView mPullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pull_to_refresh_test);
        mPullToRefresh = (PullToRefreshListView) findViewById(R.id.pull_to_refresh);
        //mPullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
        mPullToRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                new GetDataTask().execute();
            }
        });

        mPullToRefresh.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FRUITS));
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            mPullToRefresh.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
