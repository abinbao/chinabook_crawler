package com.ye.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ye.constant.R;
import com.ye.constant.Status;
import com.ye.dao.SpiderInfoDao;
import com.ye.dao.SpiderMonitorDao;
import com.ye.job.CrawlerJob;
import com.ye.model.SpiderInfoModel;
import com.ye.model.SpiderMonitorModel;
import com.ye.service.CommonSpiderService;

/**
 * 爬虫的controller层，通过网络api对爬虫进行操作，并观察爬虫任务的状态
 */
@Controller
@RequestMapping("/spider")
public class SpiderController {

    private static final Logger LOG = LoggerFactory.getLogger(SpiderController.class);

    private static ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Autowired
    CommonSpiderService chinaBookSpiderService;
    @Resource
    SpiderInfoDao spiderInfoDao;
    @Resource
    SpiderMonitorDao spiderMonitorDao;
    @Resource
    CrawlerJob crawlerJob;

    /**
     * 运行爬虫
     */
    @RequestMapping("/run")
    @ResponseBody
    public R spiderRun(Model model, @RequestParam String spidername) {
        LOG.info("Start to run Spider: {}", spidername);
        getSpiderService(spidername).setStop(false);
        // 开始执行爬虫
        Callable<R> call = () -> getSpiderService(spidername).runSpider();
        executorService.submit(call);
        // 更新爬虫状态
        SpiderInfoModel spiderInfo = new SpiderInfoModel();
        spiderInfo.setSpiderName(spidername);
        spiderInfo.setSpiderStatus(Status.ING.name());
        spiderInfoDao.updateSpiderInfoBySpiderName(spiderInfo);
        SpiderInfoModel tmp = spiderInfoDao.query().get(0);
        // 开启爬虫监控
        crawlerJob.setStop(false);
        model.addAttribute("spiderInfo", tmp);
        return R.ok();
    }

    /**
     * 终止爬虫，这里终止爬虫时，并不是立即终止爬虫任务，而是慢慢爬虫任务终止。
     * 因为每一个链接请求都是一个爬虫任务，会放到任务队列中，放到任务队列中的爬虫不能立即终止
     */
    @RequestMapping("/close")
    @ResponseBody
    public R spiderClose(@RequestParam String spidername) {
        LOG.info("Start to stop Spider: {}", spidername);
        getSpiderService(spidername).setStop(true);
        // 更新爬虫状态
        SpiderInfoModel model = new SpiderInfoModel();
        model.setSpiderName(spidername);
        model.setSpiderStatus(Status.STOP.name());
        spiderInfoDao.updateSpiderInfoBySpiderName(model);
        // 关闭爬虫监控
        crawlerJob.setStop(true);
        return R.ok();
    }

    /**
     * 根据爬虫名称获取爬虫状
     * 
     * @param spidername
     * @return
     */
    @RequestMapping("/count")
    @ResponseBody
    public R spiderCount(@RequestParam String spidername) {
        return getSpiderService(spidername).countSpiderTotal();
    }

    /**
     * 查看爬虫的监控信息
     * 
     * @param spidername
     * @return
     */
    @RequestMapping("/monitor")
    @ResponseBody
    public R spiderMonitor(@RequestParam String spidername) {
        List<SpiderMonitorModel> result = spiderMonitorDao.query(spidername);
        for (SpiderMonitorModel model : result) {
            Instant instant = model.getInsertTime().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");
            String hour = dtf.format(localDateTime);
            model.setHour(hour);
        }
        return R.ok().put("result", result);
    }

    /**
     * 获取爬虫信息，并跳转到爬虫信息页面
     * 
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/spider_info")
    public String spiderInfo(HttpSession session, Model model) {
        if (session.getAttribute("username") == null)
            return "login";
        List<SpiderInfoModel> spiders = spiderInfoDao.query();
        model.addAttribute("spiderInfo", spiders.get(0));
        return "spiders/spider_info";
    }

    /**
     * 根据爬虫名称获取爬虫状
     * 
     * @param spidername
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public R query() {
        List<SpiderInfoModel> spiders = spiderInfoDao.query();
        return R.ok().put("result", spiders.get(0));
    }

    /**
     * 根据爬虫名称获取爬虫服务
     * 
     * @param spidername
     * @return
     */
    private CommonSpiderService getSpiderService(String spidername) {
        CommonSpiderService commonSpiderService = null;
        switch (spidername) {
            case "chinabook":
                commonSpiderService = chinaBookSpiderService;
                break;

            default:
                commonSpiderService = chinaBookSpiderService;
                break;
        }
        return commonSpiderService;
    }

}
