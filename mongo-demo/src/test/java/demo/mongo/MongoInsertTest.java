package demo.mongo;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import demo.mongo.vo.Car;

import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;



@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoInsertTest {

    @Autowired
    MongoTemplate mongoTemplate;


    /**
     * 用MongoDBTemplate#insert插入数据,
     * 可插入单条,也可以插入多条
     * <p>
     * 1. 插入单条
     * 1.1 插入实体类，类可以不用@Document注解，集合名就是类名，
     * 可用@Document注解指定特殊集合(value="" 或 collection = "")
     * 也可以在insert中指定集合名称
     * 1.2 插入Document文档时，必须指collection名称，
     * 否则会报错 Couldn't find PersistentEntity for type class org.bson.Document!
     * <p>
     * 2. 插入集合
     *  必须要指定要插入的集合名称或者一个对应的vo类
     *
     */
    @Test
    public void templateInsert() {


        String str = "{\"altitude\":0,\"latitude\":0.0,\"vec1\":0,\"updateTime\":\"2020-06-17 19:55:49\",\"source\":\"JT808\",\"platformId\":\"GEOA\",\"deviceId\":\"015875123812\",\"sendTime\":\"19991130000000\",\"intensity\":17,\"satelliteNum\":0,\"alarm\":0,\"state\":1,\"direction\":0,\"longitude\":0.0}";

        mongoTemplate.insert(new JSONObject(str),"bike");

        //插入实体类
        //mongoTemplate.insert(new Car("QQ", "黑色"));
        //mongoTemplate.insert(new Car("QQ", "黑色"),"bike");

        //插入document
        //mongoTemplate.insert(Document.parse("{\"hello\": \"world\",\"xm\": \"zs\"}"),"bike");


        //插入集合
//        List<Car> list = new ArrayList<>();
//        list.add(new Car("WW","h"));
//        list.add(new Car("ee","h"));
//        list.add(new Car("rrr","h"));
//        mongoTemplate.insert(list,"car");


//        List<Document> docList = new ArrayList<>();
//        docList.add(Document.parse("{\"hello\": \"world\",\"xm\": \"zs\"}"));
//        docList.add(Document.parse("{\"hello1\": \"world1\",\"xm1\": \"zs1\"}"));
//        docList.add(Document.parse("{\"hello2\": \"world2\",\"xm2\": \"zs2\"}"));
//
//        JSONObject jsonObject = new JSONObject("{\"hello\": \"world\",\"xm\": \"zs\"}");
//        JSONObject jsonObject1 = new JSONObject("{\"hello1\": \"world1\",\"xm1\": \"zs1\"}");
//        JSONObject jsonObject2 = new JSONObject("{\"hello1\": \"world2\",\"xm1\": \"zs2\"}");
//        List<JSONObject> list = new ArrayList<>();
//
//        list.add(jsonObject);
//        list.add(jsonObject1);
//        list.add(jsonObject2);
//
//        mongoTemplate.insert(list, "car22");

    }


    /**
     * 用MongoDBTemplate#insertAll 插入集合
     *      集合的泛型必须要指定实体类型, 以实体类名或映射的集合名, 为指定的集合名称,入mongo库中
     *      但document类型做泛型不合适
     *
     */
    @Test
    public void templateInsertAll() {

        List<Car> list = new ArrayList<>();
        list.add(new Car("WW11","h11"));
        list.add(new Car("ee11","h11"));
        list.add(new Car("rrr11","h11"));

       mongoTemplate.insertAll(list);


    }


    /**
     * mongo驱动包 插入
     * 需要以 org.bson.Document 文档类型插入
     */
    @Test
    public void insertDB() {

        JSONObject put = JSONUtil.createObj().put("zs", "zs").put("ls", "ls");
        mongoTemplate.getDb().getCollection("trip").insertOne(Document.parse(put.toString()));

        Document document = new Document();
        document.append("ww", "ww").append("zl", "zl");
        mongoTemplate.getDb().getCollection("trip").insertOne(document);

    }

    /**
     * 插入多条文档
     * 需要以 org.bson.Document 文档类型插入
     */
    @Test
    public void insertAllDB() {

        List<Document> lists = new ArrayList<>();


        Document document = new Document();
        document.append("ww", "ww").append("zl", "zl");

        lists.add(document);
        Document document2 = new Document().append("ww","w2");
        lists.add(document2);
        mongoTemplate.getDb().getCollection("trip").insertMany(lists);

    }


    @Test
    public void bulkWrite(){
        List<WriteModel<Document>> lists = new ArrayList<>();


        Document document = new Document();
        document.append("ww", "ww").append("zl", "zl");

        //lists.add(document);
        Document document2 = new Document().append("ww","w2");
        //lists.add(document2);

        InsertOneModel<Document> insertOneModel = new InsertOneModel<>(document);
        InsertOneModel<Document> insertOneModel2 = new InsertOneModel<>(document2);


        lists.add(insertOneModel);
        lists.add(insertOneModel2);

        mongoTemplate.getDb().getCollection("trip").bulkWrite(lists);
    }




}
