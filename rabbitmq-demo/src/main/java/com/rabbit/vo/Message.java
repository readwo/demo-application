package com.rabbit.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private String id;

    private String name;

    private LocalDateTime time;


}
