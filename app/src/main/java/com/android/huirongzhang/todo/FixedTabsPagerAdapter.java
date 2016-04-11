package com.android.huirongzhang.todo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.huirongzhang.todo.study.TaskContract;
import com.android.huirongzhang.todo.study.TaskPresenter;
import com.android.huirongzhang.todo.work.WorkFragment;
import com.android.huirongzhang.todo.study.TaskFragment;
import com.android.huirongzhang.todo.life.LifeFragment;
import com.android.huirongzhang.todo.me.MeFragment;

/**
 * Created by HuirongZhang on 16/4/9.
 */
public class FixedTabsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public FixedTabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TaskFragment();
                new TaskPresenter().setView((TaskContract.View) fragment);
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
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.study_page_title);
            case 1:
                return mContext.getString(R.string.work_page_title);
            case 2:
                return mContext.getString(R.string.life_page_title);
            case 3:
                return mContext.getString(R.string.me_page_title);
            default:
                return null;
        }
    }
}
