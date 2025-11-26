package com.zzyl.vo;

import com.zzyl.base.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务订单视图对象
 */
@Data
public class ServiceOrderVo extends BaseVo {

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "护理项目ID")
    private Long projectId;

    @ApiModelProperty(value = "护理项目名称")
    private String projectName;

    @ApiModelProperty(value = "护理项目图片")
    private String projectImage;

    @ApiModelProperty(value = "护理项目单位")
    private String projectUnit;

    @ApiModelProperty(value = "老人ID")
    private Long elderId;

    @ApiModelProperty(value = "老人姓名")
    private String elderName;

    @ApiModelProperty(value = "期望服务时间")
    private LocalDateTime estimatedArrivalTime;

    @ApiModelProperty(value = "订单金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "订单状态：0-待支付，1-已支付，2-服务中，3-已完成，4-已取消")
    private Integer status;

    @ApiModelProperty(value = "状态描述")
    private String statusDesc;

    @ApiModelProperty(value = "交易状态：1-待支付，2-已支付，3-已关闭")
    private Integer paymentStatus;

    @ApiModelProperty(value = "备注")
    private String remark;

    // 为了兼容前端，添加嵌套对象
    @ApiModelProperty(value = "护理项目信息")
    private NursingProjectVo nursingProjectVo;

    @ApiModelProperty(value = "老人信息")
    private ElderVo elderVo;

    @ApiModelProperty(value = "床位信息")
    private BedVo bedVo;

    @ApiModelProperty(value = "下单人信息")
    private MemberVo memberVo;

    @ApiModelProperty(value = "退款记录信息")
    private RefundRecordVo refundRecordVo;

    @ApiModelProperty(value = "护理任务信息")
    private NursingTaskVo nursingTaskVo;

    @ApiModelProperty(value = "取消原因")
    private String reason;

    @Data
    public static class RefundRecordVo {
        private Integer refundStatus;
        private String refundMsg;
        private String memo;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
        private BigDecimal refundAmount;
    }

    @Data
    public static class NursingTaskVo {
        private Long id;
        private String creator;
        private LocalDateTime updateTime;
        private String mark;
        private String taskImage;
    }

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
    public static class BedVo {
        private Long id;
        private String bedNumber;
    }

    @Data
    public static class MemberVo {
        private Long id;
        private String name;
    }
}