package com.example.demo.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class DemoListener implements ApplicationListener<ApplicationReadyEvent> {



    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        applicationReadyEvent.getApplicationContext().close();
    }


}
