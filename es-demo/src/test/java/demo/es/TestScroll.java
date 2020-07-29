package demo.es;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestScroll {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Test
    public void tes1(){
        // 初始化scroll
        // 设定滚动时间间隔
        // 每个 scroll 请求（包含 scroll 参数）设置了一个新的失效时间。
        // 存活时间，当索引数据量特别大时，出现超时可能性大，此值适当调大
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(10L));

        SearchRequest searchRequest = new SearchRequest("trip_incomplete_202007");
        searchRequest.scroll(scroll);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("tansTime")
            .from("2020-07-18 00:00:00").to("2020-07-18 23:59:59"));

        searchSourceBuilder.size(5000);
//        searchSourceBuilder.fetchSource(new String[]{"vehicleNo,vehicleColor"},null);//设置返回字段和排除字段

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;

        int i = 1;
        try {
            System.out.println("====================================>第一次拉取");
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHit[] searchHits = searchResponse.getHits().getHits();

            for (SearchHit searchHit : searchHits) {
                Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                System.out.println(sourceAsMap.get("vehicleNo"));
            }
            String scrollId = searchResponse.getScrollId();
            while (searchHits != null && searchHits.length > 0) {
                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                scrollRequest.scroll(scroll);

                System.out.println("====================================>第"+ (++i) +"次拉取");
                searchResponse = restHighLevelClient.scroll(scrollRequest, RequestOptions.DEFAULT);

                scrollId = searchResponse.getScrollId();
                searchHits = searchResponse.getHits().getHits();
                for (SearchHit searchHit : searchHits) {
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    System.out.println(sourceAsMap.get("vehicleNo"));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
