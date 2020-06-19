package demo.es.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.websocket.Session;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsocketModal {

    private String sessionId;//websocket的sessionId
    private Session session;//websocket会话
    private String httpSessionId;//前端推送的唯一字符串
}
