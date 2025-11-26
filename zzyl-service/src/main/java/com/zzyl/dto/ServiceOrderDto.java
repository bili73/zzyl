package com.zzyl.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 服务订单数据传输对象
 */
@Data
public class ServiceOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单ID（退款时使用）")
    private Long id;

    @ApiModelProperty(value = "护理项目ID", required = true)
    @NotNull(message = "护理项目ID不能为空")
    private Long projectId;

    @ApiModelProperty(value = "老人ID", required = true)
    @NotNull(message = "老人ID不能为空")
    private Long elderId;

    @ApiModelProperty(value = "老人姓名")
    private String name;

    @ApiModelProperty(value = "期望服务时间")
    private String estimatedArrivalTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(value = "退款金额")
    private java.math.BigDecimal refundAmount;
}