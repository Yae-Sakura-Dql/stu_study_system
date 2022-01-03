/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 127.0.0.1:3306
 Source Schema         : stu_system

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 03/01/2022 19:06:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chat_record_b_o
-- ----------------------------
DROP TABLE IF EXISTS `chat_record_b_o`;
CREATE TABLE `chat_record_b_o` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_text` varchar(255) DEFAULT NULL,
  `send_id` int(11) NOT NULL,
  `send_name` varchar(255) DEFAULT NULL,
  `receive_id` int(10) NOT NULL,
  `receive_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat_record_b_o
-- ----------------------------
BEGIN;
INSERT INTO `chat_record_b_o` VALUES (1, '学生1的发言', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-01 22:01:10');
INSERT INTO `chat_record_b_o` VALUES (2, '学生2的发言', 4, '学生2', 0, '测试分组8', 'GROUP', '2022-01-01 22:01:42');
INSERT INTO `chat_record_b_o` VALUES (3, '学生3的发言', 5, '学生3', 0, '测试分组8', 'GROUP', '2022-01-01 22:01:47');
INSERT INTO `chat_record_b_o` VALUES (4, '教师的发言', 2, '段启龙测试账户', 0, '测试分组8', 'GROUP', '2022-01-01 22:02:42');
INSERT INTO `chat_record_b_o` VALUES (5, '学生1说话', 3, '段启龙测试账户', 2, '段启龙', 'PERSON', '2022-01-02 13:50:06');
INSERT INTO `chat_record_b_o` VALUES (6, '教师说话', 2, '段启龙测试账户', 3, '学生1', 'PERSON', '2022-01-02 14:52:12');
INSERT INTO `chat_record_b_o` VALUES (7, '测试发言', 2, '段启龙测试账户', 0, '测试分组8', 'GROUP', '2022-01-02 15:01:07');
INSERT INTO `chat_record_b_o` VALUES (8, '发言', 2, '段启龙测试账户', 0, '测试分组8', 'GROUP', '2022-01-02 15:04:59');
INSERT INTO `chat_record_b_o` VALUES (9, '测试', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 15:10:15');
INSERT INTO `chat_record_b_o` VALUES (10, '测试', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 15:10:31');
INSERT INTO `chat_record_b_o` VALUES (11, '测试', 2, '段启龙测试账户', 3, '学生1', 'PERSON', '2022-01-02 15:31:48');
INSERT INTO `chat_record_b_o` VALUES (12, '再次测试', 2, '段启龙测试账户', 3, '学生1', 'PERSON', '2022-01-02 15:32:02');
INSERT INTO `chat_record_b_o` VALUES (13, '测试', 2, '段启龙测试账户', 0, '测试分组8', 'GROUP', '2022-01-02 16:41:53');
INSERT INTO `chat_record_b_o` VALUES (14, '666', 2, '段启龙测试账户', 3, '学生1测试', 'PERSON', '2022-01-02 17:41:41');
INSERT INTO `chat_record_b_o` VALUES (15, '555', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 17:45:04');
INSERT INTO `chat_record_b_o` VALUES (16, '666', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 17:45:06');
INSERT INTO `chat_record_b_o` VALUES (17, '777', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 17:45:08');
INSERT INTO `chat_record_b_o` VALUES (18, '555', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 17:45:45');
INSERT INTO `chat_record_b_o` VALUES (19, '88', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 17:45:49');
INSERT INTO `chat_record_b_o` VALUES (20, '你好,学生2', 2, '段启龙测试账户', 4, '学生2', 'PERSON', '2022-01-02 22:22:50');
INSERT INTO `chat_record_b_o` VALUES (21, '你们好呀', 2, '段启龙测试账户', 0, '测试分组8', 'GROUP', '2022-01-02 22:25:45');
INSERT INTO `chat_record_b_o` VALUES (22, '你们好', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-02 22:26:34');
INSERT INTO `chat_record_b_o` VALUES (23, '老师1你好', 3, '学生1', 8, '教师2', 'PERSON', '2022-01-02 22:39:01');
INSERT INTO `chat_record_b_o` VALUES (24, '你是谁', 3, '学生1', 3, '学生1', 'PERSON', '2022-01-02 22:39:56');
INSERT INTO `chat_record_b_o` VALUES (25, 'hello', 3, '学生1', 0, '测试分组8', 'GROUP', '2022-01-03 09:49:01');
INSERT INTO `chat_record_b_o` VALUES (26, '大家好', 2, '张子聪', 0, '分组6测试', 'GROUP', '2022-01-03 09:52:25');
INSERT INTO `chat_record_b_o` VALUES (27, 'hello', 2, '张子聪', 0, '123', 'GROUP', '2022-01-03 11:15:13');
INSERT INTO `chat_record_b_o` VALUES (28, '你好老师', 3, '学生1', 0, '123', 'GROUP', '2022-01-03 13:00:03');
COMMIT;

-- ----------------------------
-- Table structure for group_b_o
-- ----------------------------
DROP TABLE IF EXISTS `group_b_o`;
CREATE TABLE `group_b_o` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `last_browse_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of group_b_o
-- ----------------------------
BEGIN;
INSERT INTO `group_b_o` VALUES (47, '测试分组8', 2, 5, '2022-01-02 16:13:49');
INSERT INTO `group_b_o` VALUES (48, '测试分组8', 2, 4, '2022-01-02 16:13:49');
INSERT INTO `group_b_o` VALUES (49, '测试分组8', 2, 3, '2022-01-02 16:13:49');
INSERT INTO `group_b_o` VALUES (50, '测试分组8', 2, 6, '2022-01-02 16:13:49');
INSERT INTO `group_b_o` VALUES (56, '测试分组9', 2, 3, '2022-01-02 16:42:54');
INSERT INTO `group_b_o` VALUES (57, '测试分组9', 2, 4, '2022-01-02 16:42:54');
INSERT INTO `group_b_o` VALUES (58, '测试分组9', 2, 5, '2022-01-02 16:42:54');
INSERT INTO `group_b_o` VALUES (59, '测试分组9', 2, 6, '2022-01-02 16:42:54');
INSERT INTO `group_b_o` VALUES (60, '测试分组9', 2, 7, '2022-01-02 16:42:55');
INSERT INTO `group_b_o` VALUES (61, '测试分组6', 2, 5, '2022-01-02 16:46:59');
INSERT INTO `group_b_o` VALUES (62, '测试分组6', 2, 4, '2022-01-02 16:46:59');
INSERT INTO `group_b_o` VALUES (63, '测试分组6', 2, 3, '2022-01-02 16:46:59');
INSERT INTO `group_b_o` VALUES (64, '测试分组6', 2, 6, '2022-01-02 16:46:59');
INSERT INTO `group_b_o` VALUES (67, '分组7', 2, 4, '2022-01-02 22:23:29');
INSERT INTO `group_b_o` VALUES (68, '分组7', 2, 5, '2022-01-02 22:23:29');
INSERT INTO `group_b_o` VALUES (69, '分组8', 2, 4, '2022-01-02 22:23:46');
INSERT INTO `group_b_o` VALUES (70, '分组8', 2, 5, '2022-01-02 22:23:46');
INSERT INTO `group_b_o` VALUES (71, '分组9', 2, 4, '2022-01-02 22:23:58');
INSERT INTO `group_b_o` VALUES (72, '分组9', 2, 5, '2022-01-02 22:23:58');
INSERT INTO `group_b_o` VALUES (81, '分组6测试', 2, 5, '2022-01-02 22:32:54');
INSERT INTO `group_b_o` VALUES (82, '分组6测试', 2, 6, '2022-01-02 22:32:55');
INSERT INTO `group_b_o` VALUES (83, '分组6测试', 2, 7, '2022-01-02 22:32:56');
INSERT INTO `group_b_o` VALUES (84, '分组6测试', 2, 3, '2022-01-02 22:33:02');
INSERT INTO `group_b_o` VALUES (85, '123', 2, 3, '2022-01-03 11:14:21');
INSERT INTO `group_b_o` VALUES (86, '123', 2, 4, '2022-01-03 11:14:21');
INSERT INTO `group_b_o` VALUES (87, '123', 2, 5, '2022-01-03 11:14:21');
COMMIT;

-- ----------------------------
-- Table structure for group_b_o_copy1
-- ----------------------------
DROP TABLE IF EXISTS `group_b_o_copy1`;
CREATE TABLE `group_b_o_copy1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `last_browse_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of group_b_o_copy1
-- ----------------------------
BEGIN;
INSERT INTO `group_b_o_copy1` VALUES (1, '测试分组', 2, 3, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (2, '测试分组', 2, 4, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (3, '测试分组', 2, 5, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (4, '测试分组2', 2, 5, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (5, '测试分组2', 2, 4, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (6, '测试分组2', 2, 3, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (7, '测试分组3', 2, 5, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (8, '测试分组3', 2, 4, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (9, '测试分组3', 2, 3, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (10, '测试分组4', 2, 5, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (11, '测试分组4', 2, 4, '2021-12-19 22:55:45');
INSERT INTO `group_b_o_copy1` VALUES (12, '测试分组4', 2, 3, '2021-12-19 22:55:45');
COMMIT;

-- ----------------------------
-- Table structure for student_b_o
-- ----------------------------
DROP TABLE IF EXISTS `student_b_o`;
CREATE TABLE `student_b_o` (
  `id` int(11) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `classroom` varchar(255) DEFAULT NULL,
  `teacher_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_b_o
-- ----------------------------
BEGIN;
INSERT INTO `student_b_o` VALUES (3, '学生1', '18652850888', '2230268888@qq.com', '软件1803', 2);
INSERT INTO `student_b_o` VALUES (4, '学生2', NULL, NULL, '软件1803', 2);
INSERT INTO `student_b_o` VALUES (5, '学生3', NULL, NULL, '软件1803', 2);
INSERT INTO `student_b_o` VALUES (6, '学生4', NULL, NULL, '软件1803', 2);
INSERT INTO `student_b_o` VALUES (7, '学生5', NULL, NULL, '软件1803', 2);
INSERT INTO `student_b_o` VALUES (14, '学生10', NULL, NULL, '软件1803', 2);
INSERT INTO `student_b_o` VALUES (16, '学生11', NULL, NULL, '软件1803', 2);
COMMIT;

-- ----------------------------
-- Table structure for teacher_b_o
-- ----------------------------
DROP TABLE IF EXISTS `teacher_b_o`;
CREATE TABLE `teacher_b_o` (
  `id` int(11) NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of teacher_b_o
-- ----------------------------
BEGIN;
INSERT INTO `teacher_b_o` VALUES (2, '张子聪', '1783785222', 'dql@163.com');
INSERT INTO `teacher_b_o` VALUES (8, '段启龙', '', '');
INSERT INTO `teacher_b_o` VALUES (9, '教师3', '', '');
INSERT INTO `teacher_b_o` VALUES (13, '教师5', NULL, NULL);
INSERT INTO `teacher_b_o` VALUES (15, '教师11', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for user_b_o
-- ----------------------------
DROP TABLE IF EXISTS `user_b_o`;
CREATE TABLE `user_b_o` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_role_dict` varchar(255) DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_b_o
-- ----------------------------
BEGIN;
INSERT INTO `user_b_o` VALUES (1, 'root', '123456', 'ADMIN', '2021-06-21 00:00:00');
INSERT INTO `user_b_o` VALUES (2, 'tea1', '123456', 'TEACHER', '2022-01-03 09:06:52');
INSERT INTO `user_b_o` VALUES (3, 'stu1', '123456', 'STUDENT', '2022-01-03 08:14:50');
INSERT INTO `user_b_o` VALUES (4, 'stu2', '123456', 'STUDENT', '2021-12-19 22:53:19');
INSERT INTO `user_b_o` VALUES (5, 'stu3', '123456', 'STUDENT', '2021-12-19 22:53:35');
INSERT INTO `user_b_o` VALUES (6, 'stu4', '123456', 'STUDENT', '2021-12-19 22:53:35');
INSERT INTO `user_b_o` VALUES (7, 'stu5', '123456', 'STUDENT', '2021-12-19 22:53:35');
INSERT INTO `user_b_o` VALUES (8, 'tea2', '123456', 'TEACHER', '2022-01-03 09:47:41');
INSERT INTO `user_b_o` VALUES (9, 'tea3', '123456', 'TEACHER', '2021-12-09 22:38:18');
INSERT INTO `user_b_o` VALUES (13, 'tea5', '123456', 'TEACHER', '2022-01-02 22:12:48');
INSERT INTO `user_b_o` VALUES (14, 'stu10', '123456', 'STUDENT', '2022-01-02 22:16:56');
INSERT INTO `user_b_o` VALUES (15, 'tea11', '123456', 'TEACHER', '2022-01-02 22:29:29');
INSERT INTO `user_b_o` VALUES (16, 'stu11', '123456', 'STUDENT', '2022-01-02 22:29:53');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
