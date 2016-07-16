package com.android.huirongzhang.todo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by HuirongZhang on 16/7/10.
 */
public class ImageLoader {

    private static final int MAX_CAPACITY = 20;//设置链表长度

    private static Context mContext;

    private static LinkedHashMap<String, SoftReference<Bitmap>> firstCacheMap = new LinkedHashMap<String, SoftReference<Bitmap>>(MAX_CAPACITY) {
        @Override
        protected boolean removeEldestEntry(Entry<String, SoftReference<Bitmap>> eldest) {
            //return true 表示移除最老的实体
            if (this.size() > MAX_CAPACITY) {
                return true;
            }
            // 往磁盘添加
            diskCache(eldest.getKey(), eldest.getValue());
            return false;
        }


    };

    /**
     * 加载图片到对应控件
     *
     * @param key
     * @param view
     */
    public void loadImage(String key, ImageView view, Drawable drawable) {
        synchronized (view) {// 加锁
            if (firstCacheMap.get(key) != null) {
                //检查缓存中是否有
                Bitmap bitmap = getFromCache(key);
                if (bitmap != null) {
                    //
                    view.setImageBitmap(bitmap);
                } else {
                    //下载
                    view.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                    //异步任务下载
                }
            }
        }
    }

    /**
     * @param key
     * @return
     */
    private Bitmap getFromCache(String key) {
        //检查内存软引用是否存在
        synchronized (firstCacheMap) {
            if (firstCacheMap.get(key) != null) {
                Bitmap bitmap = firstCacheMap.get(key).get();
                if (bitmap != null) {
                    //打个招呼，露个面
                    firstCacheMap.put(key, new SoftReference<Bitmap>(bitmap));
                    return bitmap;
                }
            }
        }
        //检查磁盘
        Bitmap bitmap = getFromLocalKey(key);
        if (bitmap != null) {
            firstCacheMap.put(key, new SoftReference<Bitmap>(bitmap));
            return bitmap;
        }
        return null;
    }

    /**
     * 检查SD卡里是否有图片
     *
     * @param key
     * @return
     */
    private Bitmap getFromLocalKey(String key) {
        String fileName = null;//sMD5Utils.decode(key);
        if (fileName == null) {
            return null;
        } else {
            String path = mContext.getCacheDir().getAbsolutePath() + "/" + fileName;
            InputStream is = null;
            try {
                is = new FileInputStream(new File(path));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 把图片缓存到本地磁盘
     *
     * @param key
     * @param value
     */
    private static void diskCache(String key, SoftReference<Bitmap> value) {
        //消息摘要算法
        String fileName = null;//sMD5Utils.decode(key);
        String path = mContext.getCacheDir().getAbsolutePath() + "/" + fileName;
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(new File(path));
            if (value.get() != null) {
                value.get().compress(Bitmap.CompressFormat.JPEG, 100, os);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Bitmap bitmap;
        //bitmap.compress();
    }

    class AsyncImageLoaderTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        private String key;

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            this.key = params[0];
            return null;
        }
    }
}
