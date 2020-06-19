package demo.es;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import demo.es.vo.HistoryTravelRequest;
import demo.es.vo.HistoryTravelResponse;
import demo.es.vo.ResponseObject;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.BucketOrder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest3 {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void findHistoryTravel() {
        HistoryTravelRequest request = new HistoryTravelRequest();
        //request.setProvinceId("36");
        request.setDataType("4");
        request.setQueryType("1");

        request.setQueryValue1("2020-05-15 09:00:00");
        request.setQueryValue2("2020-05-15 14:00:00");

        BoolQueryBuilder boolQueryBuilder = this.builderBool(request);
        HistoryTravelResponse response = new HistoryTravelResponse();

        long count = this.getCount(boolQueryBuilder);
        System.out.println(count);
        //if (count == 0) return ResponseObject.success(response);

        int pageNo = request.getPageNo().intValue();
        int pageSize = request.getPageSize().intValue();
        int num = pageNo * pageSize;
        int from = (pageNo - 1) * pageSize;

        long totalPage = count / pageSize;

        if (count % pageSize != 0)
            totalPage++;

        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalCount(count);
        response.setTotalPage((int)totalPage);

        List<Terms.Bucket> bucketList = getTotal(boolQueryBuilder, num, "trip_info_202005");

        int bucketLength = bucketList.size();

        System.out.println("bucketLength:="+bucketLength);
        if (bucketLength < from) {
           // return ResponseObject.success(response);
        }

        List<HistoryTravelResponse.VehTrack> vehTracks = new ArrayList<>();

        for ( int i = from;i<bucketLength;i++){
            Terms.Bucket bucket = bucketList.get(i);
            String travelId = bucket.getKey().toString();

            HistoryTravelResponse.VehTrack vehTrack = this.getVehTrack(travelId, request);
            if (vehTrack != null){
                vehTracks.add(vehTrack);
            }
        }

        response.setVehTrack(vehTracks);


        System.out.println(JSONUtil.toJsonStr(response));
        //return ResponseObject.success(response);
    }


    private HistoryTravelResponse.VehTrack getVehTrack(String travelId, HistoryTravelRequest request){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String provinceId = request.getProvinceId();
        if (StrUtil.isNotEmpty(provinceId)) {
            String[] split = provinceId.split("\\|");
            String s = split[0];

            boolQueryBuilder.must(QueryBuilders.rangeQuery("curareacode").from(s + "0000").to(s + "9999"));
        }
        /*String queryType = request.getQueryType();
        if ("1".equals(queryType)) {
            String queryValue1 = TtoTime(request.getQueryValue1());
            String queryValue2 = TtoTime(request.getQueryValue2());
            boolQueryBuilder.must(QueryBuilders.rangeQuery("tansTime").from(queryValue1).to(queryValue2));


        }*/

        boolQueryBuilder.must(QueryBuilders.termQuery("travelId", travelId));


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("trip_info_202005");


        searchSourceBuilder.sort("tansTime", SortOrder.ASC);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(100);
        searchSourceBuilder.fetchSource(new String[]{"vehicleNo","vehicleColor","tansTime"},new String[]{});
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = search.getHits().getHits();
            Map<String, Object> sourceAsMap = hits[0].getSourceAsMap();

            String vehicleNo = sourceAsMap.get("vehicleNo").toString();
            int vehicleColor = Integer.parseInt(sourceAsMap.get("vehicleColor").toString());

            String startTime = sourceAsMap.get("tansTime").toString();

            Map<String, Object> sourceAsMapEnd = hits[(hits.length-1)].getSourceAsMap();
            String endTime = sourceAsMapEnd.get("tansTime").toString();

            HistoryTravelResponse.VehTrack vehTrack = new HistoryTravelResponse.VehTrack();
            vehTrack.setEndLat(travelId);
            vehTrack.setVehPlate(vehicleNo);
            vehTrack.setVehPlateColor("1");
            vehTrack.setStartTime(startTime);
            vehTrack.setEndTime(endTime);
            vehTrack.setDataQualityLevel("4");

            return vehTrack;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }



    /**
     * 查唯一行程list
     *
     * @param bool
     * @param num
     */
    private List<Terms.Bucket> getTotal(BoolQueryBuilder bool, int num, String indices) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.terms("group_by_state")
            .field("travelId").size(num).order(BucketOrder.key(true)));


        SearchRequest searchRequest = new SearchRequest("trip_info_202005");

        searchSourceBuilder.query(bool);

        searchRequest.source(searchSourceBuilder);
        System.out.println(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchResponse.getAggregations();

            Map<String, Aggregation> stringAggregationMap = aggregations.asMap();

            SearchHits hits = searchResponse.getHits();
            // 匹配到的总记录数
            long totalHits = hits.getTotalHits();
            System.out.println(totalHits);

            Terms group_by_state = searchResponse.getAggregations().get("group_by_state");
            return (List<Terms.Bucket>) group_by_state.getBuckets();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new ArrayList<>();


    }


    private long getCount(BoolQueryBuilder bool) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(bool);
        searchSourceBuilder.aggregation(AggregationBuilders.cardinality("group_by_state")
            .field("travelId"));
        SearchRequest searchRequest = new SearchRequest("trip_incomplete_202005");
        searchRequest.types("_doc");
        searchRequest.source(searchSourceBuilder);
        //System.out.println(searchSourceBuilder);
        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Map<String, Aggregation> stringAggregationMap = search.getAggregations().asMap();
            ParsedCardinality group_by_state = (ParsedCardinality) stringAggregationMap.get("group_by_state");

            return group_by_state.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;

    }


    /**
     * 构造多条件查询语句
     *
     * @param request
     * @return
     */
    private BoolQueryBuilder builderBool(HistoryTravelRequest request) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String provinceId = request.getProvinceId();
        if (StrUtil.isNotEmpty(provinceId)) {
            String[] split = provinceId.split("\\|");
            String s = split[0];

            boolQueryBuilder.must(QueryBuilders.rangeQuery("curareacode").from(s + "0000").to(s + "9999"));
        }


        String vehPlate = request.getVehPlate();
        if (StrUtil.isNotEmpty(vehPlate)) {
            //模糊查询
            //https://blog.csdn.net/PeakMoment/article/details/99678476
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("vehicleNo", "*" + vehPlate + "*"));
        }

        String dataType = request.getDataType();
        String dataTypeKey = request.getDataTypeKey();
        if ("3".equals(dataType)) {//收费站

            boolQueryBuilder.must(QueryBuilders.termQuery("gantryType", 1));
            //支持收费站的名称或编码的模糊查询
            if (StrUtil.isNotEmpty(dataTypeKey)) {
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("gantryId", "*" + dataTypeKey + "*"));
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("pointName", "*" + dataTypeKey + "*"));
            }

        } else if ("4".equals(dataType)) {//所有门架
            boolQueryBuilder.must(QueryBuilders.termQuery("gantryType", 2));

            //支持门架的名称或编码的模糊查询
            if (StrUtil.isNotEmpty(dataTypeKey)) {
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("gantryId", "*" + dataTypeKey + "*"));
                boolQueryBuilder.should(QueryBuilders.wildcardQuery("pointName", "*" + dataTypeKey + "*"));
            }
        }

        String queryType = request.getQueryType();
        if ("0".equals(queryType)) {//0:按时间点查询


        } else if ("1".equals(queryType)) { //1:按时间段查询
            String queryValue1 = this.TtoTime(request.getQueryValue1());
            String queryValue2 = this.TtoTime(request.getQueryValue2());
            boolQueryBuilder.must(QueryBuilders.rangeQuery("tansTime").from(queryValue1).to(queryValue2));


        } else if ("2".equals(queryType)) { //2:按行程编码查询
            boolQueryBuilder.must(QueryBuilders.termQuery("travelId", request.getQueryValue1()));

        }

        return boolQueryBuilder;
    }


    private String TtoTime(String Ttime){
        StringBuilder stringBuilder = new StringBuilder(Ttime);
        StringBuilder replace = stringBuilder.replace(10, 11, " ");
        return replace.toString();
    }

    private  String timeIncrT(String timeStr){
        StringBuilder stringBuilder = new StringBuilder(timeStr);
        StringBuilder replace = stringBuilder.replace(10, 11, "T");
        return replace.toString();
    }
}
