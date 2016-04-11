package com.android.huirongzhang.todo.folder;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public interface FolderContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void addFolder(String folderName);
    }
}
