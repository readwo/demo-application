package com.demo.mysql.vo;

import lombok.Data;

/**
 * DROP TABLE IF EXISTS `S_ELECTRONIC_FENCE`;
 * CREATE TABLE `S_ELECTRONIC_FENCE`  (
 *   `electronic_fence_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '电子围栏ID',
 *   `fence_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
 *   `electronic_fence_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '围栏数据84',
 *   `electronic_fence_gnc_data` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '电子围栏高德数据',
 *   `fence_status` smallint(6) NULL DEFAULT 0 COMMENT '电子围栏状态是否启用(1：未启用，0：启用)',
 *   `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
 *   `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
 *   `province_id` bigint(20) NULL DEFAULT NULL COMMENT '省',
 *   `reviseStatus` smallint(6) NULL DEFAULT 1 COMMENT '1 校正 未审核  2 审核完成',
 *   `orderNumber` int(11) NULL DEFAULT 0 COMMENT '序号',
 *   `isAbnormal` smallint(6) NULL DEFAULT 0 COMMENT '异常状态：0正常，1异常',
 *   `road_resource_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '编号组合',
 *   `pay_road_id` bigint(20) NULL DEFAULT NULL COMMENT '收费路段ID',
 *   `interval_id` bigint(20) NULL DEFAULT NULL COMMENT '间隔ID',
 *   `stake_mark_id` bigint(20) NULL DEFAULT NULL COMMENT '桩号ID',
 *   `code` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编号',
 *   `geometry_type` smallint(6) NULL DEFAULT NULL COMMENT '形状类型 （0：线:，1：矩形，2：圆，3：多边形）',
 *   `recognize_type` smallint(6) NULL DEFAULT NULL COMMENT '识别点类型（1：路段ETC门架 2：省界ETC门架 3：收费站）',
 *   `fence_type` smallint(6) NULL DEFAULT NULL COMMENT '电子围栏类型  级别类型    0：普通  1：省界',
 *   `start_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通车时间(开始收费时间)',
 *   `end_time` char(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收费时间（结束收费时间）',
 *   PRIMARY KEY (`electronic_fence_id`) USING BTREE,
 *   INDEX `a`(`electronic_fence_id`) USING BTREE,
 *   INDEX `b`(`reviseStatus`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 35163 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '电子围栏' ROW_FORMAT = Dynamic;
 */
@Data
public class S_ELECTRONIC_FENCE {
    private int electronicFenceId;

    private String fenceName;

    private String electronicFenceData;

    private String roadResourceId;

    //private int isAbnormal;


    private int provinceId;

    private int recognizeType;


}
