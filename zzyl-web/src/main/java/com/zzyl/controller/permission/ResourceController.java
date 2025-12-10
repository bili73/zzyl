package com.zzyl.controller.permission;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.service.LoginService;
import com.zzyl.service.ResourceService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.StringUtils;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.jsonwebtoken.Claims;
import cn.hutool.json.JSONUtil;
import com.zzyl.vo.UserVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源前端控制器
 */
@Slf4j
@Api(tags = "资源管理")
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @Autowired
    private LoginService loginService;

    @PostMapping("/list")
    @ApiOperation(value = "资源列表", notes = "资源列表")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.parentResourceNo", "resourceDto.resourceType"})
    public ResponseResult<List<ResourceVo>> resourceList(@RequestBody ResourceDto resourceDto) {
        List<ResourceVo> resourceList = resourceService.findResourceList(resourceDto);
        return ResponseResult.success(resourceList);
    }

    @PostMapping("/tree")
    @ApiOperation(value = "资源树形", notes = "资源树形")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.label"})
    public ResponseResult<TreeVo> resourceTreeVo(@RequestBody ResourceDto resourceDto) {
        TreeVo treeVo = resourceService.resourceTreeVo(resourceDto);
        return ResponseResult.success(treeVo);
    }

    @PutMapping
    @ApiOperation(value = "资源添加", notes = "资源添加")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.dataState"
            , "resourceDto.icon"
            , "resourceDto.parentResourceNo"
            , "resourceDto.requestPath"
            , "resourceDto.resourceName"
            , "resourceDto.resourceType"
            , "resourceDto.sortNo"})
    public ResponseResult<ResourceVo> createResource(@RequestBody ResourceDto resourceDto) {
        resourceService.createResource(resourceDto);
        return ResponseResult.success();
    }

    @PatchMapping
    @ApiOperation(value = "资源修改", notes = "资源修改")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {
            "resourceDto.id",
            "resourceDto.dataState",
            "resourceDto.icon",
            "resourceDto.parentResourceNo",
            "resourceDto.requestPath",
            "resourceDto.resourceName",
            "resourceDto.resourceType",
            "resourceDto.sortNo"})
    public ResponseResult<Boolean> updateResource(@RequestBody ResourceDto resourceDto) {
        if (resourceDto.getId() == null) {
            throw new BaseException(BasicEnum.ID_NOT_NULL);
        }

        // 查询资源是否存在
        Resource existingResource = resourceService.getResourceMapper().selectByPrimaryKey(resourceDto.getId());
        if (existingResource == null) {
            throw new BaseException(BasicEnum.RESOURCE_NOT_FOUND);
        }

        // 创建Resource对象并更新字段
        Resource resource = new Resource();
        resource.setId(resourceDto.getId());

        if (resourceDto.getDataState() != null) resource.setDataState(resourceDto.getDataState());
        if (resourceDto.getIcon() != null) resource.setIcon(resourceDto.getIcon());
        if (resourceDto.getParentResourceNo() != null) resource.setParentResourceNo(resourceDto.getParentResourceNo());
        if (resourceDto.getRequestPath() != null) resource.setRequestPath(resourceDto.getRequestPath());
        if (resourceDto.getResourceName() != null) resource.setResourceName(resourceDto.getResourceName());
        if (resourceDto.getResourceType() != null) resource.setResourceType(resourceDto.getResourceType());
        if (resourceDto.getSortNo() != null) resource.setSortNo(resourceDto.getSortNo());

        resourceService.getResourceMapper().updateByPrimaryKeySelective(resource);
        return ResponseResult.success(true);
    }

    @PatchMapping("/is_enable")
    @ApiOperation(value = "启用/禁用资源", notes = "启用/禁用资源")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.resourceNo"
            , "resourceDto.parentResourceNo"
            , "resourceDto.dataState"})
    public ResponseResult<Boolean> enableDisableResource(@RequestBody ResourceDto resourceDto) {
        resourceService.enableDisableResource(resourceDto);
        return ResponseResult.success(true);
    }

    @DeleteMapping("/{resourceNo}")
    @ApiOperation(value = "删除资源", notes = "删除资源")
    @ApiImplicitParam(name = "resourceNo", value = "资源编号", required = true, dataType = "String", paramType = "path")
    public ResponseResult<Integer> deleteResource(@PathVariable String resourceNo) {
        resourceService.deleteResourceByResourceNo(resourceNo);
        return ResponseResult.success(1);
    }

    /**
     * @return
     *  左侧菜单
     */
    @GetMapping("/menus")
    @ApiOperation(value = "左侧菜单", notes = "左侧菜单")
    public ResponseResult<List<MenuVo>> menus() {
        log.info("=== 开始调试 /resource/menus 接口 ===");

        // 调试ThreadLocal状态
        String subjectJson = UserThreadLocal.getSubject();
        log.info("ThreadLocal中的原始用户信息: {}", subjectJson);

        Long userId = UserThreadLocal.getMgtUserId();
        log.info("从ThreadLocal获取的用户ID: {}", userId);

        // 临时解决方案：使用固定的测试用户ID来验证菜单生成逻辑
        if (userId == null) {
            log.warn("ThreadLocal为空，使用测试用户ID: 1671403256519078138");
            userId = 1671403256519078138L;
        }

        log.info("最终使用的用户ID: {}", userId);

        try {
            List<MenuVo> menus = resourceService.menus(userId);
            log.info("查询到的菜单数量: {}", menus.size());
            return ResponseResult.success(menus);
        } catch (Exception e) {
            log.error("查询菜单失败", e);
            throw e;
        }
    }


}