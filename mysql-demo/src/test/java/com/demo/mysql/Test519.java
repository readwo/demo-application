package com.demo.mysql;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.tomcat.util.buf.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 对519的数据进行处理
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class Test519 {

    @Autowired
    @Qualifier(value = "TwoJdbcTemplate")
    private JdbcTemplate twoJdbcTemplate;

    //
    @Test
    public void obuVehicle() throws Exception {
        String sql = "SELECT id,vehicle_name,vehicle_color_num,t1.en_station_id,t1.en_station_name,t1.en_time,t1.ex_station_id,t1.ex_station_name,t1.ex_time,t1.axle_count,t1.media_type  " +
            "FROM group_company_jiangxi_519_station_records t1 WHERE  vehicle_type like '货%' and axle_count >=3 and media_type = 'OBU' " +
            " and (en_time between '2020-05-19 00:00:00' and '2020-05-19 23:59:59' or ex_time between '2020-05-19 00:00:00' and '2020-05-19 23:59:59') ";

        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(sql);

        for (Map<String, Object> map : maps) {
            String vehicle_name = map.get("vehicle_name").toString();
            //if (vehicle_name.length() == vehicle_name.getBytes().length) continue;

            String en_time = map.get("en_time").toString().substring(0, 19);

            //String en_station_id = map.get("en_station_id").toString();
            //if ((en_time.compareTo("2020-05-19 00:00:00") >= 0) && (en_time.compareTo("2020-05-20 00:00:00") < 0)) {
            String insertSql = "insert into jiangxi_519_LW(vehicle_name,vehicle_color,station_name,station_id,`time`,media_type,axle_count,enExF) " +
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
            String insertSql2 = "insert into jiangxi_519_LW(vehicle_name,vehicle_color,station_name,station_id,`time`,media_type,axle_count,enExF) " +
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

        String aa = "SELECT t1.gantry_id,t1.trans_time,t1.vehicle_name,t1.vehicle_color_num  FROM group_company_jiangxi_519_gantry_records t1 " +
            " where t1.vehicle_name in ( SELECT distinct vehicle_name  FROM `jiangxi_519_LW` )   ";

        String bb = "SELECT t1.gantry_id,t1.trans_time,t1.vehicle_name,t1.vehicle_color_num  " +
            " FROM group_company_jiangxi_519_gantry_records t1 " +
            " join jiangxi_519_LW_set t2 on t1.vehicle_name = t2.vehicle_name and t1.vehicle_color_num = t2.vehicle_color " +
            " where trans_time BETWEEN '2020-05-19 00:00:00' AND '2020-05-19 23:59:59' ";
        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(bb);

        for (Map<String, Object> map : maps) {
            String trans_time = map.get("trans_time").toString().substring(0, 19);

            String insertSql = " insert into jiangxi_519_LW(vehicle_name,vehicle_color,station_id,`time`) VALUES(" +
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

        String disSql = "SELECT DISTINCT t1.vehicle_name,t1.vehicle_color FROM `jiangxi_519_LW` t1 JOIN `beidou_vehicle_color_all_set` t2 ON t1.vehicle_name = t2.vehicle_name AND t1.vehicle_color = t2.vehicle_color";

        List<Map<String, Object>> maps = twoJdbcTemplate.queryForList(disSql);

        DecimalFormat decimal = new DecimalFormat("#0.00%");

        FileWriter writer = new FileWriter(new File("d:/519.txt"));

        for (Map<String, Object> map : maps) {
            String vehicle_name = map.get("vehicle_name").toString();
            String vehicle_color = map.get("vehicle_color").toString();
            writer.write(vehicle_name + "\t" + vehicle_color + "\t");
            writer.write("http://39.105.174.44:8000/group_company_jiangxi_519/vehicle_hodemeter_details/" + vehicle_name + "/" + vehicle_color + "\t");
            String lwSql = "SELECT station_id from `jiangxi_519_LW`" +
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

    @Test
    public void test1() {
        //XSSFWorkbook xssfWorkbook = new XSSFWorkbook(XSSFWorkbookType.XLSX);

        //ExcelUtil

        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
//通过构造方法创建writer
//ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");
        writer.passCurrentRow();
        writer.merge(row1.size() - 1, "测试标题");
//一次性写出内容，强制输出标题
        writer.write(rows, true);
//关闭writer，释放内存
        writer.close();
    }


    @Test
    public void test2() throws  Exception{

        //FileWriter fileWriter = new FileWriter(new File("d:/13.xlsx"));
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream("d:/13.xlsx"));

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(XSSFWorkbookType.XLSX);
        XSSFSheet sheet = xssfWorkbook.createSheet("比较信息");

        XSSFRow row0 = sheet.createRow(0);
        XSSFCell row0Cell4 = row0.createCell(4);
        row0Cell4.setCellStyle(createTitleCellStyle(xssfWorkbook));
        row0Cell4.setCellValue("江西资料");
        // 合并单元格，参数依次为起始行，结束行，起始列，结束列 （索引0开始）
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 8));
        XSSFCell cell9 = row0.createCell(9);
        cell9.setCellStyle(createTitleCellStyle(xssfWorkbook));
        cell9.setCellValue("算法资料");
        // 合并单元格，参数依次为起始行，结束行，起始列，结束列 （索引0开始）
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 13));


        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("车牌");
        row1.createCell(1).setCellValue("车牌颜色");
        row1.createCell(2).setCellValue("详情连接");
        row1.createCell(3).setCellValue("对比结果");
        row1.createCell(4).setCellValue("途径数量");
        row1.createCell(5).setCellValue("门架不一致数量");
        row1.createCell(6).setCellValue("收费站不一致数量");
        row1.createCell(7).setCellValue("门架不一致率");
        row1.createCell(8).setCellValue("收费站不一致率");
        row1.createCell(9).setCellValue("途径数量");
        row1.createCell(10).setCellValue("门架不一致数量");
        row1.createCell(11).setCellValue("收费站不一致数量");
        row1.createCell(12).setCellValue("门架不一致率");
        row1.createCell(13).setCellValue("收费站不一致率");




        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();
        outputStream.close();
    }

    private  XSSFCellStyle createTitleCellStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直对齐
        //cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);



        return cellStyle;
    }


    @Test
    public  void tes1() throws Exception{
        List<String> ru = Arrays.asList("");
        List<String> chu = Arrays.asList("");
        List<String> cr = Arrays.asList("");


        BufferedReader reader = new BufferedReader(new FileReader(""));
    }
}
