package com.ye.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ye.constant.R;
import com.ye.dao.SpiderInfoDao;
import com.ye.dao.SpiderMonitorDao;
import com.ye.model.SpiderInfoModel;
import com.ye.model.SpiderMonitorModel;
import com.ye.service.ChinaBookSpiderService;

/**
 * 爬虫任务运行 定时查询爬虫的任务状态
 */
@Component
public class CrawlerJob {

    private static Logger LOG = LoggerFactory.getLogger(CrawlerJob.class);
    @Autowired
    ChinaBookSpiderService chinaBookSpiderService;
    @Autowired
    SpiderMonitorDao spiderMonitorDao;
    @Autowired
    SpiderInfoDao spiderInfoDao;

    /**
     * 是否开启监控，默认不开启
     */
    private boolean stop = true;

    /**
     * 每分钟查询一次
     */
    @Scheduled(cron = "0 */2 * * * ?")
    public void calculateChinaBookJob() {
        if (!stop) {
            LOG.info("query spider paramters...");
            R r = chinaBookSpiderService.countSpiderTotal();
            JSONObject obj = (JSONObject) JSONObject.toJSON(r.get("result"));
            int initNum = obj.getIntValue("init");
            int ingNum = obj.getIntValue("ing");
            int stopNum = obj.getIntValue("stop");
            SpiderMonitorModel model = new SpiderMonitorModel();
            model.setSpiderName("chinabook");
            model.setSpiderInitCount(initNum);
            model.setSpiderIngCount(ingNum);
            model.setSpiderStopCount(stopNum);
            SpiderInfoModel spider = spiderInfoDao.query().get(0);
            model.setSpiderStatus(spider.getSpiderStatus());
            spiderMonitorDao.insert(model);
            if (ingNum == 0) {
                // 更新爬虫状态
                spiderInfoDao.updateSpiderStatusByName("chinabook");
                // 重置爬虫请求个数集合
                chinaBookSpiderService.reset();
                // 终止监控
                this.setStop(true);
            }
        }
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
