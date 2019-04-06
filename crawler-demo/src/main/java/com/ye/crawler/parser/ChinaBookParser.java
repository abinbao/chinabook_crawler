package com.ye.crawler.parser;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ye.crawler.fetcher.CommonSpider;
import com.ye.crawler.pipeline.ChinaBookPipeline;
import com.ye.job.SpiderRunJob;
import com.ye.service.ChinaBookSpiderService;
import com.ye.util.ApplicationContextUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 中国图书网; 获取各个分类的url，并且继续请求
 */
public class ChinaBookParser extends CommonSpider implements PageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ChinaBookParser.class);

    @Autowired
    ApplicationContextUtils applicationContextUtils;
    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0").setRetryTimes(3)
            .setSleepTime(1000);

    /**
     * 开始解析页面，从页面中获取 接下来请求的链接
     */
    @Override
    public void process(Page page) {
        LOG.info("中国图书网 get all catetories ...");
        /**
         * 这里使用了 xpath 来获取网页中你需要的链接
         */
        List<Selectable> dataList = page.getHtml().xpath("//ul[@class='category-list']/li").nodes();
        int category2Num = 0;
        ChinaBookPipeline chinaBookPipeline = (ChinaBookPipeline) applicationContextUtils.getBean("chinaBookPipeline");
        for (Selectable sel : dataList) {
            String catetory1 = sel.xpath("/li/div[@class='category-info']/h3/a/text()").get();
            LOG.info("一级分类: {}", catetory1);
            // 获取 分类链接集合
            List<Selectable> details = sel.xpath("/li/div[@class='category-info']/p/a").nodes();
            category2Num = category2Num + details.size();
            for (Selectable detail : details) {
                /**
                 * 判断是否终止爬虫
                 */
                if (ChinaBookSpiderService.isStop()) {
                    LOG.info("Spider Stop .");
                    break;
                }
                String category2 = detail.xpath("/a/text()").get();
                String category2Url = detail.xpath("/a/@href").get();
                LOG.info("二级分类：{}, 链接为：{}", category2, category2Url);
                // 继续请求链接，获取书籍的详细信息
                Spider spider = Spider.create(new ChinaBookDetailParser(catetory1, category2)).thread(5)
                        .addPipeline(chinaBookPipeline).addUrl(category2Url);
                ChinaBookSpiderService chinaBookSpiderService = (ChinaBookSpiderService) applicationContextUtils
                        .getBean("chinaBookSpiderService");
                chinaBookSpiderService.addSpider(spider);
                // 使用线程池提交任务
                threadpool.submit(new SpiderRunJob(spider));
            }
        }
        LOG.info("一级分类共有: {}, 二级分类共有: {}", dataList.size(), category2Num);
    }

    @Override
    public Site getSite() {
        return site;
    }

}
