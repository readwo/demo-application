package demo.kafka.config;

import org.springframework.stereotype.Service;

@Service
public class Demo1Impl implements Demo1 {
    @Override
    public String demo1(String msg) {


        System.out.println(msg);


        return "null";
    }
}
