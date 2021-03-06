package com.android.huirongzhang.todo.folder;

import android.text.TextUtils;

import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.data.folder.FolderDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class FolderPresenter implements FolderContract.Presenter {
    private FolderContract.View mView;
    private final FolderDataSource mFolderRepository;
    private boolean mFirstLoad = true;

    public FolderPresenter(FolderContract.View view, FolderDataSource folderLocalDataSource) {
        mView = view;
        mFolderRepository = folderLocalDataSource;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadFolders(false);
    }

    @Override
    public void addFolder(String folderName) {
        Folder folder = new Folder(folderName);
        if (!TextUtils.isEmpty(folderName)) {
            mFolderRepository.saveFolder(folder);
        }
        //save to db
        //update UI
        mView.showFolderList();
    }

    @Override
    public void loadFolders(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadFolders(forceUpdate, true);
        //mFirstLoad = false;
    }

    @Override
    public void openFolderDetails(Folder clickedFolder) {
        mView.showFolderDetailsUi(clickedFolder);
    }

    @Override
    public void updateFolder(int id, String folderName) {
        if (!TextUtils.isEmpty(folderName)) {
            mFolderRepository.updateFolder(id, folderName);
        }
        //save to db
        //update UI
        mView.showFolderList();
    }

    @Override
    public void deleteFolder(List<Folder> folders) {
        if (folders != null && folders.size() > 0) {
            mFolderRepository.deleteFolder(folders);
        }
        //save to db
        //update UI
        mView.showFolderList();
    }

    private void loadFolders(boolean forceUpdate, final boolean showLoadingUI) {
//        if (showLoadingUI) {
//            mTasksView.setLoadingIndicator(true);
//        }
//        if (forceUpdate) {
//            mTasksRepository.refreshTasks();
//        }

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
//        EspressoIdlingResource.increment(); // App is busy until further notice

        mFolderRepository.getFolders(new FolderDataSource.LoadFoldersCallback() {
            @Override
            public void onFoldersLoaded(List<Folder> folders) {
                mView.showFolders(folders);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                mView.showNoFolders();
            }
        });
    }
}
