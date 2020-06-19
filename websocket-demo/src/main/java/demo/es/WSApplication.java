package demo.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class WSApplication {
    public static void main(String[] args) {
        SpringApplication.run(WSApplication.class,args);
    }
}
