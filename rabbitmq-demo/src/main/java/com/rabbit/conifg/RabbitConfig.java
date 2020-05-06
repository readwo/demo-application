package com.rabbit.conifg;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * springboot 集成多个mq
 */
@Configuration
public class RabbitConfig {


    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.publisher-confirms}")
    private boolean publisherConfirm;

    @Value("${rabbitmq.virtual-host}")
    private String virtualHost;

    /**
     * 正常消费的队列
     */
    public final static String PROCESS_QUEUE = "local_process_queue";
    public final static String REMOTE_PROCESS_QUEUE = "remote_process_queue";

    /**
     * 正常队列对应的exchange
     */
    public final static String PROCESS_EXCHANGE_NAME = "local_process_exchange";
    public final static String REMOTE_PROCESS_EXCHANGE_NAME = "remote_process_exchange";


    public ConnectionFactory rabbitConfiguration(String host, int port) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setPublisherConfirms(publisherConfirm); //必须要设置
        return connectionFactory;
    }



    @Bean(value = "localFactory")
    @Primary
    public ConnectionFactory localConnectionFactory(@Value("${rabbitmq.host}") String host,
                                                    @Value("${rabbitmq.port}") int port) {

        return this.rabbitConfiguration(host,port);
    }

    @Bean(value = "remoteFactory")
    public ConnectionFactory remoteConnectionFactory(@Value("${remote.rabbitmq.host}") String host,
                                                     @Value("${remote.rabbitmq.port}") int port) {

        return this.rabbitConfiguration(host,port);
    }






    public SimpleRabbitListenerContainerFactory rabbitFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean(value = "localRabbitListener")
    public SimpleRabbitListenerContainerFactory localRabbitListeners(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("localFactory") ConnectionFactory connectionFactory) {
        return this.rabbitFactory(configurer, connectionFactory);
    }

    @Bean(value = "remoteRabbitListener")
    public SimpleRabbitListenerContainerFactory remoteRabbitListeners(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            @Qualifier("remoteFactory") ConnectionFactory connectionFactory) {
        return this.rabbitFactory(configurer, connectionFactory);
    }



    @Bean(value = "remoteAdmin")
    public RabbitAdmin adminBillCollection(@Qualifier("remoteFactory") ConnectionFactory factory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(factory);
        rabbitAdmin.declareExchange(remoteExchange());
        rabbitAdmin.declareQueue(remoteQueue());
        return rabbitAdmin;
    }

    @Bean(value = "localAdmin")
    public RabbitAdmin adminBillLocal(@Qualifier("localFactory") ConnectionFactory factory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(factory);
        rabbitAdmin.declareExchange(processExchange());
        rabbitAdmin.declareQueue(processQueue());
        return rabbitAdmin;
    }

    @Bean
    public TopicExchange processExchange() {
        return new TopicExchange(PROCESS_EXCHANGE_NAME);
    }



    @Bean
    public Queue processQueue() {
        return QueueBuilder.durable(PROCESS_QUEUE)
                .build();
    }


    @Bean
    public TopicExchange remoteExchange() {
        return new TopicExchange(REMOTE_PROCESS_EXCHANGE_NAME);
    }



    @Bean
    public Queue remoteQueue() {
        return QueueBuilder.durable(REMOTE_PROCESS_QUEUE)
                .build();
    }



    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @return
     */
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(processQueue()).to(processExchange()).with("topic.message");
    }

    @Bean
    Binding bindingRemoteExchangeMessage() {
        return BindingBuilder.bind(remoteQueue()).to(remoteExchange()).with("topic.message");
    }




    @Bean(value = "localRabbitTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate(@Qualifier("localFactory") ConnectionFactory factory ) {
        RabbitTemplate template = new RabbitTemplate(factory);

        return template;
    }

    @Bean(value = "remoteRabbitTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate remoteRabbitTemplate(@Qualifier("remoteFactory") ConnectionFactory factory ) {
        RabbitTemplate template = new RabbitTemplate(factory);
        return template;
    }

}
