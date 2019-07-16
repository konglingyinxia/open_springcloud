package com.kongling.txlcn;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author kongling
 * @date 2019-05-09  11:39
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagerServer
public class CommonTxlcnManagerServerApplication {
    public static void main(String[] args) {
        System.err.println("分布式事务管理器启动....");
        SpringApplication.run(CommonTxlcnManagerServerApplication.class, args);
        System.err.println("分布式事务管理器启动成功！");
    }
}
