package com.ye.crawler.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ye.crawler.fetcher.CommonSpider;
import com.ye.crawler.pipeline.ChinaBookPipeline;
import com.ye.job.SpiderRunJob;
import com.ye.model.ChinaBookModel;
import com.ye.service.ChinaBookSpiderService;
import com.ye.util.ApplicationContextUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 中国图书网图书详细信息
 */
public class ChinaBookDetailParser extends CommonSpider implements PageProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(ChinaBookParser.class);

    @Autowired
    ApplicationContextUtils applicationContextUtils;

    private Site site = Site.me()
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0").setRetryTimes(3)
            .setSleepTime(1000);

    public ChinaBookDetailParser() {
    }

    public ChinaBookDetailParser(String category1, String category2) {
        this.category1 = category1;
        this.category2 = category2;
    }

    /**
     * 一级分类
     */
    private String category1;
    /**
     * 二级分类
     */
    private String category2;

    @Override
    public void process(Page page) {
        LOG.info("GET {}", page.getRequest().getUrl());
        // 获取各个图书的信息集合
        List<Selectable> bookList = page.getHtml().xpath("//div[@class='bookList']/ul/li").nodes();
        /**
         * 开始解析，获取页面中你需要的数据
         */
        String originUrl = page.getRequest().getUrl();
        List<ChinaBookModel> list = new ArrayList<>();
        for (Selectable book : bookList) {
            String imageUrl = book.xpath("/li/div[@class='cover']/a/img/@src").get(); // 书的图片地址
            String bookName = book.xpath("/li/div[@class='infor']/h2/a/text()").get(); // 书名
            String detailUrl = book.xpath("/li/div[@class='infor']/h2/a/@href").get(); // 书的详细链接
            String authorInfo = book.xpath("/li/div[@class='infor']/div[@class='otherInfor']/a[@class='author']/text()")
                    .get(); // 作者信息
            String publishTime = book
                    .xpath("/li/div[@class='infor']/div[@class='otherInfor']/span[@class='pulishTiem']/text()").get()
                    .trim().replace("/", ""); // 出版时间
            String publisher = book
                    .xpath("/li/div[@class='infor']/div[@class='otherInfor']/a[@class='publisher']/text()").get(); // 出版社

            String sellPrice = book
                    .xpath("/li/div[@class='infor']/div[@class='priceWrap']/span[@class='sellPrice']/text()").get()
                    .replaceAll("¥", ""); // 出售价格

            String pattern = "\\((.*?)折\\)";
            Pattern r = Pattern.compile(pattern);
            String discount = book
                    .xpath("/li/div[@class='infor']/div[@class='priceWrap']/span[@class='discount']/text()").get(); // 折扣
            Matcher mathcer = r.matcher(discount);
            if (mathcer.find()) {
                discount = mathcer.group(1);
            }

            String priceTit = book.xpath("/li/div[@class='infor']/div[@class='priceWrap']/del/text()").get()
                    .replaceAll("¥", ""); // 定价

            String recolagu = book.xpath("/li/div[@class='infor']/p[@class='recoLagu']/text()").get(); // 摘要

            ChinaBookModel model = ChinaBookModel.builder().authorInfo(authorInfo).bookName(bookName)
                    .category1(category1).category2(category2).detailUrl(detailUrl)
                    .discount(Double.parseDouble(discount)).imageUrl(imageUrl).originUrl(originUrl)
                    .priceTit(Double.parseDouble(priceTit)).publisher(publisher).publishTime(publishTime)
                    .recolagu(recolagu).sellPrice(Double.parseDouble(sellPrice)).build();
            list.add(model);
        }
        /**
         * 将数据发送到存储层，进行入库
         */
        page.putField("result", list);

        // 判断是否需要继续执行爬去任务
        if (!ChinaBookSpiderService.isStop()) {
            ChinaBookPipeline chinaBookPipeline = (ChinaBookPipeline) applicationContextUtils
                    .getBean("chinaBookPipeline");
            // 翻页操作
            try {
                String url = "http://www.bookschina.com";
                String nextPage = page.getHtml()
                        .xpath("//div[@class='pagination']/div[@class='paging']/ul/li[@class='next']/a/@href").get();
                if (StringUtils.isNotBlank(nextPage)) {
                    Spider spider = Spider.create(new ChinaBookDetailParser(category1, category2)).thread(5)
                            .addPipeline(chinaBookPipeline).addUrl(url + nextPage);
                    ChinaBookSpiderService chinaBookSpiderService = (ChinaBookSpiderService) applicationContextUtils
                            .getBean("chinaBookSpiderService");
                    chinaBookSpiderService.addSpider(spider);
                    // 使用线程池提交任务
                    threadpool.submit(new SpiderRunJob(spider));
                }
            } catch (Exception e) {
                LOG.error(page.getRequest().getUrl() + " 翻页错误，Error：" + e.getMessage());
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public String getCategory1() {
        return category1;
    }

    public void setCategory1(String category1) {
        this.category1 = category1;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

}
