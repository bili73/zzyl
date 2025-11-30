package com.zzyl.controller.permission;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.RoleDto;
import com.zzyl.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色前端控制器
 */
@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
public class RoleController {

    /**
     * 初始化角色选项列表
     * @return 角色选项列表
     */
    @GetMapping("/init-roles")
    @ApiOperation(value = "初始化角色选项",notes = "初始化角色选项")
    public ResponseResult<List<Map<String, Object>>> initRoles() {
        // 暂时返回空列表，等后续实现了RoleService后再完善
        List<Map<String, Object>> roleList = new ArrayList<>();
        return ResponseResult.success(roleList);
    }

    /**
     * 多条件查询角色列表
     * @param roleDto 角色DTO对象
     * @return List<RoleVo>
     */
    @PostMapping("/list")
    @ApiOperation(value = "角色列表",notes = "角色列表")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "RoleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.dataState","roleDto.roleName"})
    public ResponseResult<List<RoleVo>> roleList(@RequestBody(required = false) RoleDto roleDto) {
        // 暂时返回空列表，等后续实现了RoleService后再完善
        List<RoleVo> roleVoList = new ArrayList<>();
        return ResponseResult.success(roleVoList);
    }

    /**
     * 创建角色
     * @param roleDto 角色DTO对象
     * @return RoleVo
     */
    @PutMapping
    @ApiOperation(value = "角色添加",notes = "角色添加")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "RoleDto")
    public ResponseResult<RoleVo> createRole(@RequestBody RoleDto roleDto) {
        // 暂时返回空对象，等后续实现了RoleService后再完善
        RoleVo roleVo = new RoleVo();
        return ResponseResult.success(roleVo);
    }

    /**
     * 更新角色
     * @param roleDto 角色DTO对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "角色修改",notes = "角色修改")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "RoleDto")
    public ResponseResult<Boolean> updateRole(@RequestBody RoleDto roleDto) {
        // 暂时返回true，等后续实现了RoleService后再完善
        return ResponseResult.success(true);
    }

    /**
     * 删除角色
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseResult<Boolean> remove(@PathVariable Long roleId) {
        // 暂时返回true，等后续实现了RoleService后再完善
        return ResponseResult.success(true);
    }

}