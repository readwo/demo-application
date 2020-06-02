package demo.mongo;

import com.mongodb.client.model.Filters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoCountTest {

    @Autowired
    MongoTemplate mongoTemplate;



    @Test
    public void count(){


        long l = mongoTemplate.getDb().getCollection("fscross").countDocuments(
            Filters.and(Filters.gt("transTime","2020-04-28 11:30:00"),Filters.lt("transTime","2020-04-28 15:50:00"))
        );

        System.out.println(l);
    }


}
