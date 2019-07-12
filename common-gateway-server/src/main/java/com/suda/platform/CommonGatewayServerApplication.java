package com.suda.platform;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author kongling
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class})
/*
@EnableEurekaClient
*/
public class CommonGatewayServerApplication {

    public static void main(String[] args) {
        System.err.println("网关启动中....");
        SpringApplication.run(CommonGatewayServerApplication.class, args);
        System.err.println("网关启动成功！");
    }

}
