package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.vo.RoleVo;

import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 角色分页查询
     * @param roleDto 角色查询条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    PageResponse<RoleVo> findRoleVoPage(RoleDto roleDto, Integer pageNum, Integer pageSize);

    /**
     * 创建角色
     * @param roleDto 角色DTO
     * @return 角色VO
     */
    RoleVo createRole(RoleDto roleDto);

    /**
     * 更新角色
     * @param roleDto 角色DTO
     * @return 是否成功
     */
    Boolean updateRole(RoleDto roleDto);

    /**
     * 根据角色ID删除角色
     * @param roleId 角色ID
     * @return 是否成功
     */
    Boolean removeRole(Long roleId);

    /**
     * 根据角色查询选中的资源数据
     * @param roleId 角色ID
     * @return 资源编号集合
     */
    Set<String> findCheckedResources(Long roleId);

    /**
     * 获取角色下拉列表
     * @return 角色列表
     */
    List<RoleVo> initRoles();
}