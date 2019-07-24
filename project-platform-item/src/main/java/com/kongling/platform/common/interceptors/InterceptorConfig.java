package com.kongling.platform.common.interceptors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Zhang on 2018/8/26.
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public SudaPlatformInterceptor appinterceptor() {
        return new SudaPlatformInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            //TODO 配置不需要拦截的路径 APP PC 都配置
            //配置 不需要拦截的包和请求
             String[]excludePathPatterns=new String[]{
               "/app/user/login",
               "/app/user/register",
               "/swagger-ui.html",
               "/swagger-resources/**",
               "/webjars/**",
               "/v2/**",
               "/admin/user/login",
               "/common/status/**",
               "/app/comConfig/**",
            };
         registry.addInterceptor(  appinterceptor()).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);
    }


}
