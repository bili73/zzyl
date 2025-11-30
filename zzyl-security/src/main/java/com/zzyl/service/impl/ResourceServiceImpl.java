package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.NoProcessing;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeItemVo;
import com.zzyl.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;
    /**
     * 根据不同条件查询资源列表
     * @param resourceDto
     * @return
     */
    @Override
    public List<ResourceVo> findResourceList(ResourceDto resourceDto) {
        List<Resource> resources = resourceMapper.selectList(resourceDto);
        return BeanUtil.copyToList(resources, ResourceVo.class);
    }

    @Override
    public TreeVo resourceTreeVo(ResourceDto resourceDto) {
        //构建查询条件
        ResourceDto dto = ResourceDto.builder()
                .dataState(SuperConstant.DATA_STATE_0)
                .parentResourceNo(NoProcessing.processString(SuperConstant.ROOT_PARENT_ID))
                .resourceType(SuperConstant.MENU).build();
        //构建所有根节点数据
        List<Resource> resourcesList = resourceMapper.selectList(dto);
        if(EmptyUtil.isNullOrEmpty(resourcesList)){
            throw new RuntimeException("资源信息未定义");
        }
        //没有根节点，构建根节点
        Resource rootResource = new Resource();
        rootResource.setResourceNo(SuperConstant.ROOT_PARENT_ID);
        rootResource.setResourceName(("智慧养老院"));
        //返回的树形集合
        List<TreeItemVo> itemVos = new ArrayList<>();

        //使用递归构建树形结构
        recursionTreeItem(itemVos,rootResource,resourcesList);

        // 返回构建好的树形数据
        TreeVo treeVo = new TreeVo();
        treeVo.setItems(itemVos);
        return treeVo;
    }

    /**
     * 添加资源菜单
     * @param resourceDto
     */
    @Override
    public void createResource(ResourceDto resourceDto) {
        //属性拷贝
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        //查询父资源
        Resource parentResource = resourceMapper.selectByResourceNo(resource.getParentResourceNo());
        resource.setDataState(parentResource.getDataState());
        boolean isIgnore = true;
        //判断是否是按钮，如果是按钮，则不限制层级
        if(StringUtils.isNotEmpty(resourceDto.getResourceType()) && resourceDto.getResourceType().equals(SuperConstant.BUTTON)){
            isIgnore = false;
        }
        //创建当前资源的编号
        String resourceNo = createResourceNo(resourceDto.getParentResourceNo(),isIgnore);
        resource.setResourceNo(resourceNo);
        //保存资源
        resourceMapper.insert(resource);
    }

    /**
     * 启用/禁用资源
     * @param resourceDto
     */
    @Override
    public void enableDisableResource(ResourceDto resourceDto) {
        if (StringUtils.isEmpty(resourceDto.getResourceNo())) {
            throw new BaseException(BasicEnum.RESOURCE_NO_NOT_NULL);
        }

        if (StringUtils.isEmpty(resourceDto.getDataState())) {
            throw new BaseException(BasicEnum.DATA_STATE_NOT_NULL);
        }

        // 查询当前资源
        Resource resource = resourceMapper.selectByResourceNo(resourceDto.getResourceNo());
        if (resource == null) {
            throw new BaseException(BasicEnum.RESOURCE_NOT_FOUND);
        }

        // 更新状态
        resource.setDataState(resourceDto.getDataState());
        resourceMapper.updateByPrimaryKeySelective(resource);

        // 递归更新所有子资源的状态
        updateChildrenState(resource.getResourceNo(), resourceDto.getDataState());
    }

      /**
     * 删除资源
     * @param resourceId
     */
    @Override
    public void deleteResource(Long resourceId) {
        if (resourceId == null) {
            throw new BaseException(BasicEnum.ID_NOT_NULL);
        }

        // 查询资源信息
        Resource resource = resourceMapper.selectByPrimaryKey(resourceId);
        if (resource == null) {
            throw new BaseException(BasicEnum.RESOURCE_NOT_FOUND);
        }

        // 检查是否有子资源
        ResourceDto dto = ResourceDto.builder()
                .parentResourceNo(resource.getResourceNo())
                .build();
        List<Resource> childrenResources = resourceMapper.selectList(dto);

        if (!EmptyUtil.isNullOrEmpty(childrenResources)) {
            throw new BaseException(BasicEnum.HAS_CHILD_RESOURCES);
        }

        // 删除角色资源关联
        resourceMapper.deleteRoleResourceByResourceNo(resource.getResourceNo());

        // 删除资源
        resourceMapper.deleteByPrimaryKey(resourceId);
    }

    /**
     * 获取ResourceMapper
     * @return
     */
    @Override
    public ResourceMapper getResourceMapper() {
        return resourceMapper;
    }


    //创建资源编号
    private String createResourceNo(String resourceNo, boolean isIgnore) {
        // 检查resourceNo是否为空
        if (StringUtils.isEmpty(resourceNo)) {
            throw new RuntimeException("资源编号不能为空");
        }

        //100 001 000 000 000 第一层级
        //100 001 001 000 000 第二层级
        //100 001 001 001 000 第三层级
        //100 001 001 001 001 第四层级
        //判断资源编号是否大于三级
        if(isIgnore && NoProcessing.processString(resourceNo).length() / 3 >= 5){
            throw new BaseException((BasicEnum.DEPT_DEPTH_UPPER_LIMIT));
        }
        //根据父资源编号查询子资源
        ResourceDto dto = ResourceDto.builder()
                .parentResourceNo(resourceNo).build();
        List<Resource> resources = resourceMapper.selectList(dto);
        if(EmptyUtil.isNullOrEmpty(resources)){
            //无下属节点，创建新的节点编号
            //100 001 001 001 000 -> 100 001 001 001 001
            return NoProcessing.createNo(resourceNo,false);
        }else{
            //有下属节点，在已有的节点上追加
            //100 001 001 001 001 -> 100 001 001 001 002
            Long maxNo = resources.stream().map(resource -> {
                return Long.valueOf(resource.getResourceNo());
            }).max(Comparator.comparing(i -> i)).get();
            return NoProcessing.createNo(String.valueOf(maxNo),true);
        }
    }


    private void recursionTreeItem(List<TreeItemVo> itemVos, Resource rootResource, List<Resource> resourcesList) {
        //构建当前资源的属性值
        TreeItemVo treeItemVo = TreeItemVo.builder()
                .id(rootResource.getResourceNo())
                .label(rootResource.getResourceName()).build();
        //获取当前资源下的子资源
        List<Resource> childrenResourcesList = resourcesList.stream()
                .filter(resource -> resource.getParentResourceNo().equals(rootResource.getResourceNo()))
                .collect(Collectors.toList());
        //判断子资源是否不为空
        if(!EmptyUtil.isNullOrEmpty(childrenResourcesList)){
            List<TreeItemVo> children = new ArrayList<>();
            //构建子资源
            childrenResourcesList.forEach(resource -> {
                recursionTreeItem(children,resource,resourcesList);
            });
            treeItemVo.setChildren(children);
        }
        //添加到树形集合中
        itemVos.add(treeItemVo);
    }

    /**
     * 递归更新子资源状态
     * @param parentResourceNo 父资源编号
     * @param dataState 数据状态
     */
    private void updateChildrenState(String parentResourceNo, String dataState) {
        ResourceDto dto = ResourceDto.builder()
                .parentResourceNo(parentResourceNo)
                .build();
        List<Resource> childrenResources = resourceMapper.selectList(dto);

        if (!EmptyUtil.isNullOrEmpty(childrenResources)) {
            for (Resource child : childrenResources) {
                // 更新子资源状态
                child.setDataState(dataState);
                resourceMapper.updateByPrimaryKeySelective(child);
                // 递归更新子资源的子资源
                updateChildrenState(child.getResourceNo(), dataState);
            }
        }
    }
  /**
     * 根据资源编号删除资源
     * @param resourceNo
     */
    @Override
    @Transactional
    public void deleteResourceByResourceNo(String resourceNo) {
        if (resourceNo == null || resourceNo.trim().isEmpty()) {
            throw new BaseException(BasicEnum.ID_NOT_NULL);
        }

        // 根据resourceNo查询资源信息
        ResourceDto queryDto = ResourceDto.builder()
                .resourceNo(resourceNo)
                .build();
        List<Resource> resources = resourceMapper.selectList(queryDto);

        if (EmptyUtil.isNullOrEmpty(resources)) {
            throw new BaseException(BasicEnum.RESOURCE_NOT_FOUND);
        }

        // 取第一个匹配的资源（resourceNo应该是唯一的）
        Resource resource = resources.get(0);
        Long resourceId = resource.getId();

        // 检查是否有子资源
        ResourceDto childrenDto = ResourceDto.builder()
                .parentResourceNo(resource.getResourceNo())
                .build();
        List<Resource> childrenResources = resourceMapper.selectList(childrenDto);

        if (!EmptyUtil.isNullOrEmpty(childrenResources)) {
            throw new BaseException(BasicEnum.HAS_CHILD_RESOURCES);
        }

        // 删除角色资源关联
        resourceMapper.deleteRoleResourceByResourceNo(resource.getResourceNo());

        // 删除资源
        resourceMapper.deleteByPrimaryKey(resourceId);
    }
}
