package com.demo.mysql;

import com.demo.mysql.util.GpsCoordinateUtils;
import com.demo.mysql.vo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 转换电子围栏  v2
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MySQLTest2 {

    @Autowired
    @Qualifier("OneJdbcTemplate")
    JdbcTemplate jdbcTemplate;


    @Autowired
    @Qualifier("TwoJdbcTemplate")
    JdbcTemplate twoJdbcTemplate;


    @Test
    public void plaza() {

        String sql = "SELECT * FROM T_TOLL_PLAZA where  isAbnormal = 0  ";
        List<T_TOLL_PLAZA> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(T_TOLL_PLAZA.class));

        twoJdbcTemplate.execute("delete from `t_point_toll_plaza`");
        for (T_TOLL_PLAZA fence : list) {
            int provinceId = fence.getProvinceId();

            String plazaId = fence.getTollPlazaId();
            String plazaName = fence.getTollPlazaName();

            int plazaType = fence.getTollPlazaType();


            double[] doubles = GpsCoordinateUtils.calGCJ02toWGS84(Double.parseDouble(fence.getGnclat()),Double.parseDouble(fence.getGnclng()) );
            String lat = new BigDecimal(doubles[0]).setScale(6, RoundingMode.DOWN).toString();
            String lng  = new BigDecimal(doubles[1]).setScale(6, RoundingMode.DOWN).toString();


            String stationId = fence.getStationId();


            String insertSql = "INSERT INTO `t_point_toll_plaza`(`provinceId`, `tollPlazaId`, `tollPlazaName`, `" +
                    "tollPlazaType`, `lat`, `lng`, `stationId`) VALUES (" +
                provinceId + ", '" +
                    plazaId + "', '" +
                    plazaName + "', " +
                plazaType + "," +
                lat+ "," +
                lng + ", '" +
                stationId + "');";

            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }

    }


    @Test
    public void gantry() {
        String sql = "SELECT * FROM T_GANTRY_MOUNTING where isAbnormal  = 0 ";

        List<T_GANTRY_MOUNTING> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(T_GANTRY_MOUNTING.class));

        twoJdbcTemplate.execute("delete from `t_point_gantry`");
        for (T_GANTRY_MOUNTING gantry : list) {
            int prov = gantry.getProvinceId();
            String gantryId = gantry.getGantryId();
            String name = gantry.getGantryName();
            int gantryType = gantry.getGantryType();

            double[] doubles = GpsCoordinateUtils.calGCJ02toWGS84(Double.parseDouble(gantry.getGnclat()),Double.parseDouble(gantry.getGnclng()) );
            String lat = new BigDecimal(doubles[0]).setScale(6, RoundingMode.DOWN).toString();
            String lng  = new BigDecimal(doubles[1]).setScale(6, RoundingMode.DOWN).toString();


            String pileNumber = gantry.getPileNumber();

            int isGatewayGantry = gantry.getIsGatewayGantry();




            int substring = Integer.parseInt(gantryId.substring(gantryId.length() - 5, gantryId.length() - 4));


            String insertSql = "INSERT INTO `t_point_gantry`(`provinceId`, `gantryId`, `gantryName`, `gantryType`," +
                    " `lat`, `lng`, `pileNumber`, `isGatewayGantry`, `way`) VALUES (" +
                    prov + ", '" +
                    gantryId + "', '" +
                    name + "'," +
                    gantryType + " ," +
                lat + " , " +
                lng + " ,'" +
                    pileNumber + "'," +
                    isGatewayGantry + " , " +
                    substring + ");";


            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }


    @Test
    public void topology() {
        String sql = "SELECT * FROM S_ROUTING where start_id <> '' and  end_id <> ''";
        List<S_ROUTING> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(S_ROUTING.class));

        twoJdbcTemplate.execute("delete from `t_area_topology`");
        for (S_ROUTING routing : list) {

            String insertSql = "INSERT INTO `t_area_topology`(`startId`, `endId`) VALUES ('" +
                routing.getStartId() + "', '" +
                routing.getEndId() + "');";


            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }


    /**
     * 电子围栏
     */
    @Test
    public void fence() {
        String sql = "SELECT * FROM S_ELECTRONIC_FENCE where  recognize_type <> '' and province_id <> ''";
        List<S_ELECTRONIC_FENCE> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(S_ELECTRONIC_FENCE.class));

        twoJdbcTemplate.execute("delete from `t_area_polygon`");
        System.out.println("================="+list.size());
        for (S_ELECTRONIC_FENCE fence : list) {
            int prov = fence.getProvinceId();
            String geometryData = fence.getElectronicFenceData();
            Integer pointType = fence.getRecognizeType();
            int type = 2;
            if (3 == pointType) {
                type = 1;
            }


            String insertSql = "INSERT INTO `t_area_polygon`( `provinceId`, `points`, `typeId`, `resourceId`) VALUES (" +
                    prov + ", '" +
                    geometryData + "', " +
                    type + ", '" +
                fence.getRoadResourceId() + "');";

           // System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }


    @Test
    public void station(){
        String sql = "SELECT * FROM T_STATION where isAbnormal = 0 ";
        List<T_STATION> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(T_STATION.class));

        twoJdbcTemplate.execute("delete from `t_station`");
        for (T_STATION station : list) {
            int prov = station.getProvinceId();
            String stationId = station.getStationId();
            String stationName = station.getStationName();
            int type = 2;



            String insertSql = "INSERT INTO `t_station`( `provinceId`, `stationId`, `dataType`, `stationName`) VALUES (" +
                prov + ", '" +
                stationId + "', " +
                type + ", '" +
                stationName + "');";

            System.out.println(insertSql);
            twoJdbcTemplate.execute(insertSql);
        }
    }


}
