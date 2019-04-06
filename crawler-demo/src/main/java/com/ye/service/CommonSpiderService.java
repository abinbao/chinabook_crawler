package com.ye.service;

import com.ye.constant.R;

import us.codecraft.webmagic.Spider;

public interface CommonSpiderService {

    /**
     * 运行爬虫
     * 
     * @return
     */
    R runSpider();

    /**
     * 查看爬虫运行状态
     * 
     * @return
     */
    R countSpiderTotal();

    /**
     * 添加爬虫统计管理
     * 
     * @param spider
     */
    void addSpider(Spider spider);

    /**
     * 终止爬虫
     * 
     * @param stop
     */
    void setStop(boolean stop);

}
