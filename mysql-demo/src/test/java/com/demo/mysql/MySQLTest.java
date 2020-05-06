package com.demo.mysql;

import com.demo.mysql.vo.cha_electronic_fence;
import com.demo.mysql.vo.global_topology;
import com.demo.mysql.vo.roadcenter_gantry;
import com.demo.mysql.vo.roadcenter_station_plaza;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySQLTest {

    @Autowired
    @Qualifier("OneJdbcTemplate")
    JdbcTemplate jdbcTemplate;


    @Autowired
    @Qualifier("TwoJdbcTemplate")
    JdbcTemplate twoJdbcTemplate;


    @Test
    public void plaza() {
        String sql = "SELECT * FROM roadcenter_station_plaza where province_id = '36' and is_delete = 0";
        List<roadcenter_station_plaza> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(roadcenter_station_plaza.class));

        for (roadcenter_station_plaza roadcenter_gantry : list) {
            int prov = Integer.parseInt(roadcenter_gantry.getProvinceId());
            String plazaId = roadcenter_gantry.getPlazaId();
            String plazaName = roadcenter_gantry.getPlazaName();

            String plazaType = roadcenter_gantry.getPlazaType();
            int type = 1;
            if ("匝道收费广场".equals(plazaType)) {
                type = 2;
            }
            double verifyWgsLat = roadcenter_gantry.getVerifyWgsLat();
            double verifyWgsLng = roadcenter_gantry.getVerifyWgsLng();
            String stationId = roadcenter_gantry.getStationId();


            String insertSql = "INSERT INTO `etc`.`t_point_toll_plaza`(`provinceId`, `tollPlazaId`, `tollPlazaName`, `" +
                    "tollPlazaType`, `lat`, `lng`, `stationId`) VALUES (" +
                    prov + ", '" +
                    plazaId + "', '" +
                    plazaName + "', " +
                    type + "," +
                    verifyWgsLat + "," +
                    verifyWgsLng + ", '" +
                    stationId + "');";

            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }

    }


    @Test
    public void gantry() {
        String sql = "SELECT * FROM roadcenter_gantry where province_id = '36' and is_delete = 0";
        List<roadcenter_gantry> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(roadcenter_gantry.class));

        for (roadcenter_gantry roadcenter_gantry : list) {
            int prov = Integer.parseInt(roadcenter_gantry.getProvinceId());
            String gantryId = roadcenter_gantry.getGantryId();
            String name = roadcenter_gantry.getName();
            String gantry = roadcenter_gantry.getType();
            int gantryType = 2;
            if ("路段收费门架".equals(gantry)) {
                gantryType = 1;
            }

            double verifyWgsLat = roadcenter_gantry.getVerifyWgsLat();

            double verifyWgsLng = roadcenter_gantry.getVerifyWgsLng();

            String pileNumber = roadcenter_gantry.getPileNumber();

            String information2 = roadcenter_gantry.getInformation2();

            int isGatewayGantry = 0;
            if ("匝道门架".equals(information2)) {
                isGatewayGantry = 1;
            }


            int substring = Integer.parseInt(gantryId.substring(gantryId.length() - 5, gantryId.length() - 4));


            String insertSql = "INSERT INTO `etc`.`t_point_gantry`(`provinceId`, `gantryId`, `gantryName`, `gantryType`," +
                    " `lat`, `lng`, `pileNumber`, `isGatewayGantry`, `way`) VALUES (" +
                    prov + ", '" +
                    gantryId + "', '" +
                    name + "'," +
                    gantryType + " ," +
                    verifyWgsLat + " , " +
                    verifyWgsLng + " ,'" +
                    pileNumber + "'," +
                    isGatewayGantry + " , " +
                    substring + ");";


            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }


    @Test
    public void topology() {
        String sql = "SELECT * FROM global_topology where province_id = '36'";
        List<global_topology> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(global_topology.class));

        twoJdbcTemplate.execute("delete from `etc`.`t_area_topology`");
        for (global_topology roadcenter_gantry : list) {


            String insertSql = "INSERT INTO `etc`.`t_area_topology`(`startId`, `endId`) VALUES ('" +
                    roadcenter_gantry.getStart() + "', '" +
                    roadcenter_gantry.getEnd() + "');";


            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }


    /**
     * 电子围栏
     */
    @Test
    public void fence() {
        String sql = "SELECT * FROM cha_electronic_fence where province_id = '360000'";
        List<cha_electronic_fence> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(cha_electronic_fence.class));

        for (cha_electronic_fence roadcenter_gantry : list) {
            int prov = Integer.parseInt(roadcenter_gantry.getProvinceId()) / 10000;
            String geometryData = roadcenter_gantry.getGeometryData();
            Integer pointType = roadcenter_gantry.getPointType();
            int type = 2;
            if (3 == pointType) {
                type = 1;
            }


            String insertSql = "INSERT INTO `etc`.`t_area_polygon`( `provinceId`, `points`, `typeId`, `resourceId`) VALUES (" +
                    prov + ", '" +
                    geometryData + "', " +
                    type + ", '" +
                    roadcenter_gantry.getRoadResourceId() + "');";

            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }





}
