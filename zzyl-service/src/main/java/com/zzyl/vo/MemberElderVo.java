package com.zzyl.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户老人关联展示对象
 */
@Data
@ApiModel("用户老人关联展示对象")
public class MemberElderVo {

    @ApiModelProperty("关联ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("老人ID")
    private Long elderId;

    @ApiModelProperty("老人身份证号")
    private String elderIdCardNo;

    @ApiModelProperty("关系类型")
    private Integer relationType;

    @ApiModelProperty("关系描述")
    private String relation;

    @ApiModelProperty("老人姓名")
    private String elderName;

    @ApiModelProperty("老人头像")
    private String elderImage;

    @ApiModelProperty("老人状态")
    private Integer elderStatus;

    @ApiModelProperty("老人手机号")
    private String elderPhone;

    @ApiModelProperty("绑定时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}