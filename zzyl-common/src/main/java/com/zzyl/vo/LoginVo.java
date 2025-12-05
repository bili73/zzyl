package com.zzyl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录响应VO
 */
@Data
@NoArgsConstructor
public class LoginVo {

    @ApiModelProperty(value = "用户ID")
    private String id;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "创建人ID")
    private String createBy;

    @ApiModelProperty(value = "更新人ID")
    private String updateBy;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "密码（前端不显示）")
    private String password = "";

    @ApiModelProperty(value = "用户类型（0:系统用户,1:客户）")
    private String userType;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty(value = "部门编号")
    private String deptNo;

    @ApiModelProperty(value = "岗位编号")
    private String postNo;

    @ApiModelProperty(value = "用户令牌")
    private String userToken;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

  
    @Builder
    public LoginVo(String id, String createTime, String updateTime, String createBy, String updateBy,
                   String remark, String username, String userType, String nickName, String email,
                   String realName, String mobile, String sex, String deptNo, String postNo,
                   String userToken, String deptName, String postName) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.remark = remark;
        this.username = username;
        this.userType = userType;
        this.nickName = nickName;
        this.email = email;
        this.realName = realName;
        this.mobile = mobile;
        this.sex = sex;
        this.deptNo = deptNo;
        this.postNo = postNo;
        this.userToken = userToken;
        this.deptName = deptName;
        this.postName = postName;
        // 密码字段强制为空
        this.password = "";
    }
}