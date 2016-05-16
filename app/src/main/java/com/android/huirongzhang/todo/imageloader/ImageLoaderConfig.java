package com.android.huirongzhang.todo.imageloader;

import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by zhanghuirong on 2016/5/16.
 */
public class ImageLoaderConfig {

    /**
     * Sets thread pool size for image display tasks.
     * Default is :3
     */
    public static final int THREAD_POOL_SIZE = 3;
    /**
     * Sets the priority for image loading threads.
     */
    public static final int THREAD_PRIORITY = Thread.NORM_PRIORITY - 2;
    /**
     * Sets type of queue processing for tasks for loading and displaying images.
     */
    public static final QueueProcessingType TASK_PROCESSING_ORDER = QueueProcessingType.LIFO;
    /**
     *
     */
    public static final long DISK_CACHE_SIZE = 100 * 1024 * 1024;
    ;
    /**
     *
     */
    public static final int MEMORY_CACHE_SIZE_PERCENTAGE = 15;

    /**
     * Maximum image width which will be used for memory saving during decoding
     * an image to {@link android.graphics.Bitmap Bitmap}. <b>Default value - device's screen width</b>
     */
    public static final int MEMORY_CACHE_MAX_WIDTH = 720;

    /**
     *
     */
    public static final int MEMORY_CACHE_MAX_HEIGHT = 1280;
}
