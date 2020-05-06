package demo.redis;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDemo implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    RedisTemplate redisTemplate;

    private void demo(){
        //redisTemplate.opsForValue().set("hello","world");

        /*redisTemplate.opsForList().rightPush("A123","123456");
        redisTemplate.opsForList().rightPush("A123","123556");
        redisTemplate.opsForList().rightPush("A123","123666");*/



      /*  redisTemplate.opsForHash().put("A1234","A1234","a16");
        redisTemplate.opsForHash().put("A1234","A1244","a13");
        redisTemplate.opsForHash().put("A1234","A1254","a15");*/
       /* redisTemplate.opsForSet().add("A1232","张三");
        redisTemplate.opsForSet().add("A1232","李四");
        redisTemplate.opsForSet().add("A1232","王五");
        redisTemplate.opsForSet().add("A1232","赵六");
        redisTemplate.opsForSet().add("A1232","赵四");
        redisTemplate.opsForSet().add("A1232","李四");*/

        //redisTemplate.opsForList().leftPop("A123").SOUT


        //Object world = redisTemplate.opsForValue().get("world");
        //JSONObject jsonObject = JSONUtil.parseObj(world);
        System.out.println();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        demo();

        applicationReadyEvent.getApplicationContext().close();
    }



}
