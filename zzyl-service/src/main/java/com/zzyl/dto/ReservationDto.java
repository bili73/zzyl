package com.zzyl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@ApiModel("小程序端预约请求对象")
public class ReservationDto {

    @ApiModelProperty("预约人姓名")
    @NotBlank(message = "预约人姓名不能为空")
    private String name;

    @ApiModelProperty("预约人手机号")
    @NotBlank(message = "预约人手机号不能为空")
    private String mobile;

    @ApiModelProperty("探访人姓名")
    @NotBlank(message = "探访人姓名不能为空")
    private String visitor;

    @ApiModelProperty("预约时间")
    @NotNull(message = "预约时间不能为空")
    private LocalDateTime time;

    @ApiModelProperty("预约类型，0：参观预约，1：探访预约")
    @NotNull(message = "预约类型不能为空")
    private Integer type;

    @ApiModelProperty("备注")
    private String remark;
}