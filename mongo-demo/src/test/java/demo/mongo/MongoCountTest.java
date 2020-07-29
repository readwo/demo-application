package demo.mongo;

import cn.hutool.json.JSONArray;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import demo.mongo.utils.GpsCoordinateUtils;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCountTest {

    @Autowired
    MongoTemplate mongoTemplate;



    @Test
    public void count(){

        long count = mongoTemplate.count(Query.query(where("transTime").gt("2020-04-28 11:30:00").lt("2020-04-28 15:50:00")), "fscross");
        
        System.out.println("count：="+count);

        long l = mongoTemplate.getDb().getCollection("fscross").countDocuments(
            Filters.and(Filters.gt("transTime","2020-04-28 11:30:00"),Filters.lt("transTime","2020-04-28 15:50:00"))
        );


        System.out.println(l);
    }


    @Test
    public void aVoid(){

        FindIterable<Document> geoa = mongoTemplate.getDb().getCollection("geoa")
            .find().sort(Sorts.ascending("updateTime"));

        List<double[]> list = new ArrayList<>();
        for (Document document : geoa) {
            Object longitude = document.get("longitude");
            Object latitude = document.get("latitude");
            double lon = new Double(longitude.toString()).doubleValue();
            double lat = new Double(latitude.toString()).doubleValue();

            if(lat != 0.0 && lon != 0.0){
                double[] doubles = GpsCoordinateUtils.calWGS84toGC(lon,lat);
                list.add(doubles);
            }



        }
        JSONArray jsonArray = new JSONArray(list);
        System.out.println(jsonArray);

    }

    @Test
    public void et1(){
        this.getDocuments("2020-07-03T10:00:00","2020-07-03T09:00:00","20200703");
    }

    private void getDocuments(String now, String start,  String collectionName) {

        AggregateIterable<Document> aggregate = mongoTemplate.getDb().getCollection(collectionName).aggregate(Arrays.asList(
            Aggregates.match(
                Filters.and(
                    Filters.lt("tansTime", now),
                    Filters.gte("tansTime", start),
                    Filters.eq("pointType", 2)
                )
            ),
            Aggregates.group("$pointNo",
                Accumulators.sum("total", 1)
            )

        ));

        for (Document document : aggregate) {
            String id = document.get("_id", String.class);
            Integer total = document.get("total", Integer.class);
            System.out.println("id="+id+",total="+total);
            //System.out.println(total);

        }
    }

    @Test
    public void demo1222(){
        FindIterable<Document> documents1 = mongoTemplate.getDb().getCollection("bd_gps_enclosure").find(
            //Filters.and(
            Filters.eq("vehicleNo", "豫PV8178")
            //)
        );

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Document document : documents1) {
            System.out.println(document);
        }
    }


}
