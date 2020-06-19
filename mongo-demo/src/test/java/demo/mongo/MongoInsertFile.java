package demo.mongo;

import cn.hutool.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoInsertFile {

    @Autowired
    MongoTemplate mongoTemplate;


    @Test
    public void test1() throws Exception{

        BufferedReader reader = new BufferedReader(new FileReader("d:/geoa808Queue.txt"));
        List<JSONObject> list = new ArrayList<>();
        String str;
        while ((str=reader.readLine())!=null){
            JSONObject jsonObject = new JSONObject(str);

            list.add(jsonObject);
        }

        mongoTemplate.insert(list, "trace");


    }
}
