package demo.es.server;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws")
@Slf4j
public class WebSocket implements ApplicationContextAware {



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }


    @OnMessage
    public void onMessage(String message, Session session) throws Exception {



    }


    @OnOpen
    public void onOpen(Session session) throws Exception {
        log.info("websocket.id={}建立连接", session.getId());

    }


    @OnClose
    public void onClose(Session session) {

        log.info("id={}关闭链接", session.getId());
    }


    @OnError
    public void OnError(Session session, Throwable throwable) {
        log.error("{} 链接异常,{}", session.getId(), throwable.getMessage());
    }


}
