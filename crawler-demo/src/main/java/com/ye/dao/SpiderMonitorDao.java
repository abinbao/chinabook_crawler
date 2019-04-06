package com.ye.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ye.model.SpiderMonitorModel;

/**
 * 爬虫监控信息查询
 */
@Mapper
public interface SpiderMonitorDao {

    /**
     * 爬虫监控信息
     * 
     * @param user
     * @return
     */
    @Insert("insert into `crawler_db`.`spider_monitor` (`id`, `spider_name`, `spider_init_count`, `spider_ing_count`, `spider_stop_count`, `spider_status`, `insert_time`, `create_time`) VALUES (null, #{spiderName}, #{spiderInitCount}, #{spiderIngCount}, #{spiderStopCount}, #{spiderStatus}, now(), now());")
    int insert(SpiderMonitorModel model);

    @Select("select * from spider_monitor where spider_name=#{spiderName} order by insert_time desc limit 10")
    List<SpiderMonitorModel> query(String spiderName);
}
