package com.zzyl.service;

import com.zzyl.dto.LoginDto;
import com.zzyl.vo.UserVo;

/**
 * 登录服务接口
 */
public interface LoginService {

    /**
     * 用户登录
     * @param loginDto 登录信息
     * @return 用户信息
     */
    UserVo login(LoginDto loginDto);
}