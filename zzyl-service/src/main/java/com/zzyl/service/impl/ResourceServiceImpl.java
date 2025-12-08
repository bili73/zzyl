package com.zzyl.service.impl;

import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;
import com.zzyl.vo.TreeItemVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源服务实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<ResourceVo> findResourceList(ResourceDto resourceDto) {
        // 查询资源列表
        List<Resource> resources = resourceMapper.selectList(resourceDto);

        // 转换为ResourceVo
        return resources.stream().map(resource -> {
            ResourceVo resourceVo = new ResourceVo();
            BeanUtils.copyProperties(resource, resourceVo);
            return resourceVo;
        }).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public TreeVo resourceTreeVo(ResourceDto resourceDto) {
        // 查询所有启用状态的资源
        ResourceDto queryDto = new ResourceDto();
        queryDto.setDataState("0"); // 只查询启用状态
        List<Resource> resources = resourceMapper.selectList(queryDto);

        // 构建树形结构
        List<TreeItemVo> treeItems = buildTree(resources);

        TreeVo treeVo = new TreeVo();
        treeVo.setItems(treeItems);
        return treeVo;
    }

    /**
     * 构建树形结构
     */
    private List<TreeItemVo> buildTree(List<Resource> resources) {
        List<TreeItemVo> treeItems = new ArrayList<>();

        // 创建资源映射
        Map<String, TreeItemVo> nodeMap = new HashMap<>();
        List<TreeItemVo> rootNodes = new ArrayList<>();

        // 先创建所有节点
        for (Resource resource : resources) {
            TreeItemVo node = new TreeItemVo();
            node.setId(resource.getResourceNo());
            node.setLabel(resource.getResourceName());
            node.setChildren(new ArrayList<>());
            nodeMap.put(resource.getResourceNo(), node);
        }

        // 建立父子关系
        for (Resource resource : resources) {
            TreeItemVo node = nodeMap.get(resource.getResourceNo());
            String parentResourceNo = resource.getParentResourceNo();

            if (parentResourceNo == null || parentResourceNo.equals("100000000000000") || parentResourceNo.equals("0")) {
                // 根节点
                rootNodes.add(node);
            } else {
                // 子节点
                TreeItemVo parentNode = nodeMap.get(parentResourceNo);
                if (parentNode != null) {
                    parentNode.getChildren().add(node);
                }
            }
        }

        return rootNodes;
    }

    @Override
    public List<String> getUserButtons(Long userId) {
        return new ArrayList<>();
    }

    @Override
    public void createResource(ResourceDto resourceDto) {
        Resource resource = new Resource();
        BeanUtils.copyProperties(resourceDto, resource);
        resourceMapper.insertSelective(resource);
    }

    @Override
    public void enableDisableResource(ResourceDto resourceDto) {
        // 先根据resourceNo查询资源
        Resource existingResource = resourceMapper.selectByResourceNo(resourceDto.getResourceNo());
        if (existingResource == null) {
            throw new RuntimeException("资源不存在: " + resourceDto.getResourceNo());
        }

        // 设置需要更新的字段
        existingResource.setDataState(resourceDto.getDataState());
        if (resourceDto.getParentResourceNo() != null) {
            existingResource.setParentResourceNo(resourceDto.getParentResourceNo());
        }

        // 更新资源
        resourceMapper.updateByPrimaryKeySelective(existingResource);
    }

    @Override
    public void deleteResourceByResourceNo(String resourceNo) {
        Resource resource = resourceMapper.selectByResourceNo(resourceNo);
        if (resource != null) {
            resourceMapper.deleteRoleResourceByResourceNo(resourceNo);
            resourceMapper.deleteByPrimaryKey(resource.getId());
        }
    }

    @Override
    public ResourceMapper getResourceMapper() {
        return resourceMapper;
    }
}