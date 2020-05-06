package com.rabbit.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;

import com.rabbit.conifg.RabbitConfig;
import com.rabbit.service.MQSender;
import com.rabbit.vo.CreatVehicle;
import com.rabbit.vo.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 组件，向指定mq推数据
 */
@Component
public class DemoController {

    @Autowired
    @Qualifier(value = "remoteRabbitTemplate")
    RabbitTemplate remoteRabbitTemplate;

    @Autowired
    @Qualifier(value = "localRabbitTemplate")
    RabbitTemplate localRabbitTemplate;

    public JSONObject initDeom1(){
        JSONObject jsonObject = new JSONObject();

        try {
            int i = 0;
            while (true){
                Thread.sleep(100);
                Message message = new Message();
                message.setId(IdUtil.simpleUUID());
                message.setName(CreatVehicle.getPalte());
                message.setTime(LocalDateTime.now());

                if(i%2 == 0){
                    remoteRabbitTemplate.convertAndSend(RabbitConfig.REMOTE_PROCESS_QUEUE, MQSender.beanToString(message));
                }else {
                    localRabbitTemplate.convertAndSend(RabbitConfig.PROCESS_QUEUE,MQSender.beanToString(message));
                }
                i++;

                if(i>100){
                    break;
                }

            }


            jsonObject.put("code",200)
                    .put("msg","success");
            return  jsonObject;
        }catch (Exception e){
            jsonObject.put("code",500)
                    .put("msg","fail");
            return  jsonObject;
        }


    }

}
