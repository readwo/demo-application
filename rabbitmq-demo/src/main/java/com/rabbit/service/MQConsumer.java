package com.rabbit.service;


import com.alibaba.fastjson.JSONObject;
import com.rabbit.conifg.RabbitConfig;
import com.rabbit.vo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQConsumer {

    @RabbitHandler
    @RabbitListener(queues = RabbitConfig.PROCESS_QUEUE)
    public void consumer(String msg){
        Message me= JSONObject.parseObject(msg, Message.class);

    }
}
