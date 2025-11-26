package com.zzyl.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单实体类
 */
@Data
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 账单编号
     */
    private String billNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 老人ID
     */
    private Long elderId;

    /**
     * 老人姓名
     */
    private String elderName;

    /**
     * 护理项目ID
     */
    private Long projectId;

    /**
     * 护理项目名称
     */
    private String projectName;

    /**
     * 账单金额
     */
    private BigDecimal amount;

    /**
     * 交易状态：0-待支付，1-已支付，2-已退款
     */
    private Integer transactionStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 账单类型：1-服务账单，2-其他账单
     */
    private Integer billType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;
}