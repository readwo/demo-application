package com.demo.mysql;

import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test519CPC {

    @Autowired
    @Qualifier(value = "TwoJdbcTemplate")
    private JdbcTemplate twoJdbcTemplate;

    @Test
    public void cpcVehicle() throws Exception {
        String sql = "SELECT id,vehicle_name,vehicle_color_num,t1.en_station_id,t1.en_station_name,t1.en_time,t1.ex_station_id,t1.ex_station_name,t1.ex_time,t1.axle_count,t1.media_type" +
            " FROM group_company_jiangxi_519_station_records t1 " +
            " WHERE  vehicle_type like '货%' and axle_count >=3 and media_type = 'CPC卡' " +
            " and (en_time between '2020-05-19 00:00:00' and '2020-05-20 00:00:00' or ex_time between '2020-05-19 00:00:00' and '2020-05-20 00:00:00') ;";

        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : maps) {
            String vehicle_name = map.get("vehicle_name").toString();
            //if (vehicle_name.length() == vehicle_name.getBytes().length) continue;

            String en_time = map.get("en_time").toString().substring(0, 19);

            //String en_station_id = map.get("en_station_id").toString();
            //if ((en_time.compareTo("2020-05-19 00:00:00") >= 0) && (en_time.compareTo("2020-05-20 00:00:00") < 0)) {
            String insertSql = "insert into jiangxi_519_CPC_3(vehicle_name,vehicle_color,station_name,station_id,`time`,media_type,axle_count,enExF) " +
                "VALUES('" + vehicle_name + "'," +
                "'" + map.get("vehicle_color_num") + "'," +
                "'" + map.get("en_station_name") + "'," +
                "'" + map.get("en_station_id") + "'," +
                "'" + en_time + "'," +
                "'" + map.get("media_type") + "'," +
                "'" + map.get("axle_count") + "'," +
                "1)";
            twoJdbcTemplate.update(insertSql);
            //}


            String ex_time = map.get("ex_time").toString().substring(0, 19);
            //String ex_station_id = map.get("ex_station_id").toString().substring(0,19);
            //if ((ex_time.compareTo("2020-05-19 00:00:00") >= 0) && (ex_time.compareTo("2020-05-20 00:00:00") < 0)) {
            String insertSql2 = "insert into jiangxi_519_CPC_3(vehicle_name,vehicle_color,station_name,station_id,`time`,media_type,axle_count,enExF) " +
                "VALUES('" + vehicle_name + "'," +
                "'" + map.get("vehicle_color_num") + "'," +
                "'" + map.get("ex_station_name") + "'," +
                "'" + map.get("ex_station_id") + "'," +
                "'" + ex_time + "'," +
                "'" + map.get("media_type") + "'," +
                "'" + map.get("axle_count") + "'," +
                "2)";
            twoJdbcTemplate.update(insertSql2);
            // }


        }
    }


    @Test
    public void insertUpDate() {



        String bb = "SELECT t1.gantry_id,t1.trans_time,t1.vehicle_name,t1.vehicle_color_num  " +
            " FROM group_company_jiangxi_519_gantry_records t1 " +
            " join jiangxi_519_CPC_3_set t2 on t1.vehicle_name = t2.vehicle_name and t1.vehicle_color_num = t2.vehicle_color " +
            " where trans_time BETWEEN '2020-05-19 00:00:00' AND '2020-05-19 23:59:59' ";
        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(bb);

        for (Map<String, Object> map : maps) {
            String trans_time = map.get("trans_time").toString().substring(0, 19);

            String insertSql = " insert into jiangxi_519_CPC_3(vehicle_name,vehicle_color,station_id,`time`) VALUES(" +
                "'" + map.get("vehicle_name") + "', " +
                "'" + map.get("vehicle_color_num") + "'," +
                "'" + map.get("gantry_id") + "'," +
                "'" + trans_time + "')";

            //System.out.println(insertSql);
            twoJdbcTemplate.update(insertSql);

        }

    }

    @Test
    public void writeFile() throws Exception {

        String disSql = "SELECT DISTINCT t1.vehicle_name,t1.vehicle_color FROM `jiangxi_519_CPC_3_set` t1 JOIN `beidou_vehicle_color_all_set` t2 ON t1.vehicle_name = t2.vehicle_name AND t1.vehicle_color = t2.vehicle_color";

        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(disSql);

        DecimalFormat decimal = new DecimalFormat("#0.00%");

        FileWriter writer = new FileWriter(new File("d:/519-CPC.txt"));

        for (Map<String, Object> map : maps) {
            String vehicle_name = map.get("vehicle_name").toString();
            String vehicle_color = map.get("vehicle_color").toString();
            writer.write(vehicle_name + "\t" + vehicle_color + "\t");
            writer.write("http://39.105.174.44:8000/group_company_jiangxi_519/vehicle_hodemeter_details/" + vehicle_name + "/" + vehicle_color + "\t");
            String lwSql = "SELECT station_id from `jiangxi_519_CPC_3`" +
                "where vehicle_name='" + vehicle_name + "' and vehicle_color = '" + vehicle_color + "' ORDER BY time asc";

            List<String> lw = twoJdbcTemplate.queryForList(lwSql, String.class);

            String lw_join = StringUtils.join(lw);


            String hysql = "select gantry_id from ( " +
                "SELECT gantry_id,gpstime FROM algorithm_jx_trip_incomplete  WHERE vehicle_no='" + vehicle_name + "' AND vehicle_color='" + vehicle_color + "' " +
                "UNION all " +
                "SELECT gantry_id,gpstime FROM algorithm_jx_trip_info WHERE vehicle_no='" + vehicle_name + "' AND vehicle_color='" + vehicle_color + "' " +
                ") t3 ORDER BY gpstime ";

            List<String> hy = twoJdbcTemplate.queryForList(hysql, String.class);

            String hy_join = StringUtils.join(hy);

            if (lw_join.equals(hy_join)) {
                writer.write("一致\t");
            } else {
                writer.write("不一致\t");
            }

            int size = lw.size();
            //江西联网资料
            writer.write(size + "\t");

            int gantry_lw = 0;
            int station_lw = 0;
            for (String s : lw) {
                if (!hy.contains(s)) {
                    if (s.length() == 14) {
                        station_lw++;
                    } else {
                        gantry_lw++;
                    }
                }
            }
            writer.write(gantry_lw + "\t" + station_lw + "\t");

            writer.write(decimal.format((gantry_lw * 1.0 / size)) + "\t");
            writer.write(decimal.format((station_lw * 1.0 / size)) + "\t");

            int hy_size = hy.size();
            writer.write(hy_size + "\t");
            int gantry_hy = 0;
            int station_hy = 0;
            for (String s : hy) {
                if (!lw.contains(s)) {
                    if (s.length() == 14) {
                        station_hy++;
                    } else {
                        gantry_hy++;
                    }
                }
            }
            writer.write(gantry_hy + "\t" + station_hy + "\t");
            if (hy_size == 0) {
                writer.write(decimal.format(0d) + "\t");
                writer.write(decimal.format(0d) + "\t");
            } else {
                writer.write(decimal.format((gantry_hy * 1.0 / hy_size)) + "\t");
                writer.write(decimal.format((station_hy * 1.0 / hy_size)) + "\t");
            }


            writer.write("\n");
            writer.flush();

        }


        writer.close();

    }

}
