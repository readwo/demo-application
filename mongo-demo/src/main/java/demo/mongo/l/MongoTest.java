package demo.mongo.l;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoTest {


    @Autowired
    MongoTemplate mongoTemplate;

    public void demo(){

        //mongoTemplate.getDb().getCollection("trip").updateOne(new Document(""))
    }

}
