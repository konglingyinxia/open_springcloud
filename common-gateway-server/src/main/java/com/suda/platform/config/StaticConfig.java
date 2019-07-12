package com.suda.platform.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileUrlResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.net.MalformedURLException;

/**
 * 静态资源配置
 *
 * @author kongling
 * @package com.suda.platform.config
 * @date 2019-06-18  16:38
 * @project suda_cloud
 */
@Configuration
public class StaticConfig {
    @Autowired
    private MyConfig myConfig;

    @Configuration
    public class StaticRoute {
        @Bean
        RouterFunction<ServerResponse> staticResourceRouter() throws MalformedURLException {
            return RouterFunctions.resources(myConfig.getStaticPathPattern(),
                    new FileUrlResource(myConfig.getStaticLocations()));
        }
    }
}
