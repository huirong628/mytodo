package com.android.huirongzhang.todo.data.folder;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public interface FolderDataSource {

    interface LoadFoldersCallback {

        void onFoldersLoaded(List<Folder> folders);

        void onDataNotAvailable();
    }

    void saveFolder(@NonNull Folder folder);

    void getFolders(LoadFoldersCallback callback);

    void updateFolder(@NonNull int id, @NonNull String title);

    void updateFolder(@NonNull int id, int type, int taskCount);

    void deleteFolder(@NonNull List<Folder> folders);

    String getFolderTitle(int folderId);
}
