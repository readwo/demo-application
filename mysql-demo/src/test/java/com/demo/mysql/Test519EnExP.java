package com.demo.mysql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test519EnExP {

    @Autowired
    @Qualifier(value = "TwoJdbcTemplate")
    private JdbcTemplate twoJdbcTemplate;

    @Test
    public void insertStation() {
        String sql = "SELECT id,vehicle_name,vehicle_color_num,t1.en_station_id,t1.en_station_name,t1.en_time,t1.ex_station_id,t1.ex_station_name,t1.ex_time,t1.axle_count,t1.media_type " +
            "            FROM group_company_jiangxi_519_station_records t1 WHERE " +
            "            en_time between '2020-05-19 00:00:00' and '2020-05-19 23:59:59' or ex_time between '2020-05-19 00:00:00' and '2020-05-19 23:59:59' ";

        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : maps) {
            String vehicle_name = map.get("vehicle_name").toString();
            //if (vehicle_name.length() == vehicle_name.getBytes().length) continue;

            String en_time = map.get("en_time").toString().substring(0, 19);

            //String en_station_id = map.get("en_station_id").toString();
            //if ((en_time.compareTo("2020-05-19 00:00:00") >= 0) && (en_time.compareTo("2020-05-20 00:00:00") < 0)) {
            String insertSql = "insert into jiangxi_519_LW_all(vehicle_name,vehicle_color,station_name,station_id,`time`,media_type,axle_count) " +
                "VALUES('" + vehicle_name + "'," +
                "'" + map.get("vehicle_color_num") + "'," +
                "'" + map.get("en_station_name") + "'," +
                "'" + map.get("en_station_id") + "'," +
                "'" + en_time + "'," +
                "'" + map.get("media_type") + "'," +
                "'" + map.get("axle_count") + "')";
            twoJdbcTemplate.update(insertSql);
            //}


            String ex_time = map.get("ex_time").toString().substring(0, 19);
            //String ex_station_id = map.get("ex_station_id").toString().substring(0,19);
            //if ((ex_time.compareTo("2020-05-19 00:00:00") >= 0) && (ex_time.compareTo("2020-05-20 00:00:00") < 0)) {
            String insertSql2 = "insert into jiangxi_519_LW_all(vehicle_name,vehicle_color,station_name,station_id,`time`,media_type,axle_count) " +
                "VALUES('" + vehicle_name + "'," +
                "'" + map.get("vehicle_color_num") + "'," +
                "'" + map.get("ex_station_name") + "'," +
                "'" + map.get("ex_station_id") + "'," +
                "'" + ex_time + "'," +
                "'" + map.get("media_type") + "'," +
                "'" + map.get("axle_count") + "')";
            twoJdbcTemplate.update(insertSql2);
            // }


        }
    }


    @Test
    public void insertGantry() {

        /**
         * insert into jiangxi_519_LW_all(vehicle_name,vehicle_color,station_id,time)
         *  SELECT t1.vehicle_name,t1.vehicle_color_num,t1.gantry_id,t1.trans_time
         *  FROM group_company_jiangxi_519_gantry_records t1
         *  where t1.trans_time between '2020-05-19 00:00:00' and '2020-05-19 23:59:59'
         */

    }


    @Test
    public void selectInOut() throws Exception{


        String 入省门户sql = "SELECT gantryId FROM T_GANTRY_MOUNTING where provinceId='36' and in_out_province =1";
        List<String> in_P = twoJdbcTemplate.queryForList(入省门户sql, String.class);

        String 出省门户sql = "SELECT gantryId FROM T_GANTRY_MOUNTING where provinceId='36' and in_out_province =2";
        List<String> out_P = twoJdbcTemplate.queryForList(出省门户sql, String.class);

        String sql = "select  DISTINCT vehicle_name,vehicle_color from jiangxi_519_LW_all t1 where t1.station_id in " +
            " (SELECT  gantry_id FROM `beidou_office_gantry` where province_id = '36' and type ='2' )  ";
        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(sql);

        FileWriter writer1 = new FileWriter(new File("d:/11.txt"));
        FileWriter writer2 = new FileWriter(new File("d:/12.txt"));
        FileWriter writer3 = new FileWriter(new File("d:/13.txt"));

        int i1 = 0 ;
        int i2 = 0 ;
        int i3 = 0 ;



        for (Map<String, Object> map : maps) {
            String vehicle_name = map.get("vehicle_name").toString();
            String vehicle_color = map.get("vehicle_color").toString();

            String sql1 = "SELECT DISTINCT vehicle_name,vehicle_color,station_name,station_id,`time` from jiangxi_519_LW_all " +
                " where vehicle_name = '"+vehicle_name+"' and vehicle_color ='"+vehicle_color+"' ORDER BY time asc";

            List<Map<String, Object>> trips = twoJdbcTemplate.queryForList(sql1);

            String pro_gantry = null;

            boolean f1 = false; //只入
            boolean f2 = false; //只出
            boolean f3 = false; //有入有出

            String station_name_p = null;

            for (Map<String, Object> trip : trips) {

                String station_id = trip.get("station_id").toString();
                if(in_P.contains(station_id) ){
                    String station_name = trip.get("station_name").toString();
                    if(station_name.equals(station_name_p)){
                        continue;
                    }else {
                        station_name_p = station_name;
                    }
                    f1 = true;
                }


                if(out_P.contains(station_id) ){
                    String station_name = trip.get("station_name").toString();
                    if(station_name.equals(station_name_p)){
                        continue;
                    }else {
                        station_name_p = station_name;
                    }
                    f2 = true;
                }

                if(f1 && f2){
                    f3 =true;
                }
                if(f3){break;}

            }

            if(f3){
                i3++;
                System.out.println(vehicle_name+"\t"+vehicle_color +"\t有入有出");
                writer3.write(vehicle_name+"\t"+vehicle_color +"\t有入有出\n");
            }else if(f1){
                i1++;
                System.out.println(vehicle_name+"\t"+vehicle_color +"\t只有入");
                writer1.write(vehicle_name+"\t"+vehicle_color +"\t只有入\n");
            }else if (f2){
                i2++;
                System.out.println(vehicle_name+"\t"+vehicle_color +"\t只有出");
                writer2.write(vehicle_name+"\t"+vehicle_color +"\t只有出\n");
            }




        }

        System.out.println("i1="+ i1);
        System.out.println("i2="+ i2);
        System.out.println("i3="+ i3);

        writer1.flush();
        writer2.flush();
        writer3.flush();
        writer1.close();
        writer2.close();
        writer3.close();

    }



    @Test
    public void enExP_a() throws Exception{

        String sql = "SELECT DISTINCT vehicle_no,vehicle_color FROM " +
            " (" +
            " SELECT vehicle_no,vehicle_color FROM algorithm_jx_trip_incomplete where gantry_id in  (SELECT  gantry_id FROM `beidou_office_gantry` where province_id = '36' and type ='2' )\n" +

            " UNION ALL " +

            " SELECT vehicle_no,vehicle_color FROM algorithm_jx_trip_info where gantry_id in  (SELECT  gantry_id FROM `beidou_office_gantry` where province_id = '36' and type ='2' )\n" +
            " ) t1  ";




        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(sql);

        String 入省门户sql = "SELECT gantryId FROM T_GANTRY_MOUNTING where provinceId='36' and in_out_province =1 ";
        List<String> in_P = twoJdbcTemplate.queryForList(入省门户sql, String.class);

        String 出省门户sql = "SELECT gantryId FROM T_GANTRY_MOUNTING where provinceId='36' and in_out_province =2 ";
        List<String> out_P = twoJdbcTemplate.queryForList(出省门户sql, String.class);


        FileWriter writer1 = new FileWriter(new File("d:/11-a.txt"));
        FileWriter writer2 = new FileWriter(new File("d:/12-a.txt"));
        FileWriter writer3 = new FileWriter(new File("d:/13-a.txt"));

        int i1 = 0 ;
        int i2 = 0 ;
        int i3 = 0 ;

        for (Map<String, Object> map : maps) {

            String vehicle_no = map.get("vehicle_no").toString();

            String vehicle_color = map.get("vehicle_color").toString();

            String s ="SELECT gantry_id,point_name,tans_time FROM " +
                "( " +
                " SELECT gantry_id,point_name,tans_time FROM algorithm_jx_trip_incomplete " +
                " where  vehicle_no ='"+vehicle_no+"' and vehicle_color='"+vehicle_color+"' " +
                " UNION ALL " +
                " SELECT gantry_id,point_name,tans_time FROM algorithm_jx_trip_info where " +
                "   vehicle_no ='"+vehicle_no+"' and vehicle_color='"+vehicle_color+"' "+
                " ) t1    ORDER BY t1.tans_time asc";

            List<Map<String, Object>> maps1 = twoJdbcTemplate.queryForList(s);

            String station_name_p = null;
            boolean f1 = false; //只入
            boolean f2 = false; //只出
            boolean f3 = false; //有入有出

            for (Map<String, Object> m1 : maps1) {

                String station_id = m1.get("gantry_id").toString();
                if(in_P.contains(station_id) ){
                    String station_name = m1.get("point_name").toString();
                    if(station_name.equals(station_name_p)){
                        continue;
                    }else {
                        station_name_p = station_name;
                    }
                    f1 = true;
                }


                if(out_P.contains(station_id) ){
                    String station_name = m1.get("point_name").toString();
                    if(station_name.equals(station_name_p)){
                        continue;
                    }else {
                        station_name_p = station_name;
                    }
                    f2 = true;
                }

                if(f1 && f2){
                    f3 =true;
                }
                if(f3){break;}

            }

            if(f3){
                i3++;
                System.out.println(vehicle_no+"\t"+vehicle_color +"\t有入有出");
                writer3.write(vehicle_no+"\t"+vehicle_color +"\t有入有出\n");
            }else if(f1){
                i1++;
                System.out.println(vehicle_no+"\t"+vehicle_color +"\t只有入");
                writer1.write(vehicle_no+"\t"+vehicle_color +"\t只有入\n");
            }else if (f2){
                i2++;
                System.out.println(vehicle_no+"\t"+vehicle_color +"\t只有出");
                writer2.write(vehicle_no+"\t"+vehicle_color +"\t只有出\n");
            }




        }


        System.out.println("i1="+ i1);
        System.out.println("i2="+ i2);
        System.out.println("i3="+ i3);

        writer1.flush();
        writer2.flush();
        writer3.flush();
        writer1.close();
        writer2.close();
        writer3.close();


    }
}
