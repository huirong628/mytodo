package com.android.huirongzhang.todo.searchview;

import com.android.huirongzhang.todo.R;

/**
 * Created by zhanghuirong on 2016/5/24.
 */
public class SearchItem {

    private int icon;
    private CharSequence text;

    public SearchItem(CharSequence text) {
        this(text, R.drawable.search_ic_search_black_24dp);
    }

    public SearchItem(CharSequence text, int icon) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public CharSequence getText() {
        return text;
    }

    public void setText(CharSequence text) {
        this.text = text;
    }
}
