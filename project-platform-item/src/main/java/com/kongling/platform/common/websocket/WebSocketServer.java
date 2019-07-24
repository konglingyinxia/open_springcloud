package com.kongling.platform.common.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 张子艺
 * @packge com.business.webSocket
 * @data 2019-01-14 13:06
 * @project Currency
 */
@Component
@Slf4j
@Data
@ServerEndpoint(value = "/websocket/data/{username}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static volatile int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private Session session;

    public CopyOnWriteArraySet<WebSocketServer> getWebSocketSet(){
        return webSocketSet;
    }
    /**
     * 推送数据类型
     */
    private static final Set<String> TYPES = Sets
            .newHashSet("VB_XEX_CHANGE_RATE_CHANNEL");

    public Set<String>  getTypes(){
        return TYPES;
    }


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        this.session = session;
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 接收客户端发送信息
     *
     * @param message 接收客户端发送信息
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        String userId = this.session.getId();
        try {
            JSONObject obj = JSON.parseObject(message);
            String type = obj.getString("type");
            if (StringUtils.isNotBlank(type) && TYPES.contains(type)) {
                this.session.getPathParameters().put("type", type);
                if (StringUtils.equalsIgnoreCase(type, "self_select")) {
                    this.session.getPathParameters().put("list", obj.get("list").toString());
                }
            } else {
                log.info(String.format("%s,传入参数没有type或类型不正确：%s", userId, message));
            }
        } catch (Exception e) {
            log.info(String.format("%s,传入参数没有type或类型不正确：%s", userId, message));
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
            subOnlineCount();
        } catch (Exception e) {

        }
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public void sendInfo(String message) {

        //群发消息
        for (WebSocketServer item : webSocketSet) {
            try {
                /**
                 * 请求数据类型
                 */
                String type = item.session.getPathParameters().get("type");

                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 增加在线客户端连接数
     */

    public void addOnlineCount() {
        //加入set中
        webSocketSet.add(this);
        WebSocketServer.onlineCount++;
    }

    /**
     * 减少在线客户端连接数
     */

    public void subOnlineCount() {
        //从set中删除
        webSocketSet.remove(this);
        WebSocketServer.onlineCount--;
    }
}
