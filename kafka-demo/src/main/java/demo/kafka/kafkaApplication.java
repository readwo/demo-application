package demo.kafka;


import demo.kafka.consumer.KafkaConsumer;
import demo.kafka.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerEndpointRegistrar;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootApplication
@Configuration
public class kafkaApplication implements CommandLineRunner{

    public static void main(String[] args) {
        SpringApplication.run(kafkaApplication.class,args);
    }


    @Autowired
    private KafkaProducer kafkaProducer;


    @Resource
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;


    @Override
    public void run(String... args) throws Exception {

        Set<String> listenerContainerIds = kafkaListenerEndpointRegistry.getListenerContainerIds();
        listenerContainerIds.stream().forEach(
                id ->{
                    MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer(id);
                    if(!listenerContainer.isRunning()){
                        System.out.println("启动kafka监听"+id);
                        listenerContainer.start();
                    }
                }
        );


        while (true){
            //Thread.sleep(100);
            kafkaProducer.send();
        }


    }
}
