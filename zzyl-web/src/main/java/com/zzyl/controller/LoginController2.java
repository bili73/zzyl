package com.zzyl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sjqn
 * 后台登录临时接口，后期会进行改造
 */
@RestController("loginController2")
public class LoginController2 {


//    @PostMapping("/security/login")
//    public ResponseResult login(@RequestBody LoginDto loginDto){
//
//        UserVo userVo = new UserVo();
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("username",loginDto.getUsername());
//        map.put("password",loginDto.getPassword());
//
//        userVo.setUserToken("123456");
//
//        return ResponseResult.success(userVo);
//    }

    @GetMapping("/login/test")
    public String test(){
        return "登录测试接口";
    }
}