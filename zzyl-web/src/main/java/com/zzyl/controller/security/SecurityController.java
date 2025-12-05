package com.zzyl.controller.security;

import com.zzyl.base.ResponseResult;
import com.zzyl.controller.LoginController;
import com.zzyl.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 安全认证控制器（兼容旧版本接口）
 */
@Slf4j
@Api(tags = "安全认证")
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private LoginController loginController;

    /**
     * 用户登录（兼容旧版本接口）
     * @param loginRequest 登录请求参数
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录（兼容接口）", notes = "用户名密码登录，前端主要接口")
    public ResponseResult<String> loginRedirect(@RequestBody Object loginRequest) {
        log.info("前端调用登录接口");
        // 将请求转发到LoginController进行处理
        LoginDto loginDto = new LoginDto();
        // 这里需要根据实际请求参数进行转换
        // 暂时简单处理
        return loginController.login(loginDto);
    }

    /**
     * 用户登出（兼容旧版本接口）
     * @return 登出结果
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户登出（兼容接口）", notes = "用户退出登录，建议使用 /login/logout 接口")
    public ResponseResult<String> logoutRedirect() {
        log.info("使用旧版本登出接口，建议迁移到 /login/logout 接口");
        // 返回新接口地址，引导前端使用新的登出接口
        return ResponseResult.error("接口已迁移，请使用 POST /login/logout 接口进行登出");
    }
}