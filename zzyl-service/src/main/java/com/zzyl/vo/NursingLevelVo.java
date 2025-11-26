package com.zzyl.vo;

import com.zzyl.base.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 护理等级视图对象
 */
@Data
public class NursingLevelVo extends BaseVo {

    /**
     * 护理等级名称
     */
    @ApiModelProperty(value = "护理等级名称")
    private String name;

    /**
     * 护理等级描述
     */
    @ApiModelProperty(value = "护理等级描述")
    private String description;

    /**
     * 护理费用（元/月）
     */
    @ApiModelProperty(value = "护理费用（元/月）")
    private BigDecimal fee;

    /**
     * 护理等级状态（0：禁用，1：启用）
     */
    @ApiModelProperty(value = "护理等级状态（0：禁用，1：启用）")
    private Integer status;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sortNo;

    /**
     * 关联的护理计划名称
     */
    @ApiModelProperty(value = "关联的护理计划名称")
    private String planName;

    /**
     * 关联的护理计划ID
     */
    @ApiModelProperty(value = "关联的护理计划ID")
    private Long planId;

    /**
     * 护理等级绑到计划的个数
     */
    @ApiModelProperty(value = "护理等级绑到计划的个数")
    private Integer count;
}