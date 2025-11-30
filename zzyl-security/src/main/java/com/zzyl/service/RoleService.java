package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.entity.Role;

public interface RoleService {

    /**
     * 分页查询角色列表
     * @param role 角色查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    Page<Role> selectRoleByPage(Role role, Integer pageNum, Integer pageSize);

    /**
     * 根据ID获取角色
     * @param id 角色ID
     * @return 角色信息
     */
    Role selectRoleById(Long id);

    /**
     * 新增角色
     * @param role 角色信息
     * @return 是否成功
     */
    Boolean insertRole(Role role);

    /**
     * 更新角色
     * @param role 角色信息
     * @return 是否成功
     */
    Boolean updateRole(Role role);

    /**
     * 删除角色
     * @param id 角色ID
     * @return 是否成功
     */
    Boolean deleteRoleById(Long id);

    /**
     * 批量删除角色
     * @param ids 角色ID列表
     * @return 是否成功
     */
    Boolean deleteRoleByIds(Long[] ids);
}