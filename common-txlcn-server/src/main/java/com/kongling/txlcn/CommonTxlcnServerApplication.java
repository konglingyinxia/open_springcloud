package com.kongling.txlcn;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author kongling
 * @date 2019-05-09  11:39
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class})
@EnableEurekaClient
@EnableTransactionManagerServer
public class CommonTxlcnServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(CommonTxlcnServerApplication.class);


    public static void main(String[] args) {
        logger.info("分布式事务管理器启动....");
        SpringApplication.run(CommonTxlcnServerApplication.class, args);
        logger.info("分布式事务管理器启动成功！");
    }
}
