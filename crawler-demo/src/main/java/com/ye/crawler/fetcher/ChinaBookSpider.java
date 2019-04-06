package com.ye.crawler.fetcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ye.crawler.parser.ChinaBookParser;
import com.ye.service.ChinaBookSpiderService;
import com.ye.util.ApplicationContextUtils;

import us.codecraft.webmagic.Spider;

/**
 * 中国图书网爬虫入口类； 以 http://www.bookschina.com 为入口，获取所有的图书信息。
 */
public class ChinaBookSpider extends CommonSpider {

    private static final Logger LOG = LoggerFactory.getLogger(ChinaBookSpider.class);
    /**
     * 初始入口
     */
    private String url = "http://www.bookschina.com";
    private Spider spider = null;

    @Autowired
    ApplicationContextUtils applicationContextUtils;

    public ChinaBookSpider() {
        spider = Spider.create(new ChinaBookParser()).thread(5).addUrl(url);
    }

    /**
     * 运行抓取任务
     */
    public void run() {
        ChinaBookSpiderService chinaBookSpiderService = (ChinaBookSpiderService) applicationContextUtils
                .getBean("chinaBookSpiderService");
        chinaBookSpiderService.addSpider(spider);
        spider.run();
    }

    public Spider getSpider() {
        return spider;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }
}
