package com.kongling.bourse.data.kline.websocket;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kongling.bourse.data.kline.common.service.ICommonDataService;
import com.kongling.bourse.data.kline.entity.PO.GoodsOrghisInfo;
import config.redis.RedisUtils;
import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * @author 张子艺
 * @packge com.business.webSocket
 * @data 2019-01-14 13:06
 * @project Currency
 */
@Component
@Slf4j
@Data
@ServerEndpoint(prefix ="netty-websocket")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static volatile int onlineCount = 0;

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ICommonDataService commonDataService;

    private static ConcurrentMap<ChannelId, Session> concurrentHashMap = Maps.newConcurrentMap();

    public ConcurrentMap<ChannelId, Session> getWebSocketSet() {
        return concurrentHashMap;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, ParameterMap parameterMap) {
        //在线数加1
        addOnlineCount(session.id(), session);
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        subOnlineCount(session);           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 接收客户端发送信息
     *
     * @param message 接收客户端发送信息
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        try{
            GoodsOrghisInfo orghisInfo =JSONObject.toJavaObject(JSONObject.parseObject(message),GoodsOrghisInfo.class);
            commonDataService.putGoodsOrghisInfo(orghisInfo);
           // redisUtils.lpushQueue(RedisKeysPrefix.FROM_LINKED_QUEUE_NEW_DATE_MESSAGE, JSONObject.toJSONString(orghisInfo, SerializerFeature.WriteMapNullValue));

        }catch (Exception e){
            session.sendText(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 发生错误时调用
     *
     * @OnError
     **/
    @OnError
    public void onError(Session session, Throwable error) {
        try {
            log.info("推送错误：" + ExceptionUtils.getStackTrace(error));
            subOnlineCount(session);
        } catch (Exception e) {

        }
    }

    public void sendMessage(Session session, String message) throws IOException {
        session.sendText(message);
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 增加在线客户端连接数
     */

    public void addOnlineCount(ChannelId stockUserId, Session session) {
        //加入set中
        concurrentHashMap.put(stockUserId, session);
        WebSocketServer.onlineCount++;
    }

    /**
     * 减少在线客户端连接数
     */

    public void subOnlineCount(Session session) {
        //从set中删除
        for (Map.Entry<ChannelId, Session> item : concurrentHashMap.entrySet()) {
            if (item.getValue() == session) {
                concurrentHashMap.remove(item.getKey());
                WebSocketServer.onlineCount--;
            }
        }
    }

    /**
     * 群发自定义消息
     */
    static long start = 0L;
   static int i=1;
    public void sendInfo(String message) {
        for (Map.Entry<ChannelId, Session> item : concurrentHashMap.entrySet()) {
            try {
                if(item.getValue().isOpen()) {
                    /**
                     * 请求数据类型
                     */
                    synchronized (item) {
                        item.getValue().sendText(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(i==1){
            start=System.currentTimeMillis();
        }
        System.out.println("时间："+(System.currentTimeMillis()-start)+",推送："+i++);
    }

    /**
     * 自定义单发消息
     */
    public Boolean sendOneMsg(Long stockUserId, String message) {
        Boolean successIs = false;
        Session session = concurrentHashMap.get(stockUserId);
        if (session != null && session.isOpen()) {
            try {
                session.sendText(message);
                successIs = true;
            } catch (Exception e) {
                log.error("发送失败：\n"+ExceptionUtils.getFullStackTrace(e));
            }
        }
        return successIs;
    }
}