package com.kongling.platform.common.redis;

import com.kongling.platform.common.websocket.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 虚拟货币汇率通道
     */
    public void vbXExChangeRateChan(String message) {
        //群发消息
        for (WebSocketServer item : webSocketServer.getWebSocketSet()) {
                if(!item.getSession().isOpen()){
                    webSocketServer.onClose();
                   continue;
                }
                /**
                 * 请求数据类型
                 */
                String type = item.getSession().getPathParameters().get("type");
                if(type == null || !webSocketServer.getTypes().contains(type)){
                    continue;
                }
            try {
                lock.lock();
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
}
