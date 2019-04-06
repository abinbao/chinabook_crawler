package com.ye.constant;

/**
 * 爬虫状态枚举类
 */
public enum Status {

    INIT("init"), ING("ing"), STOP("stop"), WAIT("wait");

    private final String name;

    private Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
