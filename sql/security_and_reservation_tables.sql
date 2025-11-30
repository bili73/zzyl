-- 权限管理相关数据库表

-- 用户表
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) NOT NULL COMMENT '用户账号',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `open_id` varchar(100) DEFAULT NULL COMMENT '微信openId',
  `user_type` char(1) DEFAULT '0' COMMENT '用户类型（0系统用户 1客户）',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像地址',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `real_name` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `dept_no` varchar(64) DEFAULT NULL COMMENT '部门编号',
  `post_no` varchar(64) DEFAULT NULL COMMENT '岗位编号',
  `is_leader` tinyint(1) DEFAULT '0' COMMENT '是否是部门leader(0:否，1：是)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_open_id` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- 角色表
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `label` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `data_scope` char(1) DEFAULT '0' COMMENT '数据范围（0自定义 1本人 2本部门及以下 3本部门 4全部）',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `sort_no` int(4) DEFAULT '0' COMMENT '显示顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- 用户角色关联表
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

-- 资源表
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_no` varchar(64) DEFAULT NULL COMMENT '资源编号',
  `parent_resource_no` varchar(64) DEFAULT NULL COMMENT '父资源编号',
  `resource_name` varchar(50) DEFAULT '' COMMENT '资源名称',
  `resource_type` char(1) DEFAULT '' COMMENT '资源类型（s平台 c目录 m菜单 r按钮）',
  `request_path` varchar(200) DEFAULT '' COMMENT '请求地址',
  `icon` varchar(100) DEFAULT '#' COMMENT '图标',
  `label` varchar(100) DEFAULT NULL COMMENT '权限标识',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `sort_no` int(4) DEFAULT '0' COMMENT '显示顺序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_resource_no` (`resource_no`),
  KEY `idx_parent_resource_no` (`parent_resource_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资源信息表';

-- 角色资源关联表
CREATE TABLE `role_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色资源ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_no` varchar(64) NOT NULL COMMENT '资源编号',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`role_id`),
  KEY `idx_resource_no` (`resource_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色和资源关联表';

-- 部门表
CREATE TABLE `dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_no` varchar(64) DEFAULT NULL COMMENT '部门编号',
  `parent_dept_no` varchar(64) DEFAULT NULL COMMENT '父部门编号',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `sort_no` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader_id` bigint(20) DEFAULT NULL COMMENT '负责人ID',
  `leader_name` varchar(20) DEFAULT NULL COMMENT '负责人姓名',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`),
  KEY `idx_dept_no` (`dept_no`),
  KEY `idx_parent_dept_no` (`parent_dept_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

-- 岗位表
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_no` varchar(64) DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(50) DEFAULT '' COMMENT '岗位名称',
  `dept_no` varchar(64) DEFAULT NULL COMMENT '部门编号',
  `sort_no` int(4) DEFAULT '0' COMMENT '显示顺序',
  `data_state` char(1) DEFAULT '0' COMMENT '数据状态（0正常 1停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`),
  KEY `idx_post_no` (`post_no`),
  KEY `idx_dept_no` (`dept_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='岗位信息表';

-- 业务功能表

-- 预约表
CREATE TABLE `reservation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '预约ID',
  `name` varchar(30) NOT NULL COMMENT '预约人',
  `mobile` varchar(11) NOT NULL COMMENT '预约人手机号',
  `time` datetime NOT NULL COMMENT '预约时间',
  `visitor` varchar(30) DEFAULT NULL COMMENT '探访人',
  `type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '预约类型（0参观预约 1探访预约）',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '预约状态（0待报道 1已完成 2取消 3过期）',
  `elder_id` bigint(20) DEFAULT NULL COMMENT '老人ID（探访预约时使用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_time` (`time`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `idx_elder_id` (`elder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='预约信息表';

-- 护理项目计划关联表
CREATE TABLE `nursing_project_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `plan_id` bigint(20) NOT NULL COMMENT '计划ID',
  `project_id` bigint(20) NOT NULL COMMENT '项目ID',
  `execute_time` varchar(50) DEFAULT NULL COMMENT '计划执行时间',
  `execute_cycle` int(11) DEFAULT NULL COMMENT '执行周期',
  `execute_frequency` int(11) DEFAULT NULL COMMENT '执行频次',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_plan_id` (`plan_id`),
  KEY `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='护理项目计划关联表';