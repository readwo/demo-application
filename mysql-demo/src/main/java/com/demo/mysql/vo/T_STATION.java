package com.demo.mysql.vo;

import lombok.Data;

/**
 * DROP TABLE IF EXISTS `T_STATION`;
 * CREATE TABLE `T_STATION`  (
 *   `id` int(20) NOT NULL AUTO_INCREMENT,
 *   `provinceId` smallint(6) NULL DEFAULT NULL COMMENT '省市编号',
 *   `stationId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收费站编号（id）=收费路段编号+收费站顺序码+保留位',
 *   `dataType` tinyint(4) NULL DEFAULT NULL COMMENT '数据类型，2:收费站',
 *   `stationName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费站名称，不超过 50 个字符',
 *   `stationHex` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费站HEX字符串，收费站 HEX 字符串（长度 8）=收费路网号+收费站号',
 *   `lat` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度，WGS84 坐标系统单位：度,保留 6 位小数',
 *   `lng` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度，WGS84 坐标系统单位：度,保留 6 位小数',
 *   `gnclat` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高德纬度',
 *   `gnclng` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高德经度',
 *   `anglein` smallint(6) NULL DEFAULT NULL COMMENT '方向入口',
 *   `angleout` smallint(6) NULL DEFAULT NULL COMMENT '方向出口',
 *   `stakeNum` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '桩号',
 *   `operation` tinyint(4) NULL DEFAULT NULL COMMENT '操作，1-新增，2-变更，3-删除',
 *   `isAbnormal` tinyint(4) NULL DEFAULT 0 COMMENT '异常状态：0正常，1异常',
 *   `isGatewayGantry` tinyint(4) NULL DEFAULT 0 COMMENT '是否为闸道门架：0是，1否',
 *   `firstAgent` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首次处理人员',
 *   `secondAgent` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二次校验人员',
 *   `note` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
 *   PRIMARY KEY (`id`) USING BTREE,
 *   INDEX `stationId`(`stationId`) USING BTREE,
 *   INDEX `stationId_2`(`stationId`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 10553 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收费站' ROW_FORMAT = Dynamic;
 */
@Data
public class T_STATION {

    private int id;

    private int provinceId;
    private String stationId;

    private String stationName;


    private int isAbnormal;



}
