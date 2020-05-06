package com.demo.mysql.vo;

import lombok.Data;

/**
 * DROP TABLE IF EXISTS `T_GANTRY_MOUNTING`;
 * CREATE TABLE `T_GANTRY_MOUNTING`  (
 *   `id` int(11) NOT NULL AUTO_INCREMENT,
 *   `provinceId` smallint(6) NULL DEFAULT NULL COMMENT '省市编号',
 *   `gantryId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '门架编号，收费门架编号（id）=收费单元编号+顺序码（1位）+保留位（1位）',
 *   `dataType` tinyint(4) NULL DEFAULT NULL COMMENT '数据类型，3: ETC门架',
 *   `gantryName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门架名称',
 *   `gantryType` tinyint(4) NULL DEFAULT NULL COMMENT '门架类型，1-路段收费门架，2-省界收费',
 *   `tollIntervals` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费单元编码组合，收费门架若为其他收费单元代收，则在此填写门架所在的收费单元和代收的收费单元，并用“|”间隔',
 *   `lat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度，WGS84 坐标系统单位：度,保留 6 位小数',
 *   `lng` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度，WGS84 坐标系统单位：度,保留 6 位小数',
 *   `gnclat` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高德纬度',
 *   `gnclng` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '高德经度',
 *   `angle` smallint(6) NULL DEFAULT NULL COMMENT '方向',
 *   `roadId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公路编号',
 *   `roadName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公路名称',
 *   `roadSectionId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路段ID',
 *   `roadSectionName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路段名称',
 *   `roadNodeId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路网结点ID',
 *   `roadNodeName` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路网结点名称',
 *   `pileNumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '桩号',
 *   `status` tinyint(4) NULL DEFAULT NULL COMMENT '使用状态，0-停用，1-在用',
 *   `startTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '起始日期，YYYY-MM-DD',
 *   `endTime` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '终止日期，YYYY-MM-DD',
 *   `etcGantryHex` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门架HEX字符串',
 *   `operation` tinyint(4) NULL DEFAULT NULL COMMENT '操作，1-新增，2-变更，3-删除',
 *   `isAbnormal` tinyint(4) NULL DEFAULT 0 COMMENT '异常状态：0正常，1异常',
 *   `isGatewayGantry` tinyint(4) NULL DEFAULT 0 COMMENT '是否为闸道门架：0否，1是',
 *   `firstAgent` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '首次处理人员',
 *   `secondAgent` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '二次校验人员',
 *   `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
 *   `reviseStatus` smallint(6) NULL DEFAULT 0 COMMENT '0 初始状态，等待校正\r\n1 校正结束，等待复核\r\n2 复核结束，数据完成',
 *   `orderNumber` int(11) NOT NULL DEFAULT 0 COMMENT '序号',
 *   `fenceNumber` int(11) NOT NULL COMMENT '一对门架序号',
 *   `fenceOrderNumber` int(11) NOT NULL COMMENT '展示电子围栏序号',
 *   PRIMARY KEY (`id`) USING BTREE,
 *   INDEX `gantryId`(`gantryId`) USING BTREE,
 *   INDEX `provinceId`(`provinceId`) USING BTREE,
 *   INDEX `lat`(`gnclat`) USING BTREE,
 *   INDEX `lng`(`gnclng`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 40544 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '门架' ROW_FORMAT = Dynamic;
 */
@Data
public class T_GANTRY_MOUNTING {
    private int id;

    private int provinceId;

    private String gantryId;

    private String  gantryName;

    private int gantryType;

    private String pileNumber;

    private String lat;

    private String lng;

    private String gnclat;

    private String gnclng;

    private int isAbnormal;

    private int isGatewayGantry;


}
