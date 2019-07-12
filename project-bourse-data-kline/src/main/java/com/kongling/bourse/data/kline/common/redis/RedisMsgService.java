package com.kongling.bourse.data.kline.common.redis;

import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 卫星
 * <p>
 * redis 消息监听处理发送 调用webSocket信息发送方法
 * @package com.admin.web.config.redis
 * @date 2019-04-03  10:46
 * @project cloud_template
 */
@Component
public class RedisMsgService {
    private static  Lock lock =new ReentrantLock();


    /**
     * 虚拟货币汇率通道
     */
    public void vbXExChangeRateChan(String message) {

    }
}
