package com.zzyl.controller.permission;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserDto;
import com.zzyl.service.UserService;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户前端控制器
 */
@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 多条件查询用户列表
     * @param userDto 用户DTO对象
     * @return List<UserVo>
     */
    @PostMapping("/list")
    @ApiOperation(value = "用户列表",notes = "用户列表")
    @ApiImplicitParam(name = "userDto",value = "用户DTO对象",required = true,dataType = "UserDto")
    @ApiOperationSupport(includeParameters = {"userDto.dataState","userDto.realName"})
    public ResponseResult<List<UserVo>> userList(@RequestBody(required = false) UserDto userDto) {
        try {
            // 使用分页查询的第一页来获取列表
            PageResponse<UserVo> pageResponse = userService.findUserVoPage(userDto, 1, 1000);
            return ResponseResult.success(pageResponse.getRecords());
        } catch (Exception e) {
            log.error("查询用户列表失败", e);
            return ResponseResult.success(new ArrayList<>());
        }
    }

    /**
     * 用户分页查询
     * @param userDto 用户DTO对象
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return PageResponse<UserVo>
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation(value = "用户分页",notes = "用户分页")
    @ApiImplicitParam(name = "userDto",value = "用户DTO对象",required = true,dataType = "UserDto")
    public ResponseResult<PageResponse<UserVo>> userPage(
            @RequestBody(required = false) UserDto userDto,
            @PathVariable Integer pageNum,
            @PathVariable Integer pageSize) {
        try {
            PageResponse<UserVo> pageResponse = userService.findUserVoPage(userDto, pageNum, pageSize);
            return ResponseResult.success(pageResponse);
        } catch (Exception e) {
            log.error("查询用户分页失败", e);
            return ResponseResult.success(PageResponse.of(new ArrayList<>(), pageNum, pageSize, 0L));
        }
    }

    /**
     * 创建用户
     * @param userDto 用户DTO对象
     * @return UserVo
     */
    @PutMapping
    @ApiOperation(value = "用户添加",notes = "用户添加")
    @ApiImplicitParam(name = "userDto",value = "用户DTO对象",required = true,dataType = "UserDto")
    public ResponseResult<UserVo> createUser(@RequestBody UserDto userDto) {
        try {
            UserVo userVo = userService.createUser(userDto);
            return ResponseResult.success(userVo);
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 更新用户
     * @param userDto 用户DTO对象
     * @return Boolean 是否修改成功
     */
    @PatchMapping
    @ApiOperation(value = "用户修改",notes = "用户修改")
    @ApiImplicitParam(name = "userDto",value = "用户DTO对象",required = true,dataType = "UserDto")
    public ResponseResult<Boolean> updateUser(@RequestBody UserDto userDto) {
        try {
            Boolean result = userService.updateUser(userDto);
            return ResponseResult.success(result);
        } catch (Exception e) {
            log.error("更新用户失败", e);
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 获取当前用户信息
     * @return UserVo 当前用户信息
     */
    @GetMapping("/current-user")
    @ApiOperation(value = "当前用户",notes = "当前用户")
    public ResponseResult<UserVo> getCurrentUser() {
        try {
            UserVo userVo = userService.getCurrentUser();
            return ResponseResult.success(userVo);
        } catch (Exception e) {
            log.error("获取当前用户失败", e);
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 启用或禁用用户
     * @param id 用户ID
     * @param status 状态(0:正常,1:禁用)
     * @return Boolean 操作结果
     */
    @PutMapping("/is-enable/{id}/{status}")
    @ApiOperation(value = "启用或禁用用户",notes = "启用或禁用用户")
    @ApiImplicitParam(name = "id",value = "用户ID",required = true,dataType = "long")
    public ResponseResult<Boolean> isEnable(@PathVariable Long id, @PathVariable String status) {
        try {
            Boolean result = userService.isEnable(id, status);
            return ResponseResult.success(result);
        } catch (Exception e) {
            log.error("启用或禁用用户失败", e);
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 删除用户
     * @param userId 用户ID
     * @return Boolean 操作结果
     */
    @DeleteMapping("/remove/{userId}")
    @ApiOperation(value = "删除用户",notes = "删除用户")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "long")
    public ResponseResult<Boolean> remove(@PathVariable Long userId) {
        try {
            Boolean result = userService.removeUser(userId);
            return ResponseResult.success(result);
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return ResponseResult.error(e.getMessage());
        }
    }

    /**
     * 重置密码
     * @param userId 用户ID
     * @return Boolean 操作结果
     */
    @PostMapping("/reset-passwords/{userId}")
    @ApiOperation(value = "密码重置",notes = "密码重置")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "long")
    public ResponseResult<Boolean> resetPassword(@PathVariable Long userId) {
        try {
            Boolean result = userService.resetPassword(userId);
            return ResponseResult.success(result);
        } catch (Exception e) {
            log.error("重置密码失败", e);
            return ResponseResult.error(e.getMessage());
        }
    }
}