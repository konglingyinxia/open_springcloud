package com.kongling.platform.common.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yeauty.standard.ServerEndpointExporter;

/**
 * @author 张子艺
 * @packge com.controller.com.suda.platform.common.websocket
 * @data 2019-01-14 13:04
 * @project Currency
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        return new ServerEndpointExporter();
    }

}
