package com.kongling.zipkin;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * @author kongling
 * @date 2019-05-09  11:39
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class})
@EnableEurekaClient
@EnableZipkinServer
public class CommonZipkinServerApplication {
    public static void main(String[] args) {
        System.err.println("链路追踪管理器启动....");
        SpringApplication.run(CommonZipkinServerApplication.class, args);
        System.err.println("链路追踪启动成功！");
    }
}
