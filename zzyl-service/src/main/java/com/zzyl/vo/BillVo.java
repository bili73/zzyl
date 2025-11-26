package com.zzyl.vo;

import com.zzyl.base.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单视图对象
 */
@Data
public class BillVo extends BaseVo {

    @ApiModelProperty(value = "账单编号")
    private String billNo;

    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "老人ID")
    private Long elderId;

    @ApiModelProperty(value = "老人姓名")
    private String elderName;

    @ApiModelProperty(value = "护理项目名称")
    private String projectName;

    @ApiModelProperty(value = "账单金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易状态：0-待支付，1-已支付，2-已退款")
    private Integer transactionStatus;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "退款时间")
    private LocalDateTime refundTime;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(value = "账单类型：1-服务账单，2-其他账单")
    private Integer billType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    // 为了兼容前端，添加嵌套对象
    @ApiModelProperty(value = "护理项目信息")
    private NursingProjectVo nursingProjectVo;

    @ApiModelProperty(value = "老人信息")
    private ElderVo elderVo;

    @ApiModelProperty(value = "下单人信息")
    private MemberVo memberVo;

    @Data
    public static class NursingProjectVo {
        private Long id;
        private String name;
        private String image;
        private String unit;
        private BigDecimal price;
    }

    @Data
    public static class ElderVo {
        private Long id;
        private String name;
    }

    @Data
    public static class MemberVo {
        private Long id;
        private String name;
    }
}