package com.zzyl.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 设备视图对象
 */
@Data
public class DeviceVo {

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
     * 绑定位置
     */
    @ApiModelProperty(value = "绑定位置")
    private String bindingLocation;

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
     * 设备描述
     */
    @ApiModelProperty(value = "设备描述")
    private String deviceDescription;

    /**
     * 是否有门禁 0:否 1:是
     */
    @ApiModelProperty(value = "是否有门禁（0：否，1：是）")
    private Integer haveEntranceGuard;

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
