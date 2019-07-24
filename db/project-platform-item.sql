/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.6
 Source Server Type    : MySQL
 Source Server Version : 80014
 Source Host           : 127.0.0.1:3307
 Source Schema         : project-platform-item

 Target Server Type    : MySQL
 Target Server Version : 80014
 File Encoding         : 65001

 Date: 24/07/2019 18:11:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_dept
-- ----------------------------
DROP TABLE IF EXISTS `admin_dept`;
CREATE TABLE `admin_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '部门名称',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `timestamp` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '上级部门',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '部门管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_dept
-- ----------------------------
INSERT INTO `admin_dept` VALUES (4, '某某集团', 0, '2019-04-21 22:53:33', '2019-04-30 22:27:59', 0);
INSERT INTO `admin_dept` VALUES (5, '上海公司', 0, '2019-04-21 22:53:57', '2019-05-03 10:09:18', 4);
INSERT INTO `admin_dept` VALUES (6, '研发部', 0, '2019-04-21 22:54:10', '2019-04-24 08:03:08', 5);
INSERT INTO `admin_dept` VALUES (7, '财务部', 0, '2019-04-21 22:54:46', NULL, 5);
INSERT INTO `admin_dept` VALUES (12, '市场部', 0, '2019-04-30 14:31:46', '2019-04-30 14:47:44', 5);

-- ----------------------------
-- Table structure for admin_job
-- ----------------------------
DROP TABLE IF EXISTS `admin_job`;
CREATE TABLE `admin_job`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '岗位名称',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门id',
  `sort` int(10) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `timestamp` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '岗位管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_job
-- ----------------------------
INSERT INTO `admin_job` VALUES (1, '董事长', 4, 0, '2019-05-03 09:43:56', NULL);
INSERT INTO `admin_job` VALUES (3, '全栈开发', 6, 1, '2019-05-03 10:31:03', NULL);
INSERT INTO `admin_job` VALUES (4, '软件测试', 6, 2, '2019-05-03 10:31:41', NULL);
INSERT INTO `admin_job` VALUES (5, '财务总监', 7, 3, '2019-06-16 00:44:59', NULL);

-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单或按钮名字',
  `menu_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单URL',
  `menu_lev` int(4) NULL DEFAULT 0 COMMENT '菜单级别  1 一级菜单  2 二级菜单',
  `menu_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单样式',
  `menu_type` int(4) NULL DEFAULT 1 COMMENT '菜单类型  1 菜单  2按钮',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级菜单',
  `menu_order` int(10) NULL DEFAULT 0 COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  INDEX `menu_type`(`menu_type`, `parent_id`, `menu_order`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜单资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_permission
-- ----------------------------
INSERT INTO `admin_permission` VALUES (1, '系统设置', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:14');
INSERT INTO `admin_permission` VALUES (2, '权限管理', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:19');
INSERT INTO `admin_permission` VALUES (3, '会员管理', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:22');
INSERT INTO `admin_permission` VALUES (4, '商品管理', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:24');
INSERT INTO `admin_permission` VALUES (5, '抽奖商品', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:27');
INSERT INTO `admin_permission` VALUES (6, '内容管理', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:30');
INSERT INTO `admin_permission` VALUES (7, '财务管理', NULL, 1, NULL, 1, 0, 0, NULL, '2019-05-06 15:36:32');
INSERT INTO `admin_permission` VALUES (9, '轮播图', NULL, 2, NULL, 1, 1, 0, NULL, '2019-05-06 16:36:14');
INSERT INTO `admin_permission` VALUES (10, '大转盘', NULL, 2, NULL, 1, 1, 0, NULL, '2019-05-06 16:36:37');
INSERT INTO `admin_permission` VALUES (11, '编辑', NULL, 0, NULL, 2, 9, 0, NULL, '2019-05-06 16:38:15');
INSERT INTO `admin_permission` VALUES (12, '添加', '11', 0, NULL, 2, 9, 12, NULL, '2019-05-06 17:27:22');
INSERT INTO `admin_permission` VALUES (33, 'ceshi', '/sddsd', 0, NULL, 1, 0, 0, '2019-06-15 14:53:39', '2019-06-15 14:53:38');
INSERT INTO `admin_permission` VALUES (37, '测试2', '/dasdasd', 2, NULL, 1, 36, 0, '2019-06-15 15:10:34', '2019-06-15 15:10:34');

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_parent_id` bigint(20) NULL DEFAULT 0,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名',
  `role_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色编码',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `last` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1:最后一级代理，可创建会员，0:不是最后一级',
  `admin_user_id` bigint(20) NULL DEFAULT 0 COMMENT '所属管理员下级管理员角色',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_name`(`role_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (1, 1, '超级管理员', NULL, '2019-06-22 14:46:29', '13', 1, 1, '2019-06-22 14:46:29');

-- ----------------------------
-- Table structure for admin_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_dept`;
CREATE TABLE `admin_role_dept`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
  `role_id` int(20) NULL DEFAULT NULL COMMENT '角色ID',
  `dept_id` int(20) NULL DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色与部门对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role_dept
-- ----------------------------
INSERT INTO `admin_role_dept` VALUES (47, 7, 6);
INSERT INTO `admin_role_dept` VALUES (74, 0, 4);
INSERT INTO `admin_role_dept` VALUES (75, 0, 5);
INSERT INTO `admin_role_dept` VALUES (76, 0, 6);
INSERT INTO `admin_role_dept` VALUES (77, 0, 7);
INSERT INTO `admin_role_dept` VALUES (78, 0, 12);
INSERT INTO `admin_role_dept` VALUES (99, 0, 4);
INSERT INTO `admin_role_dept` VALUES (100, 0, 5);
INSERT INTO `admin_role_dept` VALUES (101, 0, 6);
INSERT INTO `admin_role_dept` VALUES (102, 0, 7);
INSERT INTO `admin_role_dept` VALUES (103, 0, 12);
INSERT INTO `admin_role_dept` VALUES (104, 0, 4);
INSERT INTO `admin_role_dept` VALUES (105, 0, 5);
INSERT INTO `admin_role_dept` VALUES (106, 0, 6);
INSERT INTO `admin_role_dept` VALUES (107, 0, 7);
INSERT INTO `admin_role_dept` VALUES (108, 0, 12);
INSERT INTO `admin_role_dept` VALUES (109, 0, 4);
INSERT INTO `admin_role_dept` VALUES (110, 0, 5);
INSERT INTO `admin_role_dept` VALUES (111, 0, 6);
INSERT INTO `admin_role_dept` VALUES (112, 0, 7);
INSERT INTO `admin_role_dept` VALUES (113, 0, 12);
INSERT INTO `admin_role_dept` VALUES (119, 13, 6);
INSERT INTO `admin_role_dept` VALUES (143, 5, 4);
INSERT INTO `admin_role_dept` VALUES (144, 5, 5);
INSERT INTO `admin_role_dept` VALUES (145, 5, 6);
INSERT INTO `admin_role_dept` VALUES (146, 5, 7);
INSERT INTO `admin_role_dept` VALUES (147, 5, 12);

-- ----------------------------
-- Table structure for admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `permission_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 151 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_role_permission
-- ----------------------------
INSERT INTO `admin_role_permission` VALUES (141, 1, 1);
INSERT INTO `admin_role_permission` VALUES (142, 1, 9);
INSERT INTO `admin_role_permission` VALUES (143, 1, 12);
INSERT INTO `admin_role_permission` VALUES (144, 1, 11);
INSERT INTO `admin_role_permission` VALUES (145, 1, 10);
INSERT INTO `admin_role_permission` VALUES (146, 1, 2);
INSERT INTO `admin_role_permission` VALUES (147, 1, 3);
INSERT INTO `admin_role_permission` VALUES (148, 1, 4);
INSERT INTO `admin_role_permission` VALUES (149, 1, 5);
INSERT INTO `admin_role_permission` VALUES (150, 1, 33);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '帐号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '昵称',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '添加时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `status` tinyint(2) NULL DEFAULT 0 COMMENT '其他状态',
  `is_disable` tinyint(1) NULL DEFAULT 0 COMMENT '0:有效 1:禁止登录',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '1:删除，0:未删除',
  `admin_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级管理员id',
  `user_invite_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理商邀请码',
  `invitation_path` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '推荐邀请关系树',
  `inspect_status` tinyint(3) NULL DEFAULT 1 COMMENT '审核状态(1-待审核;2-已审核;3-拒绝)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 'admin', 'ac58ace355cb83a929837bb7ea13b000', 'admin', 'admin', NULL, '2019-07-17 16:57:53', '2019-07-17 16:57:51', 0, 0, 0, 0, NULL, NULL, 1);
INSERT INTO `admin_user` VALUES (3, '111', 'fa06082a958d6c6b7c61c8d644fa5e7b', '333', '444', NULL, NULL, '2019-06-21 15:17:54', 0, 1, 1, 0, NULL, NULL, 1);
INSERT INTO `admin_user` VALUES (4, '1111', '46bcb224160621fdbe5b21c4b3cafb3b', '3333', '4444', NULL, NULL, '2019-06-21 15:17:46', 0, 1, 1, 0, NULL, NULL, 1);
INSERT INTO `admin_user` VALUES (5, '111', 'fa06082a958d6c6b7c61c8d644fa5e7b', '333', '444', NULL, NULL, '2019-06-22 14:39:41', 0, 1, 0, 0, NULL, NULL, 1);

-- ----------------------------
-- Table structure for admin_user_dept_job
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_dept_job`;
CREATE TABLE `admin_user_dept_job`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '部门主键ID',
  `job_id` int(20) NULL DEFAULT NULL COMMENT '岗位ID',
  `dept_id` int(20) NULL DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 148 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色与部门对应关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user_dept_job
-- ----------------------------
INSERT INTO `admin_user_dept_job` VALUES (47, 7, 6);
INSERT INTO `admin_user_dept_job` VALUES (74, 0, 4);
INSERT INTO `admin_user_dept_job` VALUES (75, 0, 5);
INSERT INTO `admin_user_dept_job` VALUES (76, 0, 6);
INSERT INTO `admin_user_dept_job` VALUES (77, 0, 7);
INSERT INTO `admin_user_dept_job` VALUES (78, 0, 12);
INSERT INTO `admin_user_dept_job` VALUES (99, 0, 4);
INSERT INTO `admin_user_dept_job` VALUES (100, 0, 5);
INSERT INTO `admin_user_dept_job` VALUES (101, 0, 6);
INSERT INTO `admin_user_dept_job` VALUES (102, 0, 7);
INSERT INTO `admin_user_dept_job` VALUES (103, 0, 12);
INSERT INTO `admin_user_dept_job` VALUES (104, 0, 4);
INSERT INTO `admin_user_dept_job` VALUES (105, 0, 5);
INSERT INTO `admin_user_dept_job` VALUES (106, 0, 6);
INSERT INTO `admin_user_dept_job` VALUES (107, 0, 7);
INSERT INTO `admin_user_dept_job` VALUES (108, 0, 12);
INSERT INTO `admin_user_dept_job` VALUES (109, 0, 4);
INSERT INTO `admin_user_dept_job` VALUES (110, 0, 5);
INSERT INTO `admin_user_dept_job` VALUES (111, 0, 6);
INSERT INTO `admin_user_dept_job` VALUES (112, 0, 7);
INSERT INTO `admin_user_dept_job` VALUES (113, 0, 12);
INSERT INTO `admin_user_dept_job` VALUES (119, 13, 6);
INSERT INTO `admin_user_dept_job` VALUES (143, 5, 4);
INSERT INTO `admin_user_dept_job` VALUES (144, 5, 5);
INSERT INTO `admin_user_dept_job` VALUES (145, 5, 6);
INSERT INTO `admin_user_dept_job` VALUES (146, 5, 7);
INSERT INTO `admin_user_dept_job` VALUES (147, 5, 12);

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role`  (
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------
INSERT INTO `admin_user_role` VALUES (1, 1);
INSERT INTO `admin_user_role` VALUES (1, 2);
INSERT INTO `admin_user_role` VALUES (1, 3);
INSERT INTO `admin_user_role` VALUES (1, 4);

-- ----------------------------
-- Table structure for agent_permission
-- ----------------------------
DROP TABLE IF EXISTS `agent_permission`;
CREATE TABLE `agent_permission`  (
  `id` bigint(20) NOT NULL,
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单或按钮名字',
  `menu_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单URL',
  `menu_lev` int(4) NULL DEFAULT NULL COMMENT '菜单级别  1 一级菜单  2 二级菜单',
  `menu_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜单样式',
  `menu_type` int(4) NULL DEFAULT NULL COMMENT '菜单类型  1 菜单  2按钮',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '上级菜单',
  `menu_order` int(10) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '代理商菜单资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent_permission
-- ----------------------------
INSERT INTO `agent_permission` VALUES (1, '系统管理', 'xxxx.html', 1, 'icon-credit-card', 1, 0, 1);
INSERT INTO `agent_permission` VALUES (2, '角色管理', NULL, 2, 'icon-credit-card', 1, 1, 2);
INSERT INTO `agent_permission` VALUES (3, '用户管理', NULL, 2, 'icon-credit-card', 1, 1, 3);
INSERT INTO `agent_permission` VALUES (4, '系统日志', NULL, 2, 'icon-credit-card', 1, 1, 4);
INSERT INTO `agent_permission` VALUES (5, '角色管理添加', NULL, NULL, '', 2, 2, NULL);
INSERT INTO `agent_permission` VALUES (6, '角色管理删除', NULL, NULL, NULL, 2, 2, NULL);
INSERT INTO `agent_permission` VALUES (7, '角色管理查看', NULL, NULL, NULL, 2, 2, NULL);
INSERT INTO `agent_permission` VALUES (8, '用户管理添加', NULL, NULL, NULL, 2, 3, NULL);
INSERT INTO `agent_permission` VALUES (9, '用户管理删除', NULL, NULL, NULL, 2, 3, NULL);
INSERT INTO `agent_permission` VALUES (10, '用户管理查看', NULL, NULL, NULL, 2, 3, NULL);
INSERT INTO `agent_permission` VALUES (11, '系统日志查看', NULL, NULL, NULL, 2, 4, NULL);
INSERT INTO `agent_permission` VALUES (12, '系统监控', NULL, NULL, NULL, 1, 1, 5);
INSERT INTO `agent_permission` VALUES (13, '系统监控查看', NULL, NULL, NULL, 2, 12, NULL);
INSERT INTO `agent_permission` VALUES (14, '分销管理', NULL, 1, 'icon-credit-card', 1, 0, 6);
INSERT INTO `agent_permission` VALUES (15, '分销级别', NULL, 2, 'icon-credit-card', 1, 14, 7);
INSERT INTO `agent_permission` VALUES (16, '分销级别查看', NULL, NULL, NULL, 2, 15, NULL);
INSERT INTO `agent_permission` VALUES (17, '分销级别添加', NULL, NULL, NULL, 2, 15, NULL);

-- ----------------------------
-- Table structure for agent_role
-- ----------------------------
DROP TABLE IF EXISTS `agent_role`;
CREATE TABLE `agent_role`  (
  `id` bigint(20) NOT NULL,
  `role_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '上级角色id ',
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色名',
  `role_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '角色编码',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `is_last` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1:最后一级代理，可创建会员，0:不是最后一级',
  `admin_user_id` bigint(20) NULL DEFAULT 0 COMMENT '所属管理员下级管理员角色',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '代理商角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent_role
-- ----------------------------
INSERT INTO `agent_role` VALUES (1083303491852402689, NULL, 'admin', '867cd70f-00cd-4a70-a8d9-57c1d3bd9c61', 1547114653771, NULL, 0, 1, '2019-01-25 13:18:18');
INSERT INTO `agent_role` VALUES (1083632255647973378, NULL, '角色管理', 'd7fe35cb-4591-4878-8f1f-608614c3c968', 1547193037167, NULL, 0, 1, '2019-01-25 13:18:18');
INSERT INTO `agent_role` VALUES (1083632642748694530, NULL, '用户管理', 'e0d697e3-c2a5-488e-b420-9db7d88e677b', 1547193129484, NULL, 0, 1, '2019-01-25 13:18:19');
INSERT INTO `agent_role` VALUES (1083641901943209986, NULL, '超级管理员', '0c370f3b-a937-4125-94fc-52778fd37ae6', 1547195337041, NULL, 0, 1, '2019-01-25 15:58:25');
INSERT INTO `agent_role` VALUES (1085418800952483842, NULL, '1', 'a52f9525-779b-4f15-b91d-0ee9f5de2599', 1547618982764, NULL, 0, 1, '2019-01-25 13:18:24');
INSERT INTO `agent_role` VALUES (1088704347015835650, 0, '2', '9289131e-360c-4e4a-afd4-97d88f4de75e', 1548402317988, NULL, 1, 1, '2019-01-25 16:01:19');

-- ----------------------------
-- Table structure for agent_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `agent_role_permission`;
CREATE TABLE `agent_role_permission`  (
  `id` bigint(20) NOT NULL,
  `r_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `m_id` bigint(20) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '代理商菜单角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent_role_permission
-- ----------------------------
INSERT INTO `agent_role_permission` VALUES (1083303491911122946, 1083303491852402689, 1);
INSERT INTO `agent_role_permission` VALUES (1083303491923705857, 1083303491852402689, 2);
INSERT INTO `agent_role_permission` VALUES (1083303491936288770, 1083303491852402689, 3);
INSERT INTO `agent_role_permission` VALUES (1083303491936288771, 1083303491852402689, 4);
INSERT INTO `agent_role_permission` VALUES (1083632255710887938, 1083632255647973378, 1);
INSERT INTO `agent_role_permission` VALUES (1083632255727665154, 1083632255647973378, 2);
INSERT INTO `agent_role_permission` VALUES (1083632642803220482, 1083632642748694530, 1);
INSERT INTO `agent_role_permission` VALUES (1083632642811609089, 1083632642748694530, 3);
INSERT INTO `agent_role_permission` VALUES (1083641902006124545, 1083641901943209986, 1);
INSERT INTO `agent_role_permission` VALUES (1083641902014513153, 1083641901943209986, 2);
INSERT INTO `agent_role_permission` VALUES (1083641902031290369, 1083641901943209986, 5);
INSERT INTO `agent_role_permission` VALUES (1083641902048067585, 1083641901943209986, 6);
INSERT INTO `agent_role_permission` VALUES (1083641902056456193, 1083641901943209986, 7);
INSERT INTO `agent_role_permission` VALUES (1083641902060650497, 1083641901943209986, 3);
INSERT INTO `agent_role_permission` VALUES (1083641902069039106, 1083641901943209986, 8);
INSERT INTO `agent_role_permission` VALUES (1083641902077427713, 1083641901943209986, 9);
INSERT INTO `agent_role_permission` VALUES (1083641902085816322, 1083641901943209986, 10);
INSERT INTO `agent_role_permission` VALUES (1083641902102593537, 1083641901943209986, 4);
INSERT INTO `agent_role_permission` VALUES (1083641902110982146, 1083641901943209986, 11);
INSERT INTO `agent_role_permission` VALUES (1083641902110982147, 1083641901943209986, 12);
INSERT INTO `agent_role_permission` VALUES (1083641902110982148, 1083641901943209986, 13);
INSERT INTO `agent_role_permission` VALUES (1085418801011204097, 1085418800952483842, 1);
INSERT INTO `agent_role_permission` VALUES (1085418801027981314, 1085418800952483842, 2);
INSERT INTO `agent_role_permission` VALUES (1085418801044758530, 1085418800952483842, 5);
INSERT INTO `agent_role_permission` VALUES (1085418801057341442, 1085418800952483842, 6);
INSERT INTO `agent_role_permission` VALUES (1085418801069924354, 1085418800952483842, 7);
INSERT INTO `agent_role_permission` VALUES (1085418801078312961, 1085418800952483842, 3);
INSERT INTO `agent_role_permission` VALUES (1085418801095090178, 1085418800952483842, 8);
INSERT INTO `agent_role_permission` VALUES (1085418801111867393, 1085418800952483842, 9);
INSERT INTO `agent_role_permission` VALUES (1085418801120256002, 1085418800952483842, 10);
INSERT INTO `agent_role_permission` VALUES (1085418801128644610, 1085418800952483842, 4);
INSERT INTO `agent_role_permission` VALUES (1085418801137033218, 1085418800952483842, 11);
INSERT INTO `agent_role_permission` VALUES (1085418801145421826, 1085418800952483842, 12);
INSERT INTO `agent_role_permission` VALUES (1085418801153810434, 1085418800952483842, 13);
INSERT INTO `agent_role_permission` VALUES (1085418801153810435, 1083641901943209986, 14);
INSERT INTO `agent_role_permission` VALUES (1085418801153810436, 1083641901943209986, 15);
INSERT INTO `agent_role_permission` VALUES (1085418801153810437, 1083641901943209986, 16);
INSERT INTO `agent_role_permission` VALUES (1085418801153810438, 1083641901943209986, 17);
INSERT INTO `agent_role_permission` VALUES (1088672560147406850, 1088672560080297985, 1);
INSERT INTO `agent_role_permission` VALUES (1088672560168378370, 1088672560080297985, 2);
INSERT INTO `agent_role_permission` VALUES (1088672560193544193, 1088672560080297985, 5);
INSERT INTO `agent_role_permission` VALUES (1088672560210321409, 1088672560080297985, 6);
INSERT INTO `agent_role_permission` VALUES (1088672560227098625, 1088672560080297985, 7);
INSERT INTO `agent_role_permission` VALUES (1088672560235487233, 1088672560080297985, 3);
INSERT INTO `agent_role_permission` VALUES (1088672560252264450, 1088672560080297985, 8);
INSERT INTO `agent_role_permission` VALUES (1088672560269041666, 1088672560080297985, 9);
INSERT INTO `agent_role_permission` VALUES (1088672560290013185, 1088672560080297985, 10);
INSERT INTO `agent_role_permission` VALUES (1088672560302596097, 1088672560080297985, 4);
INSERT INTO `agent_role_permission` VALUES (1088672560319373314, 1088672560080297985, 11);
INSERT INTO `agent_role_permission` VALUES (1088672560336150529, 1088672560080297985, 12);
INSERT INTO `agent_role_permission` VALUES (1088672560344539137, 1088672560080297985, 13);
INSERT INTO `agent_role_permission` VALUES (1088672560361316353, 1088672560080297985, 14);
INSERT INTO `agent_role_permission` VALUES (1088672560382287873, 1088672560080297985, 15);
INSERT INTO `agent_role_permission` VALUES (1088672560390676482, 1088672560080297985, 16);
INSERT INTO `agent_role_permission` VALUES (1088672560407453697, 1088672560080297985, 17);
INSERT INTO `agent_role_permission` VALUES (1088691924649127938, 1088691924611379201, 1);
INSERT INTO `agent_role_permission` VALUES (1088691924674293761, 1088691924611379201, 2);
INSERT INTO `agent_role_permission` VALUES (1088691924695265282, 1088691924611379201, 5);
INSERT INTO `agent_role_permission` VALUES (1088691924712042498, 1088691924611379201, 6);
INSERT INTO `agent_role_permission` VALUES (1088691924741402625, 1088691924611379201, 7);
INSERT INTO `agent_role_permission` VALUES (1088691924758179842, 1088691924611379201, 3);
INSERT INTO `agent_role_permission` VALUES (1088691924774957057, 1088691924611379201, 8);
INSERT INTO `agent_role_permission` VALUES (1088691924791734273, 1088691924611379201, 9);
INSERT INTO `agent_role_permission` VALUES (1088691924804317185, 1088691924611379201, 10);
INSERT INTO `agent_role_permission` VALUES (1088691924821094401, 1088691924611379201, 4);
INSERT INTO `agent_role_permission` VALUES (1088691924829483009, 1088691924611379201, 11);
INSERT INTO `agent_role_permission` VALUES (1088691924846260225, 1088691924611379201, 12);
INSERT INTO `agent_role_permission` VALUES (1088691924863037442, 1088691924611379201, 13);
INSERT INTO `agent_role_permission` VALUES (1088691924879814657, 1088691924611379201, 14);
INSERT INTO `agent_role_permission` VALUES (1088691924896591873, 1088691924611379201, 15);
INSERT INTO `agent_role_permission` VALUES (1088691924909174786, 1088691924611379201, 16);
INSERT INTO `agent_role_permission` VALUES (1088691924934340610, 1088691924611379201, 17);
INSERT INTO `agent_role_permission` VALUES (1088701987405283329, 1088701987367534594, 1);
INSERT INTO `agent_role_permission` VALUES (1088701987434643457, 1088701987367534594, 2);
INSERT INTO `agent_role_permission` VALUES (1088701987464003585, 1088701987367534594, 5);
INSERT INTO `agent_role_permission` VALUES (1088701987480780801, 1088701987367534594, 6);
INSERT INTO `agent_role_permission` VALUES (1088701987493363713, 1088701987367534594, 7);
INSERT INTO `agent_role_permission` VALUES (1088701987514335233, 1088701987367534594, 3);
INSERT INTO `agent_role_permission` VALUES (1088701987531112450, 1088701987367534594, 8);
INSERT INTO `agent_role_permission` VALUES (1088701987552083969, 1088701987367534594, 9);
INSERT INTO `agent_role_permission` VALUES (1088701987568861185, 1088701987367534594, 10);
INSERT INTO `agent_role_permission` VALUES (1088701987581444098, 1088701987367534594, 4);
INSERT INTO `agent_role_permission` VALUES (1088701987589832706, 1088701987367534594, 11);
INSERT INTO `agent_role_permission` VALUES (1088701987606609921, 1088701987367534594, 12);
INSERT INTO `agent_role_permission` VALUES (1088701987623387138, 1088701987367534594, 13);
INSERT INTO `agent_role_permission` VALUES (1088704347082944514, 1088704347015835650, 14);
INSERT INTO `agent_role_permission` VALUES (1088704347099721729, 1088704347015835650, 15);
INSERT INTO `agent_role_permission` VALUES (1088704347116498945, 1088704347015835650, 17);

-- ----------------------------
-- Table structure for agent_user
-- ----------------------------
DROP TABLE IF EXISTS `agent_user`;
CREATE TABLE `agent_user`  (
  `id` bigint(20) NOT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '帐号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '昵称',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '添加时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `status` tinyint(2) NULL DEFAULT NULL COMMENT '其他状态',
  `is_disable` tinyint(1) NULL DEFAULT 0 COMMENT '0:有效 1:禁止登录',
  `is_deleted` tinyint(1) NULL DEFAULT 0 COMMENT '1:删除，0:未删除',
  `admin_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级管理员id',
  `agent_parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父级分销代理商id',
  `user_invite_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理商邀请码',
  `invitation_path` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '推荐邀请关系树',
  `inspect_status` tinyint(3) NULL DEFAULT 1 COMMENT '审核状态(1-待审核;2-已审核;3-拒绝)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '代理商用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent_user
-- ----------------------------
INSERT INTO `agent_user` VALUES (1, 'admin', 'admin', NULL, 'admin', NULL, NULL, '2019-01-24 17:49:01', NULL, NULL, 0, 0, 0, NULL, NULL, 1);

-- ----------------------------
-- Table structure for agent_user_info
-- ----------------------------
DROP TABLE IF EXISTS `agent_user_info`;
CREATE TABLE `agent_user_info`  (
  `admin_user_id` bigint(20) NOT NULL,
  `margin` decimal(25, 8) NULL DEFAULT 0.00000000 COMMENT '保证金',
  `concession_ratio` decimal(25, 8) NULL DEFAULT 0.00000000 COMMENT '佣金比例',
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代理商名字',
  `details` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注详情',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '市',
  `county` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '县',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '详细地点',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分销代理商信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for agent_user_role
-- ----------------------------
DROP TABLE IF EXISTS `agent_user_role`;
CREATE TABLE `agent_user_role`  (
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色用户关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of agent_user_role
-- ----------------------------
INSERT INTO `agent_user_role` VALUES (1083641901943209986, 1);

-- ----------------------------
-- Table structure for com_config_dict
-- ----------------------------
DROP TABLE IF EXISTS `com_config_dict`;
CREATE TABLE `com_config_dict`  (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据值',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(4) NULL DEFAULT NULL COMMENT '排序（升序）',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `timestamp` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`label`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of com_config_dict
-- ----------------------------
INSERT INTO `com_config_dict` VALUES (4, NULL, NULL, 'user_status', '用户状态', NULL, '2019-05-26 15:13:55', '2019-06-03 20:55:09', '用户状态');
INSERT INTO `com_config_dict` VALUES (7, '0', '正常', 'user_status', NULL, 1, '2019-06-03 20:53:24', '2019-06-07 23:11:30', NULL);
INSERT INTO `com_config_dict` VALUES (8, '1', '锁定', 'user_status', NULL, 0, '2019-06-07 20:43:29', '2019-06-07 20:43:29', NULL);

-- ----------------------------
-- Table structure for log_admin_agent
-- ----------------------------
DROP TABLE IF EXISTS `log_admin_agent`;
CREATE TABLE `log_admin_agent`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求地址',
  `request_way` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求方式',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'IP',
  `method_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法地址',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法别名',
  `request_param` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '请求参数',
  `return_param` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '返回参数',
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '耗时',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `operator_id` bigint(255) NULL DEFAULT NULL COMMENT '操作人id',
  `operator_platform` tinyint(2) NULL DEFAULT 1 COMMENT '1,admin，2，agent',
  `login_facility` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录设备',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 443 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_app_pc
-- ----------------------------
DROP TABLE IF EXISTS `log_app_pc`;
CREATE TABLE `log_app_pc`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求地址',
  `request_way` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求方式',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'IP',
  `method_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法地址',
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法别名',
  `request_param` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求参数',
  `return_param` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '返回参数',
  `time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '耗时',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `operator_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人id',
  `operator_platform` tinyint(2) NULL DEFAULT 1 COMMENT '1,app',
  `login_facility` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录设备',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 604 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
