package com.rabbit.service;

import com.alibaba.fastjson.JSON;
import com.rabbit.conifg.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * rabiitMQ 发送
 */
@Service
public class MQSender {
    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    @Qualifier(value = "localRabbitTemplate")
    RabbitTemplate localRabbitTemplate;

    /**
     * 消息发送示例
     *
     * @param message
     */
    public void sendTopic(Object message) {
        String msg = this.beanToString(message);
        logger.info("send topic message:" + msg);
        localRabbitTemplate.convertAndSend(RabbitConfig.PROCESS_QUEUE, msg);
    }


    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == long.class || clazz == Long.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }

    }

}
