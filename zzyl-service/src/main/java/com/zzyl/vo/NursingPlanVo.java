package com.zzyl.vo;

import com.zzyl.base.BaseVo;
import com.zzyl.dto.NursingProjectPlanDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 护理计划视图对象
 */
@Data
public class NursingPlanVo extends BaseVo {

    /**
     * 护理计划名称
     */
    @ApiModelProperty(value = "护理计划名称")
    private String planName;

    /**
     * 护理等级ID
     */
    @ApiModelProperty(value = "护理等级ID")
    private Long nursingLevelId;

    /**
     * 护理等级名称
     */
    @ApiModelProperty(value = "护理等级名称")
    private String nursingLevelName;

    /**
     * 照护对象（老人ID）
     */
    @ApiModelProperty(value = "照护对象（老人ID）")
    private Long elderId;

    /**
     * 老人姓名
     */
    @ApiModelProperty(value = "老人姓名")
    private String elderName;

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
     * 护理项目列表
     */
    @ApiModelProperty(value = "护理项目列表")
    private List<NursingProjectVo> nursingProjects;

    /**
     * 护理计划项目关联列表（用于前端回显）
     */
    @ApiModelProperty(value = "护理计划项目关联列表")
    private List<NursingProjectPlanDto> projectPlans;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String creator;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}