package com.zzyl.entity;

import com.zzyl.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户老人关联实体
 */
@Data
@ApiModel("用户老人关联实体")
public class MemberElder extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("老人ID")
    private Long elderId;

    @ApiModelProperty("老人身份证号（冗余字段，便于查询）")
    private String elderIdCardNo;

    @ApiModelProperty("关系类型（1：本人，2：家属，3：监护人等）")
    private Integer relationType;

    @ApiModelProperty("关系描述（如：儿子、女儿、配偶等）")
    private String relation;

    @ApiModelProperty("用户称呼关系（如：叔叔、阿姨、父亲等）")
    private String remark;
}