package com.android.huirongzhang.todo.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * Created by HuirongZhang
 * on 2016/12/13.
 */

public class ClientActivity extends Activity {

    private static final String TAG = "ClientActivity";
    private IRemoteService mRemoteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 远程服务连接状态
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
        }
    };

    /**
     * 绑定远程服务
     */
    private void bindRemoteService() {
        Intent intent = new Intent(ClientActivity.this, RemoteService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 接触绑定远程服务
     */
    private void unBindRemoteService() {
        unbindService(mConnection);
    }

    /**
     * 杀死远程服务
     */
    private void killRemoteService() {
        try {
            android.os.Process.killProcess(mRemoteService.getPid());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
