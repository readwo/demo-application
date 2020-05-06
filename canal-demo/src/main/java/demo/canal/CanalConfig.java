package demo.canal;


import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CanalConfig {

    @Bean
    public CanalConnector canalConnector(){
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.206.128", 11111),
                "example",
                "",
                "");
        System.out.println(connector+"---------canal实体");
        return connector;
    }
}
