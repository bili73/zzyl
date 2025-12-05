package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.vo.RoleVo;

import java.util.List;
import java.util.Set;

/**
 * 角色服务接口
 */
public interface RoleService {

    /**
     * 分页查询角色
     * @param roleDto 角色查询条件
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResponse<RoleVo> findRoleVoPage(RoleDto roleDto, Integer pageNum, Integer pageSize);

    /**
     * 创建角色
     * @param roleDto 角色信息
     * @return 创建结果
     */
    RoleVo createRole(RoleDto roleDto);

    /**
     * 更新角色
     * @param roleDto 角色信息
     * @return 更新结果
     */
    Boolean updateRole(RoleDto roleDto);

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return 删除结果
     */
    Boolean removeRole(Long roleId);

    /**
     * 获取角色的资源权限
     * @param roleId 角色ID
     * @return 资源权限集合
     */
    Set<String> findCheckedResources(Long roleId);

    /**
     * 初始化角色列表
     * @return 角色列表
     */
    List<RoleVo> initRoles();
}