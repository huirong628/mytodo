package com.android.huirongzhang.todo.data.task;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by zhanghuirong on 2016/4/13.
 */
public class Task {
    private String id;
    private String content;
    private String type;

    public Task() {

    }

    public Task(@Nullable String content, String type) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.type = type;
    }

    public Task(String id, @Nullable String content, String type) {
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
