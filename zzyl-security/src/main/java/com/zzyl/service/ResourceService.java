package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;

import java.util.List;

public interface ResourceService {
    /**
     * 根据不同条件查询资源列表
      * @param resourceDto
     * @return
     */
   List<ResourceVo> findResourceList(ResourceDto resourceDto);

    TreeVo resourceTreeVo(ResourceDto resourceDto);

    /**
     * 添加资源菜单
     * @param resourceDto
     */
    void createResource(ResourceDto resourceDto);

    /**
     * 启用/禁用资源
     * @param resourceDto
     */
    void enableDisableResource(ResourceDto resourceDto);

    /**
     * 删除资源
     * @param resourceId
     */
    void deleteResource(Long resourceId);

    /**
     * 获取ResourceMapper
     * @return
     */
    ResourceMapper getResourceMapper();
    /**
     * 根据资源编号删除资源
     * @param resourceNo
     */
    void deleteResourceByResourceNo(String resourceNo);

}
