package demo.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafKaConfig {

    public final static String TOPIC_KAFKA = "topic_kafka";

    public final static String TOPIC_KAFKA_S = "topic_kafka_s";
    public final static String TOPIC_KAFKA_T = "topic_kafka_t";


    public static final String TOPIC_LWLK_FORWARD = "first";


    /*
     @Bean
    NewTopic initialTopic(){
        return new NewTopic(TOPIC_LWLK_FORWARD,2,(short) 3);
    }*/

    @Bean
    NewTopic initialTopic2(){
        return new NewTopic(TOPIC_KAFKA_S,1,(short)4);
    }

    @Bean
    NewTopic initialTopic3(){
        return new NewTopic(TOPIC_KAFKA_T,1,(short)4);
    }

}
