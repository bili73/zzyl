package com.zzyl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警规则视图对象
 */
@Data
public class AlertRuleVo {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 产品密钥
     */
    @ApiModelProperty(value = "产品密钥")
    private String productKey;

    /**
     * 产品名称
     */
    @ApiModelProperty(value = "产品名称")
    private String productName;

    /**
     * 模块ID
     */
    @ApiModelProperty(value = "模块ID")
    private String moduleId;

    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    /**
     * 功能名称
     */
    @ApiModelProperty(value = "功能名称")
    private String functionName;

    /**
     * 功能ID
     */
    @ApiModelProperty(value = "功能ID")
    private String functionId;

    /**
     * 物联网设备ID
     */
    @ApiModelProperty(value = "物联网设备ID")
    private String iotId;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    /**
     * 告警数据类型
     */
    @ApiModelProperty(value = "告警数据类型")
    private Integer alertDataType;

    /**
     * 告警规则名称
     */
    @ApiModelProperty(value = "告警规则名称")
    private String alertRuleName;

    /**
     * 操作符
     */
    @ApiModelProperty(value = "操作符")
    private String operator;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private Float value;

    /**
     * 持续时间（秒）
     */
    @ApiModelProperty(value = "持续时间（秒）")
    private Integer duration;

    /**
     * 告警有效期
     */
    @ApiModelProperty(value = "告警有效期")
    private String alertEffectivePeriod;

    /**
     * 告警静默期（秒）
     */
    @ApiModelProperty(value = "告警静默期（秒）")
    private Integer alertSilentPeriod;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private Long createBy;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
