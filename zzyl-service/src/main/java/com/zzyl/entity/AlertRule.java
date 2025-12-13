package com.zzyl.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警规则实体类
 */
@Data
public class AlertRule {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 产品密钥
     */
    private String productKey;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 模块ID
     */
    private String moduleId;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 功能名称
     */
    private String functionName;

    /**
     * 功能ID
     */
    private String functionId;

    /**
     * 物联网设备ID
     */
    private String iotId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 告警数据类型
     */
    private Integer alertDataType;

    /**
     * 告警规则名称
     */
    private String alertRuleName;

    /**
     * 操作符
     */
    private String operator;

    /**
     * 值
     */
    private Float value;

    /**
     * 持续时间（秒）
     */
    private Integer duration;

    /**
     * 告警有效期
     */
    private String alertEffectivePeriod;

    /**
     * 告警静默期（秒）
     */
    private Integer alertSilentPeriod;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 备注
     */
    private String remark;
}
