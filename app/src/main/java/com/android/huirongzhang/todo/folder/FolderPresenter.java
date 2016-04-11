package com.android.huirongzhang.todo.folder;

import android.text.TextUtils;

import com.android.huirongzhang.todo.data.folder.Folder;

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
        Folder folder = new Folder();
        if (!TextUtils.isEmpty(folderName)) {
            folder.setTitle(folderName);
        }
        //save to db
        //update UI
    }
}
