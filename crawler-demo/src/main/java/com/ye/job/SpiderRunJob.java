package com.ye.job;

import java.util.concurrent.Callable;

import us.codecraft.webmagic.Spider;

/**
 * 爬虫异步运行类，应为 webmagic 框架异步请求是都是重新生成一个 Thread，开销太大，使用线程池，降低系统开销
 */
public class SpiderRunJob implements Callable<Boolean> {

    private Spider spider;

    public SpiderRunJob(Spider spider) {
        this.spider = spider;
    }

    @Override
    public Boolean call() throws Exception {
        spider.run();
        return true;
    }

}
