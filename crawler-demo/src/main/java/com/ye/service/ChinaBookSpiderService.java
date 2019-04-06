package com.ye.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ye.constant.R;
import com.ye.crawler.fetcher.ChinaBookSpider;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Spider.Status;

/**
 * 爬虫的服务层，对爬虫任务的一些操作
 */
@Service
public class ChinaBookSpiderService implements CommonSpiderService {

    private static final Logger LOG = LoggerFactory.getLogger(ChinaBookSpiderService.class);

    /**
     * 判断是否终止抓取任务
     */
    private static boolean stop = false;

    /**
     * 存储生成的爬虫集合
     */
    private Map<String, Spider> map = new ConcurrentHashMap<>();

    /**
     * 运行抓取任务
     * 
     * @return
     */
    @Override
    public R runSpider() {
        ChinaBookSpider chinaBookSpider = new ChinaBookSpider();
        chinaBookSpider.run();
        return R.ok();
    }

    /**
     * 查看 spider 各种状态的数量
     * 
     * @return
     */
    @Override
    public R countSpiderTotal() {
        JSONObject data = new JSONObject();
        int initNum = 0;
        int ingNum = 0;
        int stopNum = 0;
        for (Map.Entry<String, Spider> entry : map.entrySet()) {
            String key = entry.getKey();
            LOG.info("Spider id: {}", key);
            Spider spider = map.get(key);
            LOG.info("Spider Status: {}", spider.getStatus());
            if (spider.getStatus().equals(Status.Init)) {
                initNum++;
            }
            if (spider.getStatus().equals(Status.Running)) {
                ingNum++;
            }
            if (spider.getStatus().equals(Status.Stopped)) {
                stopNum++;
            }
            data.put(com.ye.constant.Status.INIT.getName(), initNum);
            data.put(com.ye.constant.Status.ING.getName(), ingNum);
            data.put(com.ye.constant.Status.STOP.getName(), stopNum);
        }
        return R.ok().put("result", data);
    }

    /**
     * 重置 map
     */
    public void reset() {
        map.clear();
    }

    /**
     * 将新生成的 spider 添加到 hashmap 中，进行统一管理
     * 
     * @param spider
     */
    @Override
    public void addSpider(Spider spider) {
        String uuid = UUID.randomUUID().toString();
        map.put(uuid, spider);
    }

    /**
     * 返回是否需要终止爬去任务
     * 
     * @return
     */
    public static boolean isStop() {
        return stop;
    }

    /**
     * 终止爬去任务专用，当设置 stop 为 true 时，爬去任务终止
     * 
     * @param stop
     */
    public void setStop(boolean stop) {
        ChinaBookSpiderService.stop = stop;
    }

    public Map<String, Spider> getMap() {
        return map;
    }

    public void setMap(Map<String, Spider> map) {
        this.map = map;
    }

}
