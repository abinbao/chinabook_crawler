package com.ye.crawler.fetcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 抓取模块
 */
public abstract class CommonSpider {
    /**
     * 继承此类，统一使用该线程池，最高 20 个线程
     */
    public static final ExecutorService threadpool = Executors.newFixedThreadPool(20);
}
