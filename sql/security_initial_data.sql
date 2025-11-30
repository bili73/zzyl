-- 权限管理模块基础数据初始化

-- 1. 插入系统管理员用户
INSERT INTO `user` (username, password, user_type, real_name, data_state, create_by, create_time)
VALUES ('admin', '$2a$10$7JB720yubVSOfvVWbfXCOOHd3VrcWd.1JvO.1hB1NWge.bP5wfPVS', '0', '系统管理员', '0', 'system', NOW());

-- 2. 插入基础角色
INSERT INTO `role` (role_name, label, data_state, sort_no, create_by, create_time) VALUES
('超级管理员', 'super_admin', '0', 1, 'system', NOW()),
('管理员', 'admin', '0', 2, 'system', NOW()),
('普通用户', 'user', '0', 3, 'system', NOW());

-- 3. 插入超级管理员角色关联
INSERT INTO `user_role` (user_id, role_id, data_state, create_by, create_time)
VALUES (1, 1, '0', 'system', NOW());

-- 4. 插入系统资源（基础菜单结构）
INSERT INTO `resource` (resource_no, parent_resource_no, resource_name, resource_type, request_path, icon, label, data_state, sort_no, create_by, create_time) VALUES
-- 平台级别
('system', NULL, '系统管理', 'c', '/system', 'system', 'system', '0', 1, 'system', NOW()),

-- 用户管理
('user', 'system', '用户管理', 'm', '/user', 'user', 'user:list', '0', 1, 'system', NOW()),
('user:add', 'user', '用户添加', 'r', NULL, NULL, 'user:add', '0', 1, 'system', NOW()),
('user:edit', 'user', '用户编辑', 'r', NULL, NULL, 'user:edit', '0', 2, 'system', NOW()),
('user:delete', 'user', '用户删除', 'r', NULL, NULL, 'user:delete', '0', 3, 'system', NOW()),

-- 角色管理
('role', 'system', '角色管理', 'm', '/role', 'role', 'role:list', '0', 2, 'system', NOW()),
('role:add', 'role', '角色添加', 'r', NULL, NULL, 'role:add', '0', 1, 'system', NOW()),
('role:edit', 'role', '角色编辑', 'r', NULL, NULL, 'role:edit', '0', 2, 'system', NOW()),
('role:delete', 'role', '角色删除', 'r', NULL, NULL, 'role:delete', '0', 3, 'system', NOW'),

-- 资源管理
('resource', 'system', '资源管理', 'm', '/resource', 'resource', 'resource:list', '0', 3, 'system', NOW()),
('resource:add', 'resource', '资源添加', 'r', NULL, NULL, 'resource:add', '0', 1, 'system', NOW()),
('resource:edit', 'resource', '资源编辑', 'r', NULL, NULL, 'resource:edit', '0', 2, 'system', NOW()),
('resource:delete', 'resource', '资源删除', 'r', NULL, NULL, 'resource:delete', '0', 3, 'system', NOW()),

-- 部门管理
('dept', 'system', '部门管理', 'm', '/dept', 'dept', 'dept:list', '0', 4, 'system', NOW()),
('dept:add', 'dept', '部门添加', 'r', NULL, NULL, 'dept:add', '0', 1, 'system', NOW()),
('dept:edit', 'dept', '部门编辑', 'r', NULL, NULL, 'dept:edit', '0', 2, 'system', NOW()),
('dept:delete', 'dept', '部门删除', 'r', NULL, NULL, 'dept:delete', '0', 3, 'system', NOW'),

-- 岗位管理
('post', 'system', '岗位管理', 'm', '/post', 'post', 'post:list', '0', 5, 'system', NOW()),
('post:add', 'post', '岗位添加', 'r', NULL, NULL, 'post:add', '0', 1, 'system', NOW()),
('post:edit', 'post', '岗位编辑', 'r', NULL, NULL, 'post:edit', '0', 2, 'system', NOW()),
('post:delete', 'post', '岗位删除', 'r', NULL, NULL, 'post:delete', '0', 3, 'system', NOW'),

-- 基础业务管理
('basic', NULL, '基础业务', 'c', '/basic', 'basic', 'basic', '0', 2, 'system', NOW()),
('nursing', 'basic', '护理管理', 'm', '/nursing', 'nursing', 'nursing', '0', 1, 'system', NOW()),
('nursing_project', 'nursing', '护理项目管理', 'm', '/nursing-project', 'nursing-project', 'nursing:project:list', '0', 1, 'system', NOW()),
('nursing_level', 'nursing', '护理等级管理', 'm', '/nursing-level', 'nursing-level', 'nursing:level:list', '0', 2, 'system', NOW()),
('nursing_plan', 'nursing', '护理计划管理', 'm', '/nursing-plan', 'nursing-plan', 'nursing:plan:list', '0', 3, 'system', NOW());

-- 5. 为超级管理员分配所有权限
INSERT INTO `role_resource` (role_id, resource_no, data_state, create_by, create_time)
SELECT 1, resource_no, '0', 'system', NOW() FROM `resource`;

-- 6. 插入默认部门
INSERT INTO `dept` (dept_no, parent_dept_no, dept_name, sort_no, data_state, create_by, create_time) VALUES
('root', NULL, '中州养老集团', 1, '0', 'system', NOW()),
('hr', 'root', '人力资源部', 1, '0', 'system', NOW()),
('nursing', 'root', '护理部', 2, '0', 'system', NOW()),
('admin', 'root', '行政部', 3, '0', 'system', NOW()),
('finance', 'root', '财务部', 4, '0', 'system', NOW()),
('medical', 'root', '医务部', 5, '0', 'system', NOW());

-- 7. 插入默认岗位
INSERT INTO `post` (post_no, post_name, dept_no, sort_no, data_state, create_by, create_time) VALUES
('hr01', 'HR经理', 'hr', 1, '0', 'system', NOW()),
('hr02', 'HR专员', 'hr', 2, '0', 'system', NOW()),
('nursing01', '护理主管', 'nursing', 1, '0', 'system', NOW()),
('nursing02', '护理组长', 'nursing', 2, '0', 'system', NOW()),
('nursing03', '护理员', 'nursing', 3, '0', 'system', NOW()),
('admin01', '行政主管', 'admin', 1, '0', 'system', NOW()),
('admin02', '行政专员', 'admin', 2, '0', 'system', NOW()),
('finance01', '财务主管', 'finance', 1, '0', 'system', NOW()),
('finance02', '会计', 'finance', 2, '0', 'system', NOW()),
('medical01', '医务主管', 'medical', 1, '0', 'system', NOW()),
('medical02', '医生', 'medical', 2, '0', 'system', NOW'),
('medical03', '护士', 'medical', 3, '0', 'system', NOW');

-- 8. 更新部门负责人
UPDATE `dept` SET leader_id = 1, leader_name = '系统管理员' WHERE dept_no = 'root';

COMMIT;