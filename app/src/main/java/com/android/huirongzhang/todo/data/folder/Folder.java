package com.android.huirongzhang.todo.data.folder;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by zhanghuirong on 2016/4/11.
 */
public final class Folder {
    private int id;
    private String title;

    public Folder(@Nullable String title) {
        this.title = title;
    }

    public Folder(int id, @Nullable String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
