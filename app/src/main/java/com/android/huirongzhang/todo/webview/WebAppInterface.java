package com.android.huirongzhang.todo.webview;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HuirongZhang on 9/25/16.
 */

public class WebAppInterface {
    private Context mContext;

    public WebAppInterface(Context context) {
        mContext = context;
    }

    /**
     * 在web page 上显示一个toast
     *
     * @param toast
     */
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

}
