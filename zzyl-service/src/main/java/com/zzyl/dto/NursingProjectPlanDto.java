package com.zzyl.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 护理计划项目关联DTO
 */
@Getter
@Setter
public class NursingProjectPlanDto {

    /**
     * 护理项目ID
     */
    private Long projectId;

    /**
     * 护理项目名称
     */
    private String projectName;

    /**
     * 执行周期（1-7对应周一到周日）
     */
    private Integer executeCycle;

    /**
     * 执行频次（次）
     */
    private Integer executeFrequency;

    /**
     * 执行时间
     */
    private String executeTime;
}