package com.demo.mysql;

import com.demo.mysql.vo.S_ELECTRONIC_FENCE;
import com.demo.mysql.vo.T_GANTRY_MOUNTING;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElectronicFenceTest {

    @Autowired
    @Qualifier("OneJdbcTemplate")
    JdbcTemplate jdbcTemplate;


    @Autowired
    @Qualifier("TwoJdbcTemplate")
    JdbcTemplate twoJdbcTemplate;

    @Test
    public void fence() {

        String sql = "SELECT t1.electronic_fence_id,t1.fence_name,t1.road_resource_id FROM S_ELECTRONIC_FENCE t1 " +
            "where t1.province_id = 36 ";

        List<S_ELECTRONIC_FENCE> query = twoJdbcTemplate.query(sql, new BeanPropertyRowMapper(S_ELECTRONIC_FENCE.class));

        Map<String,Integer>  map = new HashMap<>();

        for (S_ELECTRONIC_FENCE fence : query) {
            String roadResourceId = fence.getRoadResourceId();
            String[] split = roadResourceId.split("\\|");
            for (String s : split) {
                map.put(s,fence.getElectronicFenceId());
            }
        }
        String sql2 = "SELECT  t1.gantryId,t1.gantryType,t1.isGatewayGantry from T_GANTRY_MOUNTING t1  where provinceId = 36";
        List<T_GANTRY_MOUNTING> query1 = twoJdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(T_GANTRY_MOUNTING.class));

        for (T_GANTRY_MOUNTING gantry : query1) {
            String gantryId = gantry.getGantryId();
            Integer integer = map.get(gantryId);
            if(integer == null) continue;
            String updateSQL = "update S_ELECTRONIC_FENCE set recognize_type = "+gantry.getGantryType()+",isGatewayGantry="+gantry.getIsGatewayGantry()+" " +
                " where electronic_fence_id = "+integer ;
            System.out.println(updateSQL);
            twoJdbcTemplate.execute(updateSQL);
        }


    }
}
