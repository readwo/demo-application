package demo.kafka.producer;


import cn.hutool.json.JSONUtil;
import demo.kafka.config.KafKaConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class KafkaProducer {


    @Autowired
    KafkaTemplate  kafkaTemplate;


    /**
     * Kafka 发布消息时如何选择 Partition
     * 1. 默认(当 key 为 null) 时采用 Round-robin 策略 send(String topic, @Nullable V data)
     * 2. 指定key(当 key 不为 null),对key做hash，分配 send(String topic, K key, @Nullable V data)
     * 3. 指定partition
     */
    public void send(){

        Trace trace = new Trace();
        String palte = CreatVehicle.getPalte();
        Integer l = (int)myHashCode(palte);
        trace.setVehicleId(palte);
        trace.setSendTime(LocalDateTime.now().toString());


        kafkaTemplate.send(KafKaConfig.TOPIC_LWLK_FORWARD,beanToString(trace));



    }

    private static long myHashCode(String str) {
        long h = 0;
        if (h == 0) {
            int off = 0;
            char val[] = str.toCharArray();
            long len = str.length();

            for (long i = 0; i < len; i++) {
                h = 31 * h + val[off++];
            }
        }
        return h % 2;
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
            return JSONUtil.toJsonPrettyStr(value);
        }

    }
}
