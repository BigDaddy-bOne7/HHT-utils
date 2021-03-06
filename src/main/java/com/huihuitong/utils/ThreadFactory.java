package com.huihuitong.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangz on 2017/7/20 9:46.
 */
public class ThreadFactory {
    public static final int POOLSIZE = 5;
    public static final int MAXPOOLSIZE = 15;
    public static final int ALIVETIME = 200;
    public static final int QUEUESIZE = 1000;
    private static ThreadPoolExecutor threadPoolExecutor;

    private ThreadFactory() {

    }

    public static synchronized ThreadPoolExecutor init() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(POOLSIZE, MAXPOOLSIZE, ALIVETIME, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(QUEUESIZE));
        }
        return threadPoolExecutor;

    }
}
