package com.zzyl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户老人关联DTO
 */
@Data
@ApiModel("用户老人关联请求对象")
public class MemberElderDto {

    @ApiModelProperty(value = "老人姓名", required = true)
    private String name;

    @ApiModelProperty(value = "老人身份证号", required = true)
    private String idCard;

    @ApiModelProperty(value = "您称呼他/她（家人关系，如：叔叔、阿姨、父亲等）", required = true)
    private String remark;

    @ApiModelProperty(value = "关系类型（1：本人，2：家属，3：监护人等）", hidden = true)
    private Integer relationType;

    // 为了兼容，添加getter方法
    public String getIdCardNo() {
        return idCard;
    }

    public String getRelation() {
        return remark;  // 您称呼他/她就是关系描述
    }
}