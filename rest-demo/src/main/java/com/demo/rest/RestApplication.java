package com.demo.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

@EnableScheduling
@SpringBootApplication
public class RestApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class);
    }

    /**
     * 定时任务并行执行，线程池大小默认为cpu核数
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        return taskScheduler;
    }

    //@Bean

    /*public static void main(String[] args) {
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        HttpHeaders headers = new HttpHeaders();

        //MediaType type = MediaType.MULTIPART_FORM_DATA;
        MediaType type = MediaType.APPLICATION_STREAM_JSON;
        headers.setContentType(type);


        MultiValueMap<String, Object> form = new LinkedMultiValueMap<String, Object>();

        String s = "{'hello':'world','vehicleColor':1}";
        InputStream inputStream = new ByteArrayInputStream(s.getBytes());
        form.add("file", inputStream);
        form.add("filename","hello.json");




        Object o = rest.postForObject("http://192.168.42.116:8082/demo", form, Object.class);
        //String s1 = objectResponseEntity.getBody().toString();
        System.out.println(o.toString());



    }*/



}
