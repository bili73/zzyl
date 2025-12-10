package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;

import java.util.List;

/**
 * 资源服务接口
 */
public interface ResourceService {

    /**
     * 查询资源列表
     * @param resourceDto 查询条件
     * @return 资源列表
     */
    List<ResourceVo> findResourceList(ResourceDto resourceDto);

    /**
     * 构建资源树
     * @param resourceDto 查询条件
     * @return 资源树
     */
    TreeVo resourceTreeVo(ResourceDto resourceDto);

    /**
     * 获取用户按钮权限
     * @param userId 用户ID
     * @return 按钮权限列表
     */
    List<String> getUserButtons(Long userId);

    /**
     * 创建资源
     * @param resourceDto 资源DTO
     */
    void createResource(ResourceDto resourceDto);

    /**
     * 启用/禁用资源
     * @param resourceDto 资源DTO
     */
    void enableDisableResource(ResourceDto resourceDto);

    /**
     * 根据资源编号删除资源
     * @param resourceNo 资源编号
     */
    void deleteResourceByResourceNo(String resourceNo);

    /**
     * 根据用户id查询对应的资源数据
     * @param userId
     * @return
     */
    List<MenuVo> menus(Long userId);

    /**
     * 获取ResourceMapper
     * @return ResourceMapper
     */
    ResourceMapper getResourceMapper();
}