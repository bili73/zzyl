package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
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
}