package com.android.huirongzhang.todo.data.task;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class Task {
    private int id;
    private String content;
    private int type;

    public Task() {

    }

    public Task(@Nullable String content, int type) {
        this.content = content;
        this.type = type;
    }

    public Task(int id, @Nullable String content, int type) {
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
