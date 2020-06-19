package demo.mongo;

import cn.hutool.json.JSONArray;
import com.mongodb.client.FindIterable;
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

import java.util.ArrayList;
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


}
