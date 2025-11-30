package com.zzyl.controller.permission;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserDto;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
        // 暂时返回空列表，等后续实现了UserService后再完善
        List<UserVo> userVoList = new ArrayList<>();
        return ResponseResult.success(userVoList);
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
        // 暂时返回空对象，等后续实现了UserService后再完善
        UserVo userVo = new UserVo();
        return ResponseResult.success(userVo);
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
        // 暂时返回true，等后续实现了UserService后再完善
        return ResponseResult.success(true);
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/{userId}")
    public ResponseResult<Boolean> remove(@PathVariable Long userId) {
        // 暂时返回true，等后续实现了UserService后再完善
        return ResponseResult.success(true);
    }

}