package com.android.huirongzhang.todo.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.habit.HabitFragment;
import com.android.huirongzhang.todo.life.LifeFragment;
import com.android.huirongzhang.todo.me.MeFragment;
import com.android.huirongzhang.todo.widget.viewpagerindicator.TabPageIndicator;
import com.android.huirongzhang.todo.work.WorkFragment;

/**
 * Created by HuirongZhang on 2016/9/29.
 */

public class SampleTab extends FragmentActivity {
    private static final String[] CONTENT = new String[]{"Tab1", "Tab2", "Tab3", "Tab4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_tab);

        FragmentPagerAdapter adapter = new MyAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager) findViewById(R.id.tab_pager);
        pager.setAdapter(adapter);

        /**
         * TabPageIndicator和ViewPager关联起来
         */
        TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.tab_indicator);
        indicator.setViewPager(pager);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new HabitFragment();
                    break;
                case 1:
                    fragment = new WorkFragment();
                    break;
                case 2:
                    fragment = new LifeFragment();
                    break;
                case 3:
                    fragment = new MeFragment();
                    break;
                default:
                    return null;
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
