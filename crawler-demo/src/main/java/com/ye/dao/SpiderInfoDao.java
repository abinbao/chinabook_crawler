package com.ye.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ye.model.SpiderInfoModel;

/**
 * 爬虫信息查询
 */
@Mapper
public interface SpiderInfoDao {
    // 查询爬虫信息
    @Select({ "<script>", "select * from spider_list", "</script>" })
    List<SpiderInfoModel> query();

    // 更新爬虫状态
    @Update("update spider_list set `spider_status`=#{spiderStatus} where `spider_name`=#{spiderName}")
    void updateSpiderInfoBySpiderName(SpiderInfoModel model);

    /**
     * 项目重新启动时，更新所有的爬虫状态
     */
    @Update("update spider_list set `spider_status`='STOP' ,`create_time`=now()")
    void updateAllSpiderStatus();

    /**
     * 根据爬虫名称更新爬虫状态
     * 
     * @param spidername
     */
    @Update("update spider_list set `spider_status`='STOP' ,`create_time`=now() where spider_name=#{spidername}")
    void updateSpiderStatusByName(String spidername);

}
