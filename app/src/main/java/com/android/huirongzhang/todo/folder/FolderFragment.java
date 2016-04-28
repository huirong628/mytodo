package com.android.huirongzhang.todo.folder;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import com.android.huirongzhang.todo.R;
import com.android.huirongzhang.todo.data.folder.Folder;
import com.android.huirongzhang.todo.task.TaskActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public class FolderFragment extends Fragment implements FolderContract.View, View.OnClickListener {

    private FolderContract.Presenter mPresenter;

    private ListView mListView;

    private FolderAdapter mListAdapter;

    private TextView mCreateView;

    boolean mEditMode = false;

    private TextView mDeleteView;

    private List<Folder> mDeleteFolders;//要删除的folder

    private MenuItem mMenuItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化adapter
        mListAdapter = new FolderAdapter(new ArrayList<Folder>(0));
        mListAdapter.setItemListener(mItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.folder_fragment, container, false);

        mListView = (ListView) root.findViewById(R.id.folder_list);
        mListView.setAdapter(mListAdapter);

        mCreateView = (TextView) root.findViewById(R.id.folder_create);
        mCreateView.setOnClickListener(this);

        mDeleteView = (TextView) root.findViewById(R.id.folder_delete);
        mDeleteView.setClickable(false);
        mDeleteView.setOnClickListener(this);

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.folder_create:
                showAddFolderDialog();
                break;
            case R.id.folder_delete:
                //执行删除操作
                mPresenter.deleteFolder(mDeleteFolders);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.folder_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mMenuItem = item;
        if (item.getItemId() == R.id.menu_folder_edit) {
            //showAddFolderDialog();
            //showPopUpMenu();
            String topTitle = item.getTitle().toString();
//            if (topTitle.equalsIgnoreCase("Edit")) {
//                topTitle = "Done";
//                mEditMode = true;
//                showDeleteView();
//            } else {
//                topTitle = "Edit";
//                mEditMode = false;
//                showNewFolderView();
//            }

            if (mEditMode) {
                topTitle = getString(R.string.title_edit);
                showNewFolderView();
            } else {
                topTitle = getString(R.string.title_done);
                showDeleteView();
            }
            mEditMode = !mEditMode;
            item.setTitle(topTitle);
            mListAdapter.showEditMode(mEditMode);
        }
        return true;
    }

    @Override
    public void showPopUpMenu() {
//		final PopupMenu popup = new PopupMenu(getActivity(), getActivity().findViewById(R.id.menu_folder_edit));
//		popup.getMenuInflater().inflate(R.menu.menu_folder, popup.getMenu());
//		//setIconEnable(popup);
//		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//			public boolean onMenuItemClick(MenuItem item) {
//				switch (item.getItemId()) {
//					case R.id.menu_folder_add:
//						showAddFolderDialog();
//						break;
//					case R.id.menu_folder_edit:
//						//动态修改menu item title
//						if (item.getTitle() == "Edit") {
//							item.setTitle("Done");
//						} else {
//							item.setTitle("Edit");
//						}
//
//						break;
//					case R.id.menu_folder_delete:
//						break;
//					default:
//						break;
//				}
//				//mPresenter.loadTasks(false);
//				return true;
//			}
//		});
//
//		popup.show();
    }

    @Override
    public void setPresenter(FolderContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showFolderList() {
        //refresh list
        if (mEditMode) {
            mListAdapter.showEditMode(false);
            mEditMode = false;
            mMenuItem.setTitle(R.string.title_edit);
            showNewFolderView();
        }
        mPresenter.loadFolders(false);
    }

    @Override
    public void showFolders(List<Folder> folders) {
        mListAdapter.replaceData(folders);
    }

    @Override
    public void showNoFolders() {
        //show no folders
    }

    @Override
    public void showFolderDetailsUi(int folderId) {
        Intent intent = new Intent(getActivity(), TaskActivity.class);
        intent.putExtra(TaskActivity.EXTRA_FOLDER_ID, folderId);
        startActivity(intent);
    }

    private void showNewFolderView() {
        mCreateView.setVisibility(View.VISIBLE);
        mDeleteView.setVisibility(View.GONE);
    }

    private void showDeleteView() {
        mCreateView.setVisibility(View.GONE);
        mDeleteView.setTextColor(Color.GRAY);
        mDeleteView.setVisibility(View.VISIBLE);
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

    private void showUpdateFolderDialog(final Folder folder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_folder, null);
        ((TextView) view.findViewById(R.id.folder_content)).setText(R.string.dialog_content);
        final EditText folderName = (EditText) view.findViewById(R.id.folder_name);
        String folderTitle = folder.getTitle();
        folderName.setText(folderTitle);
        folderName.setSelection(folderTitle.length());//光标位置
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // save ...
                        mPresenter.updateFolder(folder.getId(), folderName.getText().toString());//参数:内容
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

        @Override
        public void onFolderDelete(List<Folder> folders) {
            mDeleteFolders = folders;
            if (mDeleteFolders.size() == 0) {
                mDeleteView.setTextColor(Color.GRAY);
                mDeleteView.setClickable(false);
            } else {
                mDeleteView.setTextColor(Color.WHITE);
                mDeleteView.setClickable(true);
            }
        }

        @Override
        public void onFolderUpdate(Folder folder) {
            showUpdateFolderDialog(folder);
        }
    };

    public static FolderFragment newInstance() {
        return new FolderFragment();
    }
}
