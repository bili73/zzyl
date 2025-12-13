package com.zzyl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 告警数据视图对象
 */
@Data
public class AlertDataVo {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long id;

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
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

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
     * 功能ID
     */
    @ApiModelProperty(value = "功能ID")
    private String functionId;

    /**
     * 接入位置
     */
    @ApiModelProperty(value = "接入位置")
    private String accessLocation;

    /**
     * 位置类型
     */
    @ApiModelProperty(value = "位置类型")
    private Integer locationType;

    /**
     * 物理位置类型
     */
    @ApiModelProperty(value = "物理位置类型")
    private Integer physicalLocationType;

    /**
     * 设备描述
     */
    @ApiModelProperty(value = "设备描述")
    private String deviceDescription;

    /**
     * 数据值
     */
    @ApiModelProperty(value = "数据值")
    private String dataValue;

    /**
     * 告警规则ID
     */
    @ApiModelProperty(value = "告警规则ID")
    private Long alertRuleId;

    /**
     * 告警原因
     */
    @ApiModelProperty(value = "告警原因")
    private String alertReason;

    /**
     * 处理结果
     */
    @ApiModelProperty(value = "处理结果")
    private String processingResult;

    /**
     * 处理人ID
     */
    @ApiModelProperty(value = "处理人ID")
    private Long processorId;

    /**
     * 处理人姓名
     */
    @ApiModelProperty(value = "处理人姓名")
    private String processorName;

    /**
     * 处理时间
     */
    @ApiModelProperty(value = "处理时间")
    private LocalDateTime processingTime;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

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
