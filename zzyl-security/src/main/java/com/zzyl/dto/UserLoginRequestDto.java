package com.zzyl.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录请求DTO
 */
@Data
public class UserLoginRequestDto {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("登录临时凭证")
    private String code;

    @ApiModelProperty("手机号临时凭证")
    private String phoneCode;
}