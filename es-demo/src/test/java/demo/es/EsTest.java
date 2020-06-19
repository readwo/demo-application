package demo.es;

import cn.hutool.core.util.StrUtil;
import demo.es.vo.HistoryTravelRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.cardinality.ParsedCardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {


    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Test
    public void test1() throws Exception{
// 搜索请求对象
        SearchRequest searchRequest = new SearchRequest("trip_info_202005");
        // 指定类型
        searchRequest.types("_doc");
        // 搜索源构建对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        searchSourceBuilder.from(10);
        searchSourceBuilder.size(10);
        // 搜索方式
        // matchAllQuery搜索全部
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tansTime").gt("2020-05-15 00:00:00").lt("2020-05-16 01:00:00"));
        boolQueryBuilder.must(QueryBuilders.termQuery("vehicleNo","豫ND9603"));
        //boolQueryBuilder.must(QueryBuilders.rangeQuery("curareacode").gt("110000").lt("119999"));
        //boolQueryBuilder.(AggregationBuilders.cardinality("group_by_state").field("travelId"));
        //searchSourceBuilder.query(QueryBuilders.rangeQuery("curareacode").gt("110000").lt("119999"));
        //searchSourceBuilder.query(QueryBuilders.rangeQuery("tansTime").gt("2020-05-15 00:00:00").lt("2020-05-16 00:00:00"));
        /*searchSourceBuilder.aggregation(AggregationBuilders.terms("group_by_state")
            .field("travelId").size(20).order(BucketOrder.key(true)));*/

        searchSourceBuilder.sort("tansTime", SortOrder.ASC);

        searchSourceBuilder.query(boolQueryBuilder);
        // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        searchSourceBuilder.fetchSource(new String[]{"travelId","vehicleNo","tansTime"},new String[]{});
        // 向搜索请求对象中设置搜索源
        searchRequest.source(searchSourceBuilder);


        // 执行搜索,向ES发起http请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();



/*
        Aggregation group_by_state = stringAggregationMap.get("group_by_state");
        ParsedCardinality group_by_state1 = (ParsedCardinality)group_by_state;
        System.out.println(group_by_state1.getValue());
        Map<String, Object> metaData = group_by_state.getMetaData();*/


        // 搜索结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的总记录数
        long totalHits = hits.getTotalHits();
        System.out.println(totalHits);

        /*Terms group_by_state = searchResponse.getAggregations().get("group_by_state");
        for (Terms.Bucket bucket : group_by_state.getBuckets()) {
            System.out.println(bucket.getKey());
        }*/


        // 得到匹配度高的文档
        SearchHit[] searchHits = hits.getHits();
        // 日期格式化对象
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(SearchHit hit:searchHits){
            // 文档的主键
            String id = hit.getId();
            // 源文档内容
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            System.out.println(sourceAsMap);

        }

    }


    @Test
    public void count1() throws Exception{
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.rangeQuery("tansTime").gt("2020-05-15 00:00:00").lt("2020-05-15 01:00:00"));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        searchSourceBuilder.size(0);
        // 搜索方式
        // matchAllQuery搜索全部
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tansTime").gt("2020-05-19 00:00:00").lt("2020-05-20 00:00:00"));
       // boolQueryBuilder.must(QueryBuilders.rangeQuery("curareacode").gt("110000").lt("119999"));
        // 执行搜索,向ES发起http请求
        searchSourceBuilder.query(boolQueryBuilder);
        //AggregationBuilders.filter("",QueryBuilders.rangeQuery(""));
        searchSourceBuilder.aggregation(AggregationBuilders.cardinality("group_by_state")
            .field("travelId"));

        SearchRequest searchRequest = new SearchRequest("trip_incomplete_202005,trip_info_202005");

        searchRequest.types("_doc");
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search);
        Map<String, Aggregation> stringAggregationMap = search.getAggregations().asMap();
        Aggregation group_by_state = stringAggregationMap.get("group_by_state");
        ParsedCardinality group_by_state1 = (ParsedCardinality)group_by_state;
        System.out.println(group_by_state1.getValue());


    }



    @Test
    public void test12(){
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 搜索方式
        // matchAllQuery搜索全部
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.rangeQuery("tansTime")
            .gt("2020-05-13 00:00:00")
            .lt("2020-05-14 00:00:00"));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("curareacode").gt(110000).lt(119999));
//        boolQueryBuilder.must(AggregationBuilders.cardinality("group_by_state").field("vehicleNo"));
        searchSourceBuilder.aggregation(AggregationBuilders.cardinality("vehicleNo")
                .field("vehicleNo"));
        searchSourceBuilder.query(boolQueryBuilder);

        // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        //searchSourceBuilder.fetchSource(new String[]{"vehicleNo","tansTime","gantryId"},new String[]{});
        // 向搜索请求对象中设置搜索源
//        searchRequest.source(searchSourceBuilder);
        // 执行搜索,向ES发起http请求
        CountRequest countRequest = new CountRequest("trip_incomplete_202005");

        countRequest.source(searchSourceBuilder);
        CountResponse count = null;
        try {
            count = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("查询结果："+count.getCount());
    }





    private BoolQueryBuilder builderBool(HistoryTravelRequest request){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String provinceId = request.getProvinceId();
        if(StrUtil.isNotEmpty(provinceId)){
            String[] split = provinceId.split("\\|");
            String s = split[0];

            boolQueryBuilder.must(QueryBuilders.rangeQuery("curareacode").from(s+"0000").to(s+"9999"));
        }


        String vehPlate = request.getVehPlate();
        if(StrUtil.isNotEmpty(vehPlate)){
            //模糊查询
            //https://blog.csdn.net/PeakMoment/article/details/99678476
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("vehicleNo","*"+vehPlate+"*"));
        }

        String dataType = request.getDataType();
        String dataTypeKey = request.getDataTypeKey();
        if("3".equals(dataType)){//收费站

            boolQueryBuilder.must(QueryBuilders.termQuery("gantryId",1));
            //支持收费站的名称或编码的模糊查询
            if(StrUtil.isNotEmpty(dataTypeKey)){
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("gantryId","*"+dataTypeKey+"*"));
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("pointName","*"+dataTypeKey+"*"));
            }

        }else if ("4".equals(dataType)){//所有门架
            boolQueryBuilder.must(QueryBuilders.termQuery("gantryId",2));

            //支持门架的名称或编码的模糊查询
            if(StrUtil.isNotEmpty(dataTypeKey)){
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("gantryId","*"+dataTypeKey+"*"));
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("pointName","*"+dataTypeKey+"*"));
            }
        }

        String queryType = request.getQueryType();
        if("0".equals(queryType)){//0:按时间点查询


        }else if("1".equals(queryType)){ //1:按时间段查询
            String queryValue1 = this.TtoTime(request.getQueryValue1());
            String queryValue2 = this.TtoTime(request.getQueryValue2());
            boolQueryBuilder.must(QueryBuilders.rangeQuery("tansTime").from(queryValue1).to(queryValue2));


        }else if("2".equals(queryType)){ //2:按行程编码查询
            boolQueryBuilder.must(QueryBuilders.termQuery("travelId",request.getQueryValue1()));

        }

        return boolQueryBuilder;
    }

    private String TtoTime(String Ttime){
        StringBuilder stringBuilder = new StringBuilder(Ttime);
        StringBuilder replace = stringBuilder.replace(10, 11, " ");
        return replace.toString();
    }



}

