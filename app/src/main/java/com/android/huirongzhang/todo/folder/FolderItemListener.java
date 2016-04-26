package com.android.huirongzhang.todo.folder;

import com.android.huirongzhang.todo.data.folder.Folder;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public interface FolderItemListener {
    void onFolderClick(Folder folder);

    void onFolderDelete(List<Folder> folders);

    void onFolderUpdate(Folder folder);
}
