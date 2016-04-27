package com.android.huirongzhang.todo.data.folder;

import android.support.annotation.NonNull;

import com.android.huirongzhang.todo.data.local.folder.FolderLocalDataSource;

import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/12.
 */
public class FolderRepository implements FolderDataSource {

    private static FolderRepository INSTANCE = null;
    private final FolderDataSource mFolderLocalDataSource;

    // Prevent direct instantiation.
    private FolderRepository(@NonNull FolderDataSource folderLocalDataSource) {
        mFolderLocalDataSource = folderLocalDataSource;
    }

    public static FolderRepository getInstance(FolderDataSource folderLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new FolderRepository(folderLocalDataSource);
        }
        return INSTANCE;
    }


    @Override
    public void saveFolder(@NonNull Folder folder) {
        mFolderLocalDataSource.saveFolder(folder);
    }

    @Override
    public void getFolders(LoadFoldersCallback callback) {
        mFolderLocalDataSource.getFolders(callback);
    }

    @Override
    public void updateFolder(@NonNull int id, @NonNull String title) {
        mFolderLocalDataSource.updateFolder(id, title);
    }

    @Override
    public void deleteFolder(@NonNull List<Folder> folders) {
        mFolderLocalDataSource.deleteFolder(folders);
    }
}
