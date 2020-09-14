package com.minio.es;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @author ：yeyh
 * @date ：Created in 2020/8/27 10:58
 * @description：
 * @modified By：
 */
@MapperScan("com.minio.es.mapper")
@EnableEurekaClient
@SpringBootApplication
public class MinioEsServiceApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(MinioEsServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MinioEsServiceApplication.class, args);
        logger.info("MinioEsService服务启动成功.");
    }
}