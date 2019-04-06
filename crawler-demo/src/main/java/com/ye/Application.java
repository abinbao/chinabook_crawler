package com.ye;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ye.dao.SpiderInfoDao;

/**
 * 项目运行初始类
 */
@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class Application implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    SpiderInfoDao spiderInfoDao;

    /**
     * 开始启动
     * 
     * @param args
     */
    public static void main(String[] args) {
        logger.info("Starting ...");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 更新所有爬虫的状态信息
        spiderInfoDao.updateAllSpiderStatus();
    }

}