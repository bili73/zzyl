package com.zzyl.controller.security;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.LoginDto;
import com.zzyl.service.LoginService;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 安全认证控制器
 */
@Slf4j
@Api(tags = "安全认证")
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     * @param loginDto 登录请求参数
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户名密码登录")
    public ResponseResult<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        log.info("前端调用登录接口");
        UserVo userVo = loginService.login(loginDto);

        // 返回前端期望的数据格式：包含userToken字段的对象
        Map<String, String> result = new HashMap<>();
        result.put("userToken", userVo.getUserToken());

        return ResponseResult.success(result);
    }

    /**
     * 用户登出
     * @return 登出结果
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户登出", notes = "用户退出登录")
    public ResponseResult<Void> logout() {
        log.info("用户登出");
        return ResponseResult.success();
    }
}