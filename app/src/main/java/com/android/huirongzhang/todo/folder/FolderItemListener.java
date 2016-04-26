package com.android.huirongzhang.todo.folder;

import com.android.huirongzhang.todo.data.folder.Folder;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public interface FolderItemListener {
    void onFolderClick(Folder folder);

    void onFolderDelete(Folder folder);

    void onFolderUpdate(Folder folder);
}
