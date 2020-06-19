package com.demo.rest.contor;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.demo.rest.vo.RequestD;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class DemoC {


    @RequestMapping("/test1")
    public Object demo(@Valid @RequestBody RequestD stringMap,BindingResult bindingResult ){
        if(bindingResult.hasErrors()){
            return JSONUtil.createObj().put("nne",bindingResult.getFieldError().getDefaultMessage());
        }
        System.out.println(stringMap.toString());

        return new RequestD();
    }




}
