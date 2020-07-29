package demo.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticSearchConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchConfig.class);

    /**
     * elk集群地址
     */
    @Value("${elasticsearch.ip}")
    private String hostName;

    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    @Value("${elasticsearch.hostlist}")
    private String hostlist;


    @Bean // 高版本客户端
    public RestHighLevelClient restHighLevelClient() {
        // 解析 hostlist 配置信息。假如以后有多个，则需要用 ， 分开
        String[] split = hostlist.split(",");
        // 创建 HttpHost 数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        // 创建RestHighLevelClient客户端
        return new RestHighLevelClient(RestClient.builder(httpHostArray));
    }

    //项目主要使用 RestHighLevelClient，对于低级的客户端暂时不用
    @Bean
    public RestClient restClient() {
        // 解析hostlist配置信息
        String[] split = hostlist.split(",");
        // 创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        return RestClient.builder(httpHostArray).build();
    }
//    @Bean //高版本客户端
//    public RestHighLevelClient restHighLevelClient() {
//        // 创建 HttpHost 数组，其中存放es主机和端口的配置信息
//        HttpHost httpHost = new HttpHost(hostName, port,"http");
//
//
//        // 创建RestHighLevelClient客户端
//        return new RestHighLevelClient(RestClient.builder(httpHost));
//    }
//
//
//    // 项目主要使用 RestHighLevelClient，对于低级的客户端暂时不用
//    @Bean
//    public RestClient restClient() {
//
//
//        HttpHost host = new HttpHost(hostName, port,"http");
//
//        return RestClient.builder(host).build();
//    }

}