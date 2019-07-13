package com.suda.eurka;


import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * springboot启动类
 * @author  kongling
 */
  @SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
          MybatisAutoConfiguration.class})
  @EnableEurekaServer
public class CommonEurekaServerApplication {

    public  static  void main(String [] args){
        System.err.println("启动中....");
        SpringApplication.run(CommonEurekaServerApplication.class,args);
        System.err.println("启动成功！");
    }

}
