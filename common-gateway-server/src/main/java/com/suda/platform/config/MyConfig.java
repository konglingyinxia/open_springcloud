package com.suda.platform.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author kongling
 * @package com.suda.platform.config
 * @date 2019-06-18  16:42
 * @project suda_cloud
 */
@ConfigurationProperties(prefix = "my-config")
@Data
@Component
public class MyConfig {
    /**
     * 配置静态资源目录
     */
    private String staticLocations;

    /**
     * 配置静态资源访问路径前缀
     */
    private String  staticPathPattern;
}
