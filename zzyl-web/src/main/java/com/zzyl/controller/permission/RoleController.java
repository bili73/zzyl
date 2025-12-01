package com.zzyl.controller.permission;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.RoleDto;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色前端控制器
 */
@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色列表分页查询
     * @param params 查询参数，包含roleName等条件
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return 分页结果
     */
    @PostMapping("page/{pageNum}/{pageSize}")
    @ApiOperation(value = "角色分页",notes = "角色分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "params",value = "查询参数",required = true,dataType = "Map"),
            @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
            @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    public ResponseResult<PageResponse<RoleVo>> findRoleVoPage(
            @RequestBody Map<String, Object> params,
            @PathVariable("pageNum") int pageNum,
            @PathVariable("pageSize") int pageSize) {

        // 从Map参数中提取RoleDto信息
        RoleDto roleDto = new RoleDto();
        if (params != null && params.containsKey("roleName")) {
            roleDto.setRoleName((String) params.get("roleName"));
        }

        // 调用服务层进行分页查询
        PageResponse<RoleVo> pageResponse = roleService.findRoleVoPage(roleDto, pageNum, pageSize);

        return ResponseResult.success(pageResponse);
    }

    /**
     *  保存角色
     * @param roleDto 角色DTO对象
     * @return RoleVo
     */
    @PutMapping
    @ApiOperation(value = "角色添加",notes = "角色添加")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName","roleDto.dataState"})
    public ResponseResult<RoleVo> createRole(@RequestBody RoleDto roleDto) {

        // 调用服务层创建角色
        RoleVo roleVo = roleService.createRole(roleDto);

        return ResponseResult.success(roleVo);
    }

    /**
     * 根据角色查询选中的资源数据
     * @param roleId 角色ID
     * @return 资源编号集合
     */
    @ApiOperation(value = "根据角色查询选中的资源数据")
    @GetMapping("/find-checked-resources/{roleId}")
    public ResponseResult<Set<String>> findCheckedResources(@PathVariable("roleId") Long roleId){

        // 调用服务层查询角色资源
        Set<String> checkedResources = roleService.findCheckedResources(roleId);

        return ResponseResult.success(checkedResources);
    }

    /**
     * 修改角色
     * @param roleDto 角色DTO对象
     * @return 是否成功
     */
    @PatchMapping
    @ApiOperation(value = "角色修改",notes = "角色修改")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName","roleDto.dataState","roleDto.dataScope","roleDto.checkedResourceNos","roleDto.checkedDeptNos","roleDto.id"})
    public ResponseResult<Boolean> updateRole(@RequestBody RoleDto roleDto) {

        // 调用服务层更新角色
        Boolean result = roleService.updateRole(roleDto);

        return ResponseResult.success(result);
    }

    /**
     * 删除角色
     * @param roleId 角色ID
     * @return 是否成功
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseResult<Boolean> removeRole(@PathVariable("roleId") Long roleId) {

        // 调用服务层删除角色
        Boolean result = roleService.removeRole(roleId);

        return ResponseResult.success(result);
    }

    /**
     * 角色下拉列表
     * @return 角色列表
     */
    @ApiOperation(value = "角色下拉列表", notes = "获取所有启用的角色列表，用于下拉选择")
    @GetMapping("/init-roles")
    public ResponseResult<List<RoleVo>> initRoles() {

        // 调用服务层获取角色下拉列表
        List<RoleVo> roleList = roleService.initRoles();

        return ResponseResult.success(roleList);
    }

    /**
     * 角色下拉列表（支持POST请求）
     * @param params 请求参数（可为空）
     * @return 角色列表
     */
    @ApiOperation(value = "角色下拉列表（POST）", notes = "获取所有启用的角色列表，用于下拉选择")
    @PostMapping("/init-roles")
    public ResponseResult<List<RoleVo>> initRolesPost(@RequestBody(required = false) Map<String, Object> params) {

        // 调用服务层获取角色下拉列表
        List<RoleVo> roleList = roleService.initRoles();

        return ResponseResult.success(roleList);
    }
}
