package com.zzyl.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.MenuMetaVo;
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
import java.util.stream.Collectors;

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

    /**
     * 根据用户id查询对应的资源数据
     * @param userId
     * @return
     */
    @Override
    public List<MenuVo> menus(Long userId) {
        //查询用户对应的所有的菜单数据
        List<MenuVo> menuVoList = resourceMapper.findListByUserId(userId);
        if (CollUtil.isEmpty(menuVoList)) {
            throw new RuntimeException("用户角色和菜单为空");
        }
        //数据进行分组（parentNo:[{},{}]）
        Map<String, List<MenuVo>> parentRNoMap = menuVoList
                .stream()
                .collect(Collectors.groupingBy(MenuVo::getParentResourceNo));
        //遍历所有数据
        menuVoList.forEach(menuVo -> {
            menuVo.setMeta(MenuMetaVo.builder().title(menuVo.getName()).build());
            menuVo.setRedirect("/"+menuVo.getName());
            //根据父编号到map中查找是否包含子菜单，如果包含则设置为当前菜单的子菜单
            List<MenuVo> menuVos = parentRNoMap.get(menuVo.getResourceNo());
            if(!CollUtil.isEmpty(menuVos)){
                menuVos.forEach(m->{
                    m.setMeta(MenuMetaVo.builder().title(menuVo.getName()).build());
                    m.setRedirect(m.getName());
                });
                menuVo.setChildren(menuVos);
            }
        });
        //根据根编号查找所有的子
        return parentRNoMap.get(SuperConstant.ROOT_PARENT_ID);
    }

    @Override
    public ResourceMapper getResourceMapper() {
        return resourceMapper;
    }
}