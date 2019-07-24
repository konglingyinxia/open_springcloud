package com.kongling.platform.common.redis;

import com.constant.RedisKeysPrefix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * redis 消息订阅通道
 *
 * @author 张子艺
 * @packge com.currency.admin.config
 * @data 2019-01-16 09:41
 * @project currencycloud
 */
@Configuration
@Component
public class RedisListenerConfig {
    /**
     * 配置redis 监听器  订阅通道可配置多个 相对应消息处理器也需配置多个
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter1) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        /**
         * 消息通道 可添加多个
         */
        container.addMessageListener(listenerAdapter1, new PatternTopic(RedisKeysPrefix.VB_XEX_CHANGE_RATE_CHANNEL));

        return container;
    }


    /**
     * 配置redis 监听消息处理器 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     */
    @Bean
    MessageListenerAdapter listenerAdapter1(RedisMsgService redisReceiver) {
        return new MessageListenerAdapter(redisReceiver, "vbXExChangeRateChan");
    }


}
