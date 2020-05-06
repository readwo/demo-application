package com.demo.mysql.vo;

import com.demo.mysql.util.GpsCoordinateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * DROP TABLE IF EXISTS `T_TOLL_PLAZA`;
 * CREATE TABLE `T_TOLL_PLAZA`  (
 *   `id` int(20) NOT NULL AUTO_INCREMENT,
 *   `provinceId` smallint(6) NULL DEFAULT NULL COMMENT '省市编号',
 *   `tollPlazaId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作收费广场编号',
 *   `tollPlazaName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费广场名称',
 *   `tollPlazaType` tinyint(4) NULL DEFAULT NULL COMMENT '广场类型，主线收费广场1，匝道收费广场2',
 *   `lat` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WGS纬度小',
 *   `lng` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'WGS经度大',
 *   `gnclat` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高德纬度',
 *   `gnclng` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高德经度',
 *   `anglein` smallint(6) NULL DEFAULT NULL COMMENT '入站角度',
 *   `angleout` smallint(6) NULL DEFAULT NULL COMMENT '出战角度',
 *   `operation` tinyint(4) NULL DEFAULT NULL COMMENT '操作',
 *   `stationId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属收费站编号',
 *   `reviseStatus` smallint(6) NULL DEFAULT 0 COMMENT '0 初始状态，等待校正\r\n1 校正结束，等待复核\r\n2 复核结束，数据完成',
 *   `firstAgent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
 *   `secondAgent` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
 *   `orderNumber` int(11) NOT NULL DEFAULT 0 COMMENT '序号',
 *   `isAbnormal` smallint(6) NULL DEFAULT 0 COMMENT '异常状态：0正常，1异常',
 *   `note` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
 *   `tollNumber` int(11) NOT NULL COMMENT '无异常编码',
 *   PRIMARY KEY (`id`) USING BTREE,
 *   INDEX `tollPlazaId`(`tollPlazaId`) USING BTREE,
 *   INDEX `lat`(`gnclat`) USING BTREE,
 *   INDEX `lng`(`gnclng`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 19697 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收费广场' ROW_FORMAT = Dynamic;
 */
@Data
public class T_TOLL_PLAZA {

    private int id;

    private int provinceId;


    private String tollPlazaId;

    private String tollPlazaName;

    private int tollPlazaType;


    private String lat;


    private String lng;

    private String gnclat;

    private String gnclng;





    private String stationId;

    private int isAbnormal;

    public static void main(String[] args) {
        String lat = "25.285726";
        String lng = "113.021616";
        double[] doubles = GpsCoordinateUtils.calGCJ02toWGS84(Double.parseDouble(lat),Double.parseDouble(lng) );
        System.out.println(new BigDecimal(doubles[0]).setScale(6, RoundingMode.DOWN)+","+doubles[1]);
    }


}
