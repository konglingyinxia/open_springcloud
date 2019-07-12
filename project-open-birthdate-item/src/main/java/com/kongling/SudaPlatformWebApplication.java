package com.kongling;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author kongling
 * @package com
 * @date 2019-05-09  11:39
 * @project suda_cloud
 */
@SpringBootApplication(scanBasePackages = {"config.advice"})
@MapperScan({"com.kongling.birthdate.mapper"})
@ComponentScan(basePackages = {"com.util","config"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.kongling.fegin"})
@EnableAsync
@EnableCaching
public class SudaPlatformWebApplication {
    private static final Logger logger = LoggerFactory.getLogger(SudaPlatformWebApplication.class);


    public static void main(String[] args) {
        logger.info("Springboot启动中....");
        SpringApplication.run(SudaPlatformWebApplication.class, args);
        logger.info("启动成功！");
    }
}
