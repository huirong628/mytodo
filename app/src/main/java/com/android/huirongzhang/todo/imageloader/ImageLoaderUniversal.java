package com.android.huirongzhang.todo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhanghuirong on 2016/5/16.
 */
public class ImageLoaderUniversal {
    private FileNameGenerator mFileNameGenerator = new Md5FileNameGenerator();
    private ImageSize mMemoryCacheMaxImageSize = new ImageSize(ImageLoaderConfig.MEMORY_CACHE_MAX_WIDTH, ImageLoaderConfig.MEMORY_CACHE_MAX_HEIGHT);

    public void init(Context context) {
        if (ImageLoader.getInstance().isInited()) {//判断是否已经初始化
            return;
        }

        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
        //初始化
        builder.threadPoolSize(ImageLoaderConfig.THREAD_POOL_SIZE);
        builder.threadPriority(ImageLoaderConfig.THREAD_PRIORITY);
        builder.tasksProcessingOrder(ImageLoaderConfig.TASK_PROCESSING_ORDER);
        builder.denyCacheImageMultipleSizesInMemory();//

        //初始化文件缓存参数
        try {
            builder.diskCache(new LruDiskCache(getCacheDir(), mFileNameGenerator, ImageLoaderConfig.DISK_CACHE_SIZE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化内存缓存参数
        builder.memoryCache(new WeakMemoryCache());
        builder.memoryCacheSizePercentage(ImageLoaderConfig.MEMORY_CACHE_SIZE_PERCENTAGE); //缓存的文件空间（内存百分百）
        builder.memoryCacheExtraOptions(ImageLoaderConfig.MEMORY_CACHE_MAX_WIDTH, ImageLoaderConfig.MEMORY_CACHE_MAX_HEIGHT); //即保存的每个缓存文件的最大长宽

        ImageLoader.getInstance().init(builder.build());
    }

    private File getCacheDir() {
        return null;
    }


    /**
     * @param url
     * @param bitmap
     */
    public void putMemoryCacheImage(String url, Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        MemoryCache memoryCache = ImageLoader.getInstance().getMemoryCache();
        String memoryCacheKey = MemoryCacheUtils.generateKey(url, mMemoryCacheMaxImageSize);
        memoryCache.put(memoryCacheKey, bitmap);
    }

    /**
     * @param url
     * @return bitmap
     */
    private Bitmap getMemoryCacheImage(String url) {
        MemoryCache memoryCache = ImageLoader.getInstance().getMemoryCache();
        String memoryCacheKey = MemoryCacheUtils.generateKey(url, mMemoryCacheMaxImageSize);
        Bitmap bitmap = memoryCache.get(memoryCacheKey);
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        return bitmap;
    }

    /**
     * 清理内存缓存
     */
    public void clearMemoryCache() {
        ImageLoader.getInstance().clearMemoryCache();
    }

    /**
     * 图片进行文件缓存
     *
     * @param url
     * @param bitmap
     */
    public void putDiskCacheImage(String url, Bitmap bitmap) {

    }

    /**
     * 从文件缓存中获取图片
     *
     * @param url
     */
    public void getDiskCacheImage(String url) {

    }
}
