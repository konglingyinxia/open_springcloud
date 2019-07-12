package com.suda.eurka;


import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by Zhang on 2018/8/4.
 * springboot启动类
 * @author  kongling
 */
  @SpringBootApplication(exclude={DataSourceAutoConfiguration.class,
          MybatisAutoConfiguration.class})
  @EnableEurekaServer
public class EurekaServerApplication {

    public  static  void main(String [] args){
        System.err.println("启动中....");
        SpringApplication.run(EurekaServerApplication.class,args);
        System.err.println("启动成功！");
    }

}
