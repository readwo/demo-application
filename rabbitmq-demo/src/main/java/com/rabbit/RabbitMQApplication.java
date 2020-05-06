package com.rabbit;

import com.rabbit.controller.DemoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class RabbitMQApplication  implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class,args);
    }

    @Autowired
    DemoController demoController;

    /**
     * 程序启动便启动，不用请求调用
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        demoController.initDeom1();
    }
}