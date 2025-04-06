/*
 Navicat Premium Data Transfer

 Source Server         : 本地库
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : travel_group

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 09/03/2023 22:24:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `entity_type` int NULL DEFAULT NULL,
  `entity_id` int NULL DEFAULT NULL,
  `target_id` int NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `status` int NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_id`(`user_id` ASC) USING BTREE INVISIBLE,
  INDEX `index_entity_id`(`entity_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 2, 1, 1, 0, 'hello，小姐姐', 0, '2023-02-20 08:52:06');

-- ----------------------------
-- Table structure for discuss_post
-- ----------------------------
DROP TABLE IF EXISTS `discuss_post`;
CREATE TABLE `discuss_post`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `type` int NULL DEFAULT NULL COMMENT '0-普通; 1-置顶;',
  `status` int NULL DEFAULT NULL COMMENT '0-正常; 1-精华; 2-拉黑;',
  `create_time` timestamp NULL DEFAULT NULL,
  `comment_count` int NULL DEFAULT NULL,
  `score` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discuss_post
-- ----------------------------
INSERT INTO `discuss_post` VALUES (1, '2', '玉龙雪山滴滴', '一起约战玉龙雪山，小姐姐优先，一起旅游不寂寞！', 0, 0, '2023-02-02 17:32:04', 1, 3108);

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `user_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `scenic_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '景区id',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (1, '2', '1', '2023-01-21 19:34:34');
INSERT INTO `favorite` VALUES (2, '2', '2', '2023-01-21 20:21:12');
INSERT INTO `favorite` VALUES (3, '2', '3', '2023-01-21 20:21:19');
INSERT INTO `favorite` VALUES (4, '2', '4', '2023-01-21 20:21:24');
INSERT INTO `favorite` VALUES (5, '2', '5', '2023-01-21 22:26:56');
INSERT INTO `favorite` VALUES (6, '2', '6', '2023-01-21 22:27:01');
INSERT INTO `favorite` VALUES (7, '2', '7', '2023-01-21 22:27:05');
INSERT INTO `favorite` VALUES (8, '2', '8', '2023-01-21 22:27:11');
INSERT INTO `favorite` VALUES (9, '2', '9', '2023-01-21 22:27:15');
INSERT INTO `favorite` VALUES (10, '2', '10', '2023-01-21 22:39:53');
INSERT INTO `favorite` VALUES (11, '3', '7', '2023-01-21 23:20:16');
INSERT INTO `favorite` VALUES (12, '3', '1', '2023-01-21 23:20:25');
INSERT INTO `favorite` VALUES (13, '3', '2', '2023-01-21 23:20:37');
INSERT INTO `favorite` VALUES (14, '3', '3', '2023-01-21 23:20:41');
INSERT INTO `favorite` VALUES (15, '3', '4', '2023-01-21 23:20:45');
INSERT INTO `favorite` VALUES (16, '3', '5', '2023-01-21 23:20:49');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '意见反馈id',
  `userId` int NULL DEFAULT NULL COMMENT '用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '反馈内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (1, 2, '这个系统真的卡');
INSERT INTO `feedback` VALUES (2, 2, '意见反馈有没有用啊？？？？');

-- ----------------------------
-- Table structure for login_ticket
-- ----------------------------
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `ticket` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `status` int NULL DEFAULT 0 COMMENT '0-有效; 1-无效;',
  `expired` timestamp NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_ticket`(`ticket`(20) ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `from_id` int NULL DEFAULT NULL,
  `to_id` int NULL DEFAULT NULL,
  `conversation_id` varchar(45) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL,
  `status` int NULL DEFAULT NULL COMMENT '0-未读;1-已读;2-删除;',
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_from_id`(`from_id` ASC) USING BTREE,
  INDEX `index_to_id`(`to_id` ASC) USING BTREE,
  INDEX `index_conversation_id`(`conversation_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 3, 2, '2_3', '你好，user1!', 1, '2023-01-21 17:03:37');
INSERT INTO `message` VALUES (2, 2, 3, '2_3', 'ok,我也很高兴认识你！', 0, '2023-01-21 17:04:34');
INSERT INTO `message` VALUES (3, 1, 3, 'follow', '{&quot;entityType&quot;:3,&quot;entityId&quot;:3,&quot;userId&quot;:2}', 0, '2023-01-21 17:15:11');
INSERT INTO `message` VALUES (4, 1, 3, 'follow', '{&quot;entityType&quot;:3,&quot;entityId&quot;:3,&quot;userId&quot;:2}', 0, '2023-01-21 17:15:20');
INSERT INTO `message` VALUES (5, 1, 2, 'comment', '{&quot;entityType&quot;:1,&quot;entityId&quot;:1,&quot;postId&quot;:1,&quot;userId&quot;:2}', 1, '2023-02-20 08:52:43');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `FIRED_TIME` bigint NOT NULL,
  `SCHED_TIME` bigint NOT NULL,
  `PRIORITY` int NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('communityScheduler', 'postScoreRefreshJob', 'communityJobGroup', NULL, 'com.nowcoder.community.quartz.PostScoreRefreshJob', '1', '0', '0', '1', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);
INSERT INTO `qrtz_job_details` VALUES ('travelScheduler', 'postScoreRefreshJob', 'communityJobGroup', NULL, 'com.fuchen.travel.quartz.PostScoreRefreshJob', '1', '0', '0', '1', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787000737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F40000000000010770800000010000000007800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('communityScheduler', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('travelScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint NOT NULL,
  `CHECKIN_INTERVAL` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `REPEAT_COUNT` bigint NOT NULL,
  `REPEAT_INTERVAL` bigint NOT NULL,
  `TIMES_TRIGGERED` bigint NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
INSERT INTO `qrtz_simple_triggers` VALUES ('communityScheduler', 'postScoreRefreshTrigger', 'communityTriggerGroup', -1, 300000, 13149);
INSERT INTO `qrtz_simple_triggers` VALUES ('travelScheduler', 'postScoreRefreshTrigger', 'communityTriggerGroup', -1, 300000, 21398);

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `INT_PROP_1` int NULL DEFAULT NULL,
  `INT_PROP_2` int NULL DEFAULT NULL,
  `LONG_PROP_1` bigint NULL DEFAULT NULL,
  `LONG_PROP_2` bigint NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL,
  `PRIORITY` int NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `START_TIME` bigint NOT NULL,
  `END_TIME` bigint NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME` ASC, `CALENDAR_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('communityScheduler', 'postScoreRefreshTrigger', 'communityTriggerGroup', 'postScoreRefreshJob', 'communityJobGroup', NULL, 1671947292914, 1671946992914, 0, 'WAITING', 'SIMPLE', 1668002592914, 0, NULL, 0, '');
INSERT INTO `qrtz_triggers` VALUES ('travelScheduler', 'postScoreRefreshTrigger', 'communityTriggerGroup', 'postScoreRefreshJob', 'communityJobGroup', NULL, 1678368616372, 1678368316372, 0, 'WAITING', 'SIMPLE', 1671949216372, 0, NULL, 0, '');

-- ----------------------------
-- Table structure for scenic
-- ----------------------------
DROP TABLE IF EXISTS `scenic`;
CREATE TABLE `scenic`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '景区id',
  `scenic_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '景区名称',
  `introduce` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '景区简介',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '景区内容',
  `image_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '景区图片',
  `notice` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '旅游须知',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '景区价格',
  `merchant` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '经营商家',
  `phone` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `status` int NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scenic
-- ----------------------------
INSERT INTO `scenic` VALUES (1, '故宫博物院', '故宫又称紫禁城，是明、清两代的皇宫，也是古老中国的标志和象征。当你置身于气派规整的高墙深院，能真真切切地感受到它曾经的荣耀。', '北京故宫是中国明清两代的皇家宫殿，旧称紫禁城，位于北京中轴线的中心。北京故宫以三大殿为中心，占地面积约72万平方米，建筑面积约15万平方米，有大小宫殿七十多座，房屋九千余间。 北京故宫是世界上现存规模最大、保存最为完整的木质结构古建筑群之一。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/gugong.jpg', '开放时间\r\n全年 周一 不开放；4月1日-10月31日 周二至周日 08:30-17:00(最晚入园16:10)；11月1日-次年3月31日 周二至周日 08:30-16:30(最晚入园15:40)；清明节,劳动节,端午节,中秋节,国庆节 07:30-17:00(最晚入园16:10)，元旦节,春节 08:30-16:30(最晚入园15:40)；法定节假日周一不闭馆；停止入园时间含钟表馆、珍宝馆\r\n优待政策\r\n未成年人：未满18岁（不含）的中国公民（含港澳台居民及获得永久居留权的外国人）（有监护人陪同），需提前至官网预约，免费\r\n老人：年龄：60周岁（含）以上，优惠\r\n学生：18周岁（含）以上学生，本科以下学历（含本科，不含成人教育和研究生），凭学生证或学校介绍信，优惠\r\n残疾人：凭残疾人证件，免费\r\n军人：“八一”建军节，现役军人凭有效证件，免费\r\n离休干部：凭离休证，免费\r\n低保人员：持有本市社会保障金领取证的人员，凭相关有效证件原件，半价\r\n女性：三八”妇女节，女性观众享受，半价\r\n14岁（含）以下陪同家长：“六一”儿童节当日，14周岁以下儿童（含14周岁）的陪同家长一人享受半价优惠，半价\r\n补充说明：未成年人免费门票（含珍宝馆、钟表馆）需提前预约', 60.00, '5A', '4009501925', '北京市东城区景山前街4号', '2023-01-21 17:47:44', 1);
INSERT INTO `scenic` VALUES (2, '颐和园', '颐和园原是清朝帝王的行宫和花园，是我国现存规模非常大，保存非常完整的皇家园林，景色极具优雅，还有很多珍贵的文物，被誉为“皇家园林博物馆”。', '颐和园规模宏大，全园可分3个区域：以仁寿殿为中心的政治活动区；以玉澜堂、乐寿堂为主体的帝后生活区；以长廊沿线、后山、西区为主的苑园游览区。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/yiheyuan.jpg', '优待政策\r\n儿童：6周岁（含6周岁）以下或身高1.2米（含1.2米）以下的儿童，免费（含园中园）\r\n老人：持本人“北京通—养老助残卡”游客刷卡入园；60周岁及以上外省市游客凭本人身份证验证入园；60周岁及以上外籍游客凭本人护照换票入园；，免费\r\n残疾人：凭残疾人有效证件，免费\r\n离休人员：凭离休证，免费\r\n军人：现役军人、退休军人及武警官兵凭有效证件，免费\r\n省、部级以上劳模/英模：省、部级以上劳模、英模，凭有效证件，免费\r\n未成年人：对6周岁（不含6周岁）～18周岁（含18周岁）未成年人，优惠\r\n学生：全日制大学本科及以下学历学生，凭有效身份证或学生证，优惠\r\n港澳台人员：香港、澳门、台湾等入境游青少年凭《港澳居民来往内地通行证》、《台湾居民来往大陆通行证》或学生证件等有效身份证明，均可办理购票入园手续，优惠\r\n社会保障人员：持有社会保障金领取证的人员凭有效证件，优惠\r\n补充说明：三八妇女节、五四青年节当天，对持有单位介绍信集体游园的可购买优惠票；“六一”国际儿童节当天，儿童免票入园。集体游园的儿童和小学生，其带队老师不超过所带儿童10%的，亦可免票入园。导游带团来园必须凭国家核发的正式导游证、旅行社行程单、派遣单方可免费入园。无证导游或者证件不符合者，一律购票入园；', 30.00, '5A', '010 62881144', '北京市海淀区新建宫门路19号', '2023-01-21 19:32:20', 1);
INSERT INTO `scenic` VALUES (3, ' 八达岭长城', '八达岭长城号称天下九塞之一，风光集巍峨险峻、秀丽苍翠于一体，是明长城景色中的精华。“不到长城非好汉”，很多国人都以这里为登临长城的主要选择。', '八达岭长城关城为东窄西宽的梯形，有东西二门。东门额题“居庸外镇”，西门额题“北门锁钥”。关城向北延伸的长城是北长城，有敌楼12处。关城以南是南长城，有敌楼7处。南长城相比北长城的游客人数较少，如果时间充裕，可以先去走南长城，再走北长城。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/badalingchangcheng-2.jpg', '开放时间\r\n4月1日-10月31日 06:30-16:30；11月1日-次年3月31日 07:30-16:00；春节 06:30-16:00\r\n优待政策\r\n儿童：18岁(不含)以下凭有效证件，免费\r\n学生：18岁（含）-25岁（含）全日制大学生（不含成教等）凭本人有效学生证，半价\r\n老人：60周岁（含）以上凭有效证件，免费\r\n残疾人：凭残疾证（含军残），免费\r\n军人：凭士官证或军官证，免费\r\n离休人员：凭离休证，免费\r\n社会保障金人员：凭社会保障金领取证，优惠\r\n消防救援人员：凭有效证件，免费\r\n补充说明：60周岁以上老人与6周岁以下未成年人的门票免费游客，可提前在景区官网进行预约（请勿购票），预约成功后，可凭本人身份证直接检票入园，无需换票。 优惠政策仅针对门票，索道及地面缆车1.2米（含）以下儿童免费，其他大小同价。 以上信息仅供参考，具体信息请以当天披露为准', 17.50, '5A', '010-61118002', '北京市延庆区G6京藏高速58号出口', '2023-01-21 19:59:00', 1);
INSERT INTO `scenic` VALUES (4, '白马寺', '白马寺始建于东汉永平十一年（公元68年），是佛教传入我国后由官府建造的寺院，历来被尊为中国佛教的“祖庭”和“释源”。据传寺名因“白马负经”的典故而得。', '游客由山门进去寺内。山门是明代所建，山门外两旁的石雕马是宋代的遗物。寺内现有五重大殿，坐落在一条中轴线上，其中主殿是第二重殿大佛殿，也是做法事的主要场所。在第三重殿大雄殿内，摆放着白马寺的“镇寺之宝”——中国仅存的元代“夹贮干漆造像”。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/baimasi.jpg', '开放时间\r\n4月1日-10月7日 07:40-18:30(最晚入园18:00)；10月8日-次年3月31日 08:00-17:30(最晚入园17:00)\r\n优待政策\r\n免票人群：1.4米（含）以下儿童； 70岁（含）以上老人； 残疾人凭残疾证、军人凭军官证、记者凭有效证件； 持洛阳旅游年票的游客，免费\r\n半价人群：学生凭学生证； 60岁（含）-69岁（含）凭身份证件，半价', 99.00, '4A', '0379-63781065,0379-63789090', '洛阳市洛龙区白马寺镇洛白路6号', '2023-01-21 20:03:34', 1);
INSERT INTO `scenic` VALUES (5, '泰山风景区', '泰山风景区位于泰安市泰山区。泰山自古以来便是我国有名的山峰之一，有五岳之首的美誉。泰山风景区以壮丽著称，山峰陡峭高耸，十分壮观，在山顶观看日出、晚霞等也十分绮丽。', '山间经常有云海出现，而冬天则有雾凇奇观，非常漂亮。除了自然风光外，山上还有众多的古迹、石刻、宗教庙宇等人文景观。  春天可以看到山间的桃花和梨花，夏天山顶翠绿，而且由于山顶凉爽，所以这里还是有名的避暑胜地，而秋天山间的树叶红黄相间层林尽染，十分漂亮。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/taishan.jpg', '开放时间\r\n全年 全天开放；一、泰山天外村游客中心（乘车进山至中天门）、中天门客运站（乘车下山至天外村游客中心）自2021年11月1日起，运营时间调整为每日6:00至24:00，其他时段暂停运营。 二、泰山桃花峪游客中心（乘车上行至桃花源后，再乘坐桃花源索道至南天门）、桃花源客运站（乘车下行至桃花峪游客中心）继续保持每日6:00至17:00运行。红门游客中心（徒步进山）继续保持24小时开放。 三、中天门索道（中天门至南天门）每日6:30至17:30运营;桃花源索道（桃花源下站至桃花源上站）每日7:00至17:00运营。后石坞索道08:30至16:00运营。\r\n优待政策\r\n现役军人、军队离退休干部、退休士官：凭有效证件，免费\r\n残疾人：凭有效证件，免费\r\n老人：60周岁（含）以上，免费\r\n中国和山东摄影家协会会员、记者：凭有效证件，免费\r\n儿童：6周岁（含）以下或身高1.4米（含）以下，免费；6周岁（不含）-18周岁（不含）或身高1.4米(不含)以上未成年人，优惠票\r\n“山东惠才卡”、“泰山人才金卡”的高层次人才：凭有效证件，免费\r\n学生：全日制本科及以下学历学生，优惠票\r\n教师、省部级以上劳模、英模：凭有效证件，优惠票\r\n补充说明：免票政策人员，在天外村或桃花峪乘车上山需购买30元车票。', 57.00, '5A', '0538-96008888', ' 泰安市泰山区红门路', '2023-01-21 20:08:33', 1);
INSERT INTO `scenic` VALUES (6, '老君山景区', '老君山景区相传是道教始祖老子的归隐修炼之地，北魏时在山中建庙纪念，后来被封为“天下名山”，历来香火旺盛。', '老君山主景区内包括中天门、金顶、老君庙、南天门、玉皇顶以及马鬃岭等主要景点。老君庙自北魏始建以来，就是中原香客朝拜的中心；玉皇顶的道观中供奉着玉皇大帝，这里也是山中观景的好地方；金顶和马鬃岭则是观看日出和云海的好地方。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/laojunshan.jpg', '开放时间\r\n全年 08:00-18:00；索道运营时间：8:00-18:00\r\n优待政策\r\n学生：18岁(含)以下以及全日制本科、大中专在校生，凭本人有效学生证及身份证实行门票半价；，半价\r\n教师：教师资格证门票半价；，半价\r\n老人：60周岁(含)以上老人凭身份证，免门票\r\n未成年人：12周岁（含）以下儿童免门票，身高在1.4米（含）以下儿童门票、索道全免，免门票\r\n现役军人、残疾军人：持士兵证、军官证、残疾军人证等有效证件，免门票\r\n残疾人：残疾人凭残疾证，免门票\r\n陪护人员：视力、智力残疾人和一级二级肢体残疾人的一名陪护人员，免门票\r\n信教群众：持居士证、皈依证、戒牒证的群众，免门票\r\n警察：全国人民警察凭身份证和人民警察证；，免门票\r\n消防员：全国在职消防员凭本人消防证和身份证，免门票\r\n记者、导游：记者证、导游证，免门票', 48.00, '5A', '0379-66873890,0379-66838888', '洛阳市栾川县七里坪村21组', '2023-01-21 20:15:56', 1);
INSERT INTO `scenic` VALUES (7, '清明上河园', '清明上河园，是以宋代张择端的名画《清明上河图》为蓝本，以北宋都城汴梁（现名开封）的市井生活和古代娱乐为题材的仿古文化主题公园。', '园内分为三部分：迎宾广场、北苑和南苑，再现了繁华的汴京城，是活生生的《清明上河图》。在园中游玩时还能看到“包公巡汴京河”、“王员外嫁女儿”、“岳飞沙场点兵”、“斗鸡比赛”、“踩高跷”、“蹴鞠比赛”等一系列具有北宋特色的故事场景演出和民俗表演。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/qingmingshangheyuan.jpg', '开放时间\r\n1月22日-2月7日 09:00-20:30；2月8日-4月30日 09:00-17:30\r\n优待政策\r\n儿童：1.2米（不含）以下，免费\r\n学生：中国境内全日制大学本科及以下学历学生含大陆及港澳台，凭居民身份证或学生证等有效证件，半价\r\n未成年人：6周岁（不含）-18周岁（不含），半价\r\n老人：60周岁（含）-70周岁（不含）的老年人，凭本人“老年人优待证”或身份证”，半价；70周岁(含)以上凭老年证或身份证，免费\r\n残疾人：凭残疾证和身份证，免费\r\n记者：凭国家新闻出版总署颁发的记者证，免费\r\n军人：现役军人 凭军官证或士兵证或学员证或警官证 (含武警)；残疾军人 凭军残证，免费\r\n人民警察：凭本人警察证和身份证，免费\r\n补充说明：购买大宋东京梦华演出票，可免费进入清明上河园游玩，届时不会根据单清明上河园门票收费标准，补特殊人群门票价差，敬请知晓，感谢。', 120.00, '5A', '0371-25663819,0371-25664874', '开封市龙亭区龙亭西路5号', '2023-01-21 20:29:57', 0);
INSERT INTO `scenic` VALUES (8, '开封包公祠', '开封包公祠，位于开封市西南的包公湖畔，为纪念清官包拯而建的祠堂。自金、元朝以来，开封就建有包公祠，如今的包公祠是上世纪八十年代选址重建的。', '开封有个包青天，铁面无私辨忠奸”，包公在北宋时期曾任开封府尹（开封市长），一生为官清廉，不畏权贵，执法如山。开封包公祠面积不大，是一组仿宋风格的古建筑群。有由大殿、二殿、东西配殿、半壁廊、碑亭等建筑组成。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/kaifengbaogongci.jpg', '开放时间\r\n4月1日-10月31日 07:00-19:00(最晚入园18:00)；11月1日-次年3月31日 07:30-18:00(最晚入园17:00)\r\n优待政策\r\n儿童：身高 1.2米（含）以下（持售票窗口小票入园参观），免费\r\n儿童/老人/学生：6周岁（不含6周岁）至18周岁（不含18周岁）未成年人、全日制大学本科及以下学历学生（凭身份证或学生证）、满60周岁（含60岁）不足70周岁（不含70岁）的老年人（凭本人身份证），半价\r\n老人： 70周岁（含）以上（凭老年证或身份证），免费\r\n残疾人：凭残疾证，免费\r\n现役军人：凭军官证，免费\r\n港澳台入境游青少年：香港、澳门、台湾等入境游青少年凭《港澳居民来往内地通行证》，《台湾居民来往大陆通行证》或学生证件等有效身份证明，半价\r\n补充说明：以上信息仅供参考，具体请以景区当天公布为准。', 30.00, '4A', '0371-25663819,0371-25664874', '开封县向阳路1号', '2023-01-21 20:42:27', 0);
INSERT INTO `scenic` VALUES (9, '龙门石窟', '龙门石窟位列世界文化遗产名录，是河南洛阳的必去景点。', '以伊河为界，石窟分为西山和东山两部分，东山石窟多是唐代作品，而西山石窟开凿于北朝和隋唐时期，是龙门精华的部分，包括奉先寺的卢舍那佛像和古阳洞中的“龙门二十品”。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/longmenshiku.jpg', '开放时间\r\n2月1日-3月31日 08:00-18:00(停止售票17:30)；4月1日-10月7日 08:00-18:30(停止售票17:30)；10月8日-11月14日 08:00-18:00(停止售票17:30)；11月15日-次年1月31日 08:00-17:00(停止售票16:30)\r\n优待政策\r\n老人：60周岁（含）以上老年人；，免费\r\n未成年人：12周岁（不含）-18周岁（不含）凭身份证或户口本原件，半价\r\n儿童：2.12周岁（含）以下或身高1.4米（含）以下儿童；，免费\r\n军人：持军官证、士兵证、学员证、军残证等相关有效证件原件的现役军人、残疾军人；离休人员（凭离休证）、军队离退休干部（凭军官退休证、文职干部退休证、士官退休证原件）；，免费\r\n残疾人：残疾人（凭中国残疾人联合会制发的残疾人证原件及身份证原件)、伤残人民警察（凭民政部、公安部、中国残疾人联合会联合制发的《中华人民共和国伤残人民警察证》及身份证原件）、伤残国家机关工作人员（凭民政部制发的《伤残国家机关工作人员证》原件及身份证原件）、伤残民兵民工（凭民政部制发的《伤残民兵民工》证原件及身份证原件）享受免票。 视力、智力残疾者和一级、二级肢体残疾者可免一名陪护人员门票；，免费\r\n警察：人民警察，凭公安部监制的人民警察证原件，免费\r\n消防员：国家消防救援人员，凭国家应急管理部制发的干部证、消防员证、学员证、退休证原件，免费\r\n记者：记者（持国家新闻出版署发放的记者证。港澳台记者应持有加盖单位行政公章的记者证或工作证，配合《港澳居民来往内地通行证》原件或《台湾居民来往大陆通行证》原件同时使用），免费\r\n学生：参加全国统一普通高校招生考试被录取的全日制大学本科生、专科生，在校中、小学生，凭有效学生证原件（自考生、电大生、夜大生和成教生等非全日制学生不在优惠对象之列），半价\r\n导游：旅行社持有文旅部颁发的电子导游证或经理资格证的人员，凭证件可直接入园，免费\r\n教师：凭教师资格证原件，半价\r\n先进人物：省部级以上劳动模范、国家优秀专家、五一奖章获得者、三八红旗手、优秀教师、道德模范等先进人物，凭荣誉证或专家证及身份证，在票务中心窗口换票后，经检票处验审入园，免费', 158.00, '5A', '0379-65980972', '洛阳市洛龙区龙门镇龙门大道', '2023-01-21 22:25:19', 0);
INSERT INTO `scenic` VALUES (10, '云台山风景名胜区', '云台山是国家级风景名胜区、国家首批5A级旅游景区和全球首批世界地质公园，位于河南焦作市修武县境内，太行山南麓，豫晋省界交汇处，总面积50多平方公里。', '云台山有红石峡、潭瀑峡、泉瀑峡、猕猴谷、茱萸峰、叠彩洞、万善寺、子房湖等八个景点，是一处以裂谷构造、水动力作用和地质地貌景观为主，以自然生态和人文景观为辅，以峰谷交错、绝壁林立与飞瀑流泉、清溪幽潭为特色，集美学价值与科学价值于一身的综合性风景名胜区。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/yuntaishanfengjingmingshengqu.jpg', '开放时间\r\n3月1日-11月30日 06:30-18:30(最晚入园17:30)；12月1日-次年2月28日 07:00-17:30(最晚入园16:30)；云溪谷夜游18:30-20:30，具体开放时间以景区当天公告为准。\r\n优待政策\r\n未成年人：6周岁（不含）-18周岁（含）凭学生证/身份证，门票半价\r\n儿童：身高1.4米（含）或6周岁（含）以下，门票+车票免费\r\n学生：全日制大中专在校学生凭有效学生证，门票半价\r\n人民教师：全国人民教师凭教师资格证，门票半价\r\n军人：现役军人凭军官证或士兵证（需同时出示军人保障卡），门票免费\r\n残疾人：凭残疾证，门票免费\r\n老人：60周岁以上(含）凭身份证/老年人优待证，门票免费\r\n离休干部：凭离休证，门票免费\r\n摄影协会/作家协会会员：中国摄影家协会会员、中国作家协会会员凭有效证件，门票免费\r\n记者：凭国家新闻出版总署颁发的记者证，门票免费\r\n人民警察：国公安民警凭身份证和人民警察证，门票免费\r\n消防员：全国在职消防员凭本人消防证和身份证，门票免费\r\n一级英模、全国道德模范：凭有效证件，门票免费', 120.00, '5A', '0391-7709300', '焦作市修武县七贤镇沙墙村北2公里', '2023-01-21 22:35:01', 0);
INSERT INTO `scenic` VALUES (11, '嵩山少林风景区', '嵩山少林景区为国家级风景名胜区、国家AAAAA级旅游景区、国家地质公园、科普教育基地。', '嵩山位于河南省登封县境内，地处中原，故称中岳，分太室、少室二山，各36峰，主峰海拔1492米。嵩山以其历史悠久,文化灿烂,名胜古迹繁多,居五岳之冠，全山有72峰，峰多寺也多，有“上有七十二峰，下有七十二寺”之说。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/songshanshaolin.jpg', '开放时间\r\n全年 08:00-17:00\r\n优待政策\r\n老人：60周岁以上凭身份证，免费\r\n残疾人：凭残疾人证（视力、智力残疾人和以及、二级肢体残疾人的陪护人员可随行），免费\r\n现役军人、残疾军人：凭士兵证、军官证、残疾军人证等有效证件，免费\r\n国家消防救援人员：凭有效证件，免费\r\n宗教活动场所同一宗教的宗教教职人员及其工作人员：凭有效证件，免费\r\n举行过入教仪式的同一宗教的信教群众：凭有效证件（如佛、道教在家信徒的居士证、皈依证等），免费', 80.00, '5A', '0371-62745000', '郑州市登封市中岳大街146号', '2023-01-22 10:06:46', 0);
INSERT INTO `scenic` VALUES (12, '殷墟', '殷墟，是中国商代后期都城的遗址，是中国历史上被证实的第一个都城，位于安阳市殷都区。', '景区内的博物馆陈列了大量的青铜器、石器、玉器等出土文物，但“司母戊大方鼎”、“妇好鸮尊”等珍贵文物，已经被调配到中国国家博物馆或河南博物院了，在这里你看到的是文物的复制品。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/yinxu.jpg', '部分景点介绍：\r\n\r\n王陵遗址，位于河南省安阳市洹河北岸侯家庄与武官村北高地，东西长约450米，南北宽约250米，总面积约11.3公顷。自1934年以来，在这里累计发现大墓13座，陪葬墓、祭祀坑与车马坑2000余处，并出土了数量众多、制作精美的青铜器、玉器、石器、陶器等，是学术界公认的殷商王陵所在地。\r\n\r\n免费政策：儿童身高1.2米以下免票;70岁以上老人凭有效证件免费。\r\n\r\n优惠政策：学生凭学生证购买学生票;60--70的老人可购老人票(凭有效身份证件)。\r\n\r\n“洹水殷墟名不虚，三千年前是帝都”，认识中华文明，就从安阳殷墟开始!\r\n\r\n', 75.00, '5A', '0372-3161008,0372-3161022', '安阳市殷都区殷墟路1号', '2023-01-22 10:14:54', 0);
INSERT INTO `scenic` VALUES (13, '龙潭大峡谷5A', '龙潭大峡谷景区位于洛阳市新安县北部，是一条以典型的红岩嶂谷群地质地貌景观为主的峡谷景区。', '经过十二亿年地质沉积和260万年水流切割旋蚀形成的高峡瓮谷，堪称世界一绝。峡谷内的自然风光也非常迷人，清澈的溪流和从绝壁飞溅而下的瀑布、深潭随处可见，并且有各种各样的娱乐活动，是炎炎夏日的避暑好去处。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/longtandaxiagu.jpg', '开放时间\r\n全年（冬令时） 09:00-17:00；全年（夏令时） 07:00-17:30\r\n优待政策\r\n儿童：12周岁（含12周岁）及1.4米（含1.4米）以下儿童凭身份证或户口本等有效证件，免费\r\n老人：60岁以上（含60岁）凭有效身份证件，免费\r\n残疾人：凭残疾证，免费\r\n现役军人：凭军官证或士兵证，免费\r\n人民警察和国家消防救援人员：凭证件，免费\r\n学生：凭学生证，半价', 178.00, '5A', '0379-67134180,0379-67134182,0379-69721188', '洛阳市新安县洛阳市新安县北部', '2023-01-22 10:23:58', 0);
INSERT INTO `scenic` VALUES (14, '玉龙雪山', '玉龙雪山为云南省丽江市境内雪山群', '玉龙雪山在纳西语中被称为“欧鲁”，意为“天山”。其十三座雪峰连绵不绝，宛若一条“巨龙”腾越飞舞，故称为“玉龙”。又因其岩性主要为石灰岩与玄武岩，黑白分明，所以又称为“黑白雪山”。是纳西人的神山，传说纳西族保护神“三朵”的化身。', 'https://travel-scenic-url-1304336794.cos.ap-nanjing.myqcloud.com/yulongxueshan.jpg', '开放时间\r\n全年 07:30-16:30；景区根据客流量不定期调整开放时间，客流量大：07:30 - 16:30，客流量小：07:30 - 15:30\r\n优待政策\r\n儿童：6周岁(不含)-18周岁(含)的未成年人，半价；6周岁(含) 以下，免费\r\n学生：持全日制大学本 科及以下学历的在读大学，半价\r\n老人：60周岁(含60) -70周岁(不含)的老人，半价；70周岁(含)以上的老人，免费\r\n减免人员及持丽江一区四县人员：持减免政策相关证件及持丽江一区四县人员持相关证件，免费', 60.00, '5A', '0888-5137677,0888-5161501,0888-5162140,0888-5131068', '丽江市玉龙县丽江古城以北15公里处（玉龙雪山风景区）', '2023-01-22 15:28:57', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL COMMENT '0-普通用户; 1-超级管理员; 2-版主;',
  `status` int NULL DEFAULT NULL COMMENT '0-未激活; 1-已激活;',
  `activation_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `header_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_username`(`username`(20) ASC) USING BTREE,
  INDEX `index_email`(`email`(20) ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '7d60628f65664a274c7bc2428ec7dd5e', 'a0bb6', '1727195232@qq.com', 2, 1, 'd333d36fb94e472483386b69d4b8cc52', 'https://travel-header-url-1304336794.cos.ap-nanjing.myqcloud.com/912d96f91e014ae6a2ef1cbf235f03ec.jpg', '2023-01-21 16:44:12');
INSERT INTO `user` VALUES (2, 'user1', '1d4fa43542a5bf3bf9037517a8ed3f2b', '88dda', 'fuchen1024@qq.com', 0, 1, 'd903c11e70394b1f934d89b9bbfcc8ca', 'https://default-face-1304336794.cos.ap-nanjing.myqcloud.com/face2.jpg', '2023-01-21 16:59:15');
INSERT INTO `user` VALUES (3, 'user2', 'c06f137c92470a7b06a0c971c119ef00', '93056', 'psvmsout@foxmail.com', 0, 1, '11d911ee57b14dfcbdcc6c94235d8658', 'https://travel-header-url-1304336794.cos.ap-nanjing.myqcloud.com/c5c30c66986e419eb9569d617b2c963e.jpg', '2023-01-21 17:02:05');
INSERT INTO `user` VALUES (4, 'admin2', '67f0dc66fca91c6ab256b3aaf29fb73b', 'df14e', 'fuchen2000@foxmail.com', 1, 1, NULL, 'http://localhost:9000/travel/user/header/fc9f3ca81b544439a2af02f1f3a18db4.jpg', '2023-01-21 17:41:04');

SET FOREIGN_KEY_CHECKS = 1;
