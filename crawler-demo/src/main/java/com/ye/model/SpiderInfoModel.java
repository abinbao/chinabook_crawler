package com.ye.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 爬虫信息表
 */
public class SpiderInfoModel extends BaseObject implements Serializable {

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
     * 爬虫描述
     */
    private String spiderDescription;

    private Date insertTime;

    private Date createTime;

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

    public String getSpiderDescription() {
        return spiderDescription;
    }

    public void setSpiderDescription(String spiderDescription) {
        this.spiderDescription = spiderDescription;
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
