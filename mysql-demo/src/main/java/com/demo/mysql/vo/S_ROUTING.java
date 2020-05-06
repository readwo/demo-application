package com.demo.mysql.vo;

import lombok.Data;

/**
 * DROP TABLE IF EXISTS `S_ROUTING`;
 * CREATE TABLE `S_ROUTING`  (
 *   `routing_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '拓扑ID',
 *   `province_id` int(11) NULL DEFAULT NULL COMMENT '行政区域ID',
 *   `start_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '起始点ID',
 *   `start_type` smallint(6) NULL DEFAULT NULL COMMENT '起始点类型  1：门架  2：收费广场',
 *   `end_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '结束点ID',
 *   `end_type` smallint(6) NULL DEFAULT NULL COMMENT '结束点类型  1：门架  2：收费广场',
 *   `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *   `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
 *   `reviseStatus` smallint(6) NULL DEFAULT 1 COMMENT '1 校正 未审核  2 审核完成',
 *   `orderNumber` int(11) NULL DEFAULT 0 COMMENT '序号',
 *   `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
 *   `vehicle_type` smallint(6) NULL DEFAULT NULL COMMENT '计费车型',
 *   `geometry_type` smallint(6) NULL DEFAULT NULL COMMENT '形状类型 （0：线:，1：矩形，2：圆，3：多边形）',
 *   `fee` float(16, 2) NULL DEFAULT NULL COMMENT '费用,单位元',
 *   `miles` bigint(20) NULL DEFAULT NULL COMMENT '里程',
 *   PRIMARY KEY (`routing_id`) USING BTREE,
 *   INDEX `a`(`routing_id`) USING BTREE,
 *   INDEX `b`(`province_id`) USING BTREE,
 *   INDEX `c`(`start_id`) USING BTREE,
 *   INDEX `d`(`end_id`) USING BTREE,
 *   INDEX `e`(`reviseStatus`) USING BTREE
 * ) ENGINE = InnoDB AUTO_INCREMENT = 94835 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '拓扑网络' ROW_FORMAT = Dynamic;
 */
@Data
public class S_ROUTING {

    private int routingId;

    private int provinceId;

    private String startId;

    private String endId;
}
