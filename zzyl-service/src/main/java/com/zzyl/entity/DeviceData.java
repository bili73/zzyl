package com.zzyl.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备数据实体类
 */
@Data
public class DeviceData {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 物联网设备ID
     */
    private String iotId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 产品密钥
     */
    private String productKey;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 功能ID
     */
    private String functionId;

    /**
     * 接入位置
     */
    private String accessLocation;

    /**
     * 位置类型
     */
    private Integer locationType;

    /**
     * 物理位置类型
     */
    private Integer physicalLocationType;

    /**
     * 设备描述
     */
    private String deviceDescription;

    /**
     * 数据值
     */
    private String dataValue;

    /**
     * 告警时间
     */
    private LocalDateTime alarmTime;

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
