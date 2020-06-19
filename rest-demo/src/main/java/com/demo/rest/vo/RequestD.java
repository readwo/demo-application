package com.demo.rest.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@ToString
public class RequestD implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "hello2")
    private String vehicleNo;

    @NotNull(message = "hello")
    private Integer vehicleColor;

    @Positive(message = "必须为正数")
    private Integer pageNo =1;

    @Positive(message = "必须为正数")
    private Integer pageSize = 10;

    private List<Map<String, Object>> dataList;
    
    



}
