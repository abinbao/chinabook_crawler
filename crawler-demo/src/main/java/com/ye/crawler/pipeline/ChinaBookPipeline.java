package com.ye.crawler.pipeline;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ye.dao.ChinaBookDao;
import com.ye.model.ChinaBookModel;
import com.ye.util.ApplicationContextUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 中国图书网; 爬虫存储层
 */
@Service
public class ChinaBookPipeline implements Pipeline {

    private static final Logger LOG = LoggerFactory.getLogger(ChinaBookPipeline.class);

    @Autowired
    ApplicationContextUtils applicationContextUtils;

    /**
     * 将图书的详细信息添加到数据库中
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<ChinaBookModel> list = resultItems.get("result");
        ChinaBookDao chinaBookDao = (ChinaBookDao) applicationContextUtils.getBean("chinaBookDao");
        for (ChinaBookModel model : list) {
            chinaBookDao.insert(model);
        }
    }
}
