package demo.kafka.consumer;

import cn.hutool.json.JSONObject;
import demo.kafka.config.Demo1;
import demo.kafka.config.KafKaConfig;
import demo.kafka.producer.CreatVehicle;
import demo.kafka.producer.Trace;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {


    @KafkaListener(id="test1",topics = KafKaConfig.TOPIC_LWLK_FORWARD,autoStartup = "false")
    public void consumer(ConsumerRecord<?,?> msg){
        System.out.println(msg.toString());

    }

    @Autowired
    Demo1 demo1;

    /*@KafkaListener(id="test2",topics = "topic_interface_bzx",autoStartup = "false")
    public void consumer2(ConsumerRecord<?,?> msg){
        System.out.println(msg.value());


    }*/


    public static void main(String[] args) {
        Trace trace = new Trace();
        trace.setVehicleId(CreatVehicle.getPalte());
        trace.setVehicleColor(1);
        JSONObject jsonObject = new JSONObject(trace);
        trace.getVehicleColor();
        jsonObject.get("vehicleColor");
        while(true){
            long l = System.nanoTime();
            System.out.println(trace.getVehicleId());
            long l1 = System.nanoTime();
            System.out.println(jsonObject.get("vehicleId"));
            long l2 = System.nanoTime();
            System.out.println(l1-l);
            System.out.println(l2-l1);
        }



    }
}
