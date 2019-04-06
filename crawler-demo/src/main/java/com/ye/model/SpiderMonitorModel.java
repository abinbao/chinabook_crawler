package com.ye.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 中国图书网爬虫监控信息
 */
public class SpiderMonitorModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    /**
     * 爬虫名称
     */
    private String spiderName;

    /**
     * 爬虫状态
     */
    private String spiderStatus;
    /**
     * 爬虫初始请求数量
     */
    private Integer spiderInitCount;
    /**
     * 爬虫正在请求中数量
     */
    private Integer spiderIngCount;

    /**
     * 爬虫请求完毕数量
     */
    private Integer spiderStopCount;

    private Date insertTime;

    private Date createTime;

    private String hour;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpiderName() {
        return spiderName;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }

    public String getSpiderStatus() {
        return spiderStatus;
    }

    public void setSpiderStatus(String spiderStatus) {
        this.spiderStatus = spiderStatus;
    }

    public Integer getSpiderInitCount() {
        return spiderInitCount;
    }

    public void setSpiderInitCount(Integer spiderInitCount) {
        this.spiderInitCount = spiderInitCount;
    }

    public Integer getSpiderIngCount() {
        return spiderIngCount;
    }

    public void setSpiderIngCount(Integer spiderIngCount) {
        this.spiderIngCount = spiderIngCount;
    }

    public Integer getSpiderStopCount() {
        return spiderStopCount;
    }

    public void setSpiderStopCount(Integer spiderStopCount) {
        this.spiderStopCount = spiderStopCount;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
