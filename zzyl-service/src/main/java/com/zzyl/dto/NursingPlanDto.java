package com.zzyl.dto;

import com.zzyl.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 护理计划数据传输对象
 */
@Data
public class NursingPlanDto extends BaseDto {

    /**
     * 计划名称
     */
    @ApiModelProperty(value = "计划名称")
    private String name;

    /**
     * 护理等级ID
     */
    @ApiModelProperty(value = "护理等级ID")
    private Long nursingLevelId;

    /**
     * 照护对象（老人ID）
     */
    @ApiModelProperty(value = "照护对象（老人ID）")
    private Long elderId;

    /**
     * 计划开始时间
     */
    @ApiModelProperty(value = "计划开始时间")
    private LocalDateTime startTime;

    /**
     * 计划结束时间
     */
    @ApiModelProperty(value = "计划结束时间")
    private LocalDateTime endTime;

    /**
     * 状态（0：禁用，1：启用）
     */
    @ApiModelProperty(value = "护理计划状态（0：禁用，1：启用）")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 护理项目ID列表
     */
    @ApiModelProperty(value = "护理项目ID列表")
    private List<Long> nursingProjectIds;
}