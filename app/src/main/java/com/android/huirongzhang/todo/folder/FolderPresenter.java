package com.android.huirongzhang.todo.folder;

import android.widget.Toast;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class FolderPresenter implements FolderContract.Presenter {
    private FolderContract.View mView;

    public FolderPresenter(FolderContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void addFolder(String folderName) {

    }
}
