package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.constant.CacheConstant;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.entity.RoleResource;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.RoleResourceMapper;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    @Cacheable(value = CacheConstant.ROLE_LIST_CACHE, key = "#roleDto.roleName != null ? #roleDto.roleName.hashCode() : 'all' + '_' + #pageNum + '_' + #pageSize", unless = "#result == null")
    public PageResponse<RoleVo> findRoleVoPage(RoleDto roleDto, Integer pageNum, Integer pageSize) {
        // 使用PageHelper进行分页
        PageHelper.startPage(pageNum, pageSize);

        // 根据条件查询角色
        String roleName = roleDto.getRoleName();
        List<Role> roles = roleMapper.selectByCondition(roleName, null);

        // 使用PageResponse.of方法转换
        Page<Role> page = (Page<Role>) roles;
        return PageResponse.of(page, RoleVo.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = {
            CacheConstant.ROLE_CACHE,
            CacheConstant.ROLE_LIST_CACHE,
            CacheConstant.USER_ROLES_CACHE,
            CacheConstant.ROLE_RESOURCES_CACHE,
            CacheConstant.USER_PERMISSIONS_CACHE
    }, allEntries = true)
    public RoleVo createRole(RoleDto roleDto) {
        // 检查角色名是否已存在
        List<Role> existingRoles = roleMapper.selectByCondition(roleDto.getRoleName(), null);
        if (existingRoles != null && !existingRoles.isEmpty()) {
            throw new RuntimeException("角色名已存在: " + roleDto.getRoleName());
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);

        // 设置默认值
        if (role.getDataState() == null) {
            role.setDataState("0"); // 0启用
        }
        if (role.getSortNo() == null) {
            role.setSortNo(0);
        }

        roleMapper.insertSelective(role);

        RoleVo roleVo = new RoleVo();
        BeanUtils.copyProperties(role, roleVo);
        return roleVo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {
            CacheConstant.ROLE_CACHE,
            CacheConstant.ROLE_LIST_CACHE,
            CacheConstant.USER_ROLES_CACHE,
            CacheConstant.ROLE_RESOURCES_CACHE,
            CacheConstant.USER_PERMISSIONS_CACHE
    }, allEntries = true)
    public Boolean updateRole(RoleDto roleDto) {
        if (roleDto.getId() == null) {
            throw new IllegalArgumentException("角色ID不能为空");
        }

        Role role = new Role();
        BeanUtils.copyProperties(roleDto, role);

        // 更新角色基本信息
        int result = roleMapper.updateByPrimaryKeySelective(role);

        // 如果有资源权限更新，先删除原有权限再添加新权限
        if (roleDto.getCheckedResourceNos() != null && roleDto.getCheckedResourceNos().length > 0) {
            // 删除原有权限
            roleResourceMapper.deleteByRoleId(roleDto.getId()); // 需要在RoleResourceMapper中添加这个方法

            // 添加新权限
            for (String resourceNo : roleDto.getCheckedResourceNos()) {
                RoleResource roleResource = new RoleResource();
                roleResource.setRoleId(roleDto.getId());
                roleResource.setResourceNo(resourceNo);
                roleResource.setDataState("0");
                roleResourceMapper.insertSelective(roleResource);
            }
        }

        // 如果有数据权限更新
        if (roleDto.getCheckedDeptNos() != null && roleDto.getCheckedDeptNos().length > 0) {
            // 这里需要处理角色部门权限，需要RoleDeptMapper
            // 暂时先跳过，后续可以添加
        }

        return result > 0;
    }

    @Override
    @Transactional
    @CacheEvict(value = {
            CacheConstant.ROLE_CACHE,
            CacheConstant.ROLE_LIST_CACHE,
            CacheConstant.USER_ROLES_CACHE,
            CacheConstant.ROLE_RESOURCES_CACHE,
            CacheConstant.USER_PERMISSIONS_CACHE
    }, allEntries = true)
    public Boolean removeRole(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("角色ID不能为空");
        }

        // 删除角色资源关联
        roleResourceMapper.deleteByRoleId(roleId); // 需要在RoleResourceMapper中添加这个方法

        // 删除角色
        int result = roleMapper.deleteByPrimaryKey(roleId);

        return result > 0;
    }

    @Override
    @Cacheable(value = CacheConstant.ROLE_RESOURCES_CACHE, key = "#roleId", unless = "#result == null")
    public Set<String> findCheckedResources(Long roleId) {
        if (roleId == null) {
            throw new IllegalArgumentException("角色ID不能为空");
        }

        // 这里需要自定义查询方法，暂时返回空集合
        // 需要在RoleResourceMapper中添加查询方法
        List<RoleResource> roleResources = roleResourceMapper.selectByRoleId(roleId); // 需要添加这个方法

        return roleResources.stream()
                .map(RoleResource::getResourceNo)
                .collect(Collectors.toSet());
    }

    @Override
    @Cacheable(value = CacheConstant.ROLE_CACHE, key = "'init'", unless = "#result == null or #result.size() == 0")
    public List<RoleVo> initRoles() {
        // 查询所有启用状态的角色
        List<Role> roles = roleMapper.selectAll(); // 需要在RoleMapper中添加这个方法

        return roles.stream()
                .filter(role -> "0".equals(role.getDataState())) // 只返回启用的角色
                .map(role -> {
                    RoleVo roleVo = new RoleVo();
                    BeanUtils.copyProperties(role, roleVo);
                    return roleVo;
                })
                .collect(Collectors.toList());
    }
}