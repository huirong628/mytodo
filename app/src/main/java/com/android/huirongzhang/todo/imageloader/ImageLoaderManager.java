package com.android.huirongzhang.todo.imageloader;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Calendar;

/**
 * Created by zhanghuirong on 2016/5/16.
 */
public class ImageLoaderManager {

    private static ImageLoaderManager instance;
    private ImageLoaderUniversal mImageLoaderUniversal;

    private ImageLoaderManager() {
        mImageLoaderUniversal = new ImageLoaderUniversal();
    }

    /**
     * Returns singleton class instance
     */
    public static ImageLoaderManager getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderManager.class) {
                if (instance == null) {
                    instance = new ImageLoaderManager();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        mImageLoaderUniversal.init(null);//null临时使用
    }

    /**
     * @param url:网络图片资源地址
     */
    public Bitmap loadImage(String url) {
        Calendar now = Calendar.getInstance();
        return null;
    }
}
