package com.zzyl.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 服务订单实体类
 */
@Data
public class ServiceOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 护理项目ID
     */
    private Long projectId;

    /**
     * 护理项目名称
     */
    private String projectName;

    /**
     * 老人ID
     */
    private Long elderId;

    /**
     * 老人姓名
     */
    private String elderName;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 期望服务时间
     */
    private LocalDateTime estimatedArrivalTime;

    /**
     * 订单金额
     */
    private BigDecimal amount;

    /**
     * 订单状态：0-待支付，1-已支付，2-服务中，3-已完成，4-已取消
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

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