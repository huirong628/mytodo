package com.android.huirongzhang.todo.folder;

import com.android.huirongzhang.todo.BasePresenter;
import com.android.huirongzhang.todo.BaseView;
import com.android.huirongzhang.todo.data.folder.Folder;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public interface FolderContract {
    interface View extends BaseView<Presenter> {

        void showFolderList();

        void showFolders(List<Folder> folders);

        void showFolderDetailsUi(String id);
    }

    interface Presenter extends BasePresenter {
        void addFolder(String folderName);

        void loadFolders(boolean forceUpdate);

        void openFolderDetails(Folder clickedFolder);
    }
}
