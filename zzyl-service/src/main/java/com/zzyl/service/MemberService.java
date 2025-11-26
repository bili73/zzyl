package com.zzyl.service;

import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.vo.LoginVo;

public interface MemberService {
    /**
     * 小程序的登录逻辑
     * @param userLoginRequestDto
     * @return
     */
    LoginVo login(UserLoginRequestDto userLoginRequestDto);
}
