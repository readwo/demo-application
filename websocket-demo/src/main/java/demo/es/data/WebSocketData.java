package demo.es.data;

import demo.es.vo.WebsocketModal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketData {
    public static Map<String, WebsocketModal> sessionMap = new ConcurrentHashMap<>();


}
