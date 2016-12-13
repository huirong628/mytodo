// IRemoteService.aidl
package com.android.huirongzhang.todo.service;

import com.android.huirongzhang.todo.service.MyData;
// Declare any non-default types here with import statements

/**
*
* 定义远程通信的接口方法
*
*/

interface IRemoteService {
    int getPid();
    MyData getMyData();
}