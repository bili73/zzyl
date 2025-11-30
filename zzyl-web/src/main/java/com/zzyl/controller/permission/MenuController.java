package com.zzyl.controller.permission;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ResourceDto;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源/菜单前端控制器
 */
@Slf4j
@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class MenuController {

    /**
     * 多条件查询菜单列表
     * @param resourceDto 资源DTO对象
     * @return List<MenuVo>
     */
    @PostMapping("/list")
    @ApiOperation(value = "菜单列表",notes = "菜单列表")
    @ApiImplicitParam(name = "resourceDto",value = "资源DTO对象",required = true,dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.dataState","resourceDto.resourceName"})
    public ResponseResult<List<MenuVo>> menuList(@RequestBody(required = false) ResourceDto resourceDto) {
        // 暂时返回空列表，等后续实现了ResourceService后再完善
        List<MenuVo> menuVoList = new ArrayList<>();
        return ResponseResult.success(menuVoList);
    }

    /**
     * 获取菜单树形结构
     * @param resourceDto 查询条件
     * @return TreeVo
     */
    @PostMapping("/tree")
    @ApiOperation(value = "菜单树形",notes = "菜单树形")
    public ResponseResult<TreeVo> menuTree(@RequestBody(required = false) ResourceDto resourceDto) {
        // 暂时返回空树，等后续实现了ResourceService后再完善
        TreeVo treeVo = new TreeVo();
        return ResponseResult.success(treeVo);
    }

    /**
     * 创建菜单
     * @param resourceDto 资源DTO对象
     * @return MenuVo
     */
    @PutMapping
    @ApiOperation(value = "菜单添加",notes = "菜单添加")
    @ApiImplicitParam(name = "resourceDto",value = "资源DTO对象",required = true,dataType = "ResourceDto")
    public ResponseResult<MenuVo> createMenu(@RequestBody ResourceDto resourceDto) {
        // 暂时返回空对象，等后续实现了ResourceService后再完善
        MenuVo menuVo = new MenuVo();
        return ResponseResult.success(menuVo);
    }

    /**
     * 更新菜单
     * @param resourceDto 资源DTO对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "菜单修改",notes = "菜单修改")
    @ApiImplicitParam(name = "resourceDto",value = "资源DTO对象",required = true,dataType = "ResourceDto")
    public ResponseResult<Boolean> updateMenu(@RequestBody ResourceDto resourceDto) {
        // 暂时返回true，等后续实现了ResourceService后再完善
        return ResponseResult.success(true);
    }

    /**
     * 删除菜单
     */
    @ApiOperation("删除菜单")
    @DeleteMapping("/{menuId}")
    public ResponseResult<Boolean> remove(@PathVariable Long menuId) {
        // 暂时返回true，等后续实现了ResourceService后再完善
        return ResponseResult.success(true);
    }

}