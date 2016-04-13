package com.android.huirongzhang.todo.folder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.task.TaskActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class FolderFragment extends Fragment implements FolderContract.View {

    private FolderContract.Presenter mPresenter;

    private FolderAdapter mListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化adapter
        mListAdapter = new FolderAdapter(new ArrayList<Folder>(0), mItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.folder_fragment, container, false);

        ListView listView = (ListView) root.findViewById(R.id.folder_list);
        listView.setAdapter(mListAdapter);

        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.folder_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_folder_add) {
            showAddFolderDialog();
        }
        return true;
    }

    @Override
    public void setPresenter(FolderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showFolderList() {
        //reresh list
        mPresenter.loadFolders(false);
    }

    @Override
    public void showFolders(List<Folder> folders) {
        mListAdapter.replaceData(folders);
    }

    @Override
    public void showFolderDetailsUi(String folderId) {
        Intent intent = new Intent(getActivity(), TaskActivity.class);
        intent.putExtra(TaskActivity.EXTRA_FOLDER_ID, folderId);
        startActivity(intent);
    }

    private void showAddFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_folder, null);
        final EditText folderName = (EditText) view.findViewById(R.id.folder_name);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // save ...
                        mPresenter.addFolder(folderName.getText().toString());//参数:内容
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();// to remove the dialog from view,so not use the dialog.cancel();
                    }
                });
        builder.setCancelable(false);//Sets the dialog is not cancelable.
        builder.show();
    }

    /**
     * Listener for clicks on folders in the ListView.
     */
    FolderItemListener mItemListener = new FolderItemListener() {
        @Override
        public void onFolderClick(Folder clickedFolder) {
            mPresenter.openFolderDetails(clickedFolder);
        }
    };
}
