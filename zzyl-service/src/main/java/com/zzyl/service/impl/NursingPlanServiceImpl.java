package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProject;
import com.zzyl.entity.NursingLevel;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.mapper.NursingLevelMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.NursingPlanVo;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NursingPlanServiceImpl implements NursingPlanService {
    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    @Autowired
    private NursingLevelMapper nursingLevelMapper;

    @Override
    public List<NursingPlanVo> getAll() {
        List<NursingPlan> nursingPlans = nursingPlanMapper.selectAll();
        // 手动转换，处理字段映射
        return nursingPlans.stream().map(plan -> {
            NursingPlanVo vo = BeanUtil.toBean(plan, NursingPlanVo.class);
            vo.setPlanName(plan.getName()); // 手动映射 name -> planName
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public PageResponse<NursingPlanVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingPlan> nursingPlans = nursingPlanMapper.selectByPage(name, status);

        // 手动转换，处理字段映射
        List<NursingPlanVo> nursingPlanVos = nursingPlans.stream().map(plan -> {
            NursingPlanVo vo = BeanUtil.toBean(plan, NursingPlanVo.class);
            // 现在plan.getName()应该能正确获取到数据库中的plan_name值
            vo.setPlanName(plan.getName());
            return vo;
        }).collect(Collectors.toList());

        PageResponse<NursingPlanVo> planVoPageResponse = new PageResponse<>();
        planVoPageResponse.setRecords(nursingPlanVos);
        planVoPageResponse.setTotal(nursingPlans.getTotal());
        planVoPageResponse.setPage(pageNum);
        planVoPageResponse.setPageSize(pageSize);
        planVoPageResponse.setPages(Long.valueOf(nursingPlans.getPages()));

        return planVoPageResponse;
    }

    @Override
    public NursingPlanVo getById(Long id) {
        NursingPlan nursingPlan = nursingPlanMapper.selectById(id);
        NursingPlanVo nursingPlanVo = BeanUtil.toBean(nursingPlan, NursingPlanVo.class);
        nursingPlanVo.setPlanName(nursingPlan.getName()); // 手动映射 name -> planName

        // 查询关联的护理项目及其执行参数
        List<NursingProjectPlanDto> projectPlans = nursingPlanMapper.selectProjectPlanDetailsByPlanId(id);
        if (projectPlans != null && !projectPlans.isEmpty()) {
            // 获取护理项目基本信息
            List<NursingProjectVo> nursingProjects = projectPlans.stream()
                    .map(planProject -> {
                        NursingProject project = nursingProjectMapper.selectById(planProject.getProjectId());
                        if (project == null) {
                            return null;
                        }
                        NursingProjectVo projectVo = BeanUtil.toBean(project, NursingProjectVo.class);
                        return projectVo;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            nursingPlanVo.setNursingProjects(nursingProjects);

            // 设置 projectPlans 字段用于前端回显，使用从数据库查询的真实数据
            nursingPlanVo.setProjectPlans(projectPlans);
        }

        return nursingPlanVo;
    }

    @Override
    @Transactional
    public void add(NursingPlanDto nursingPlanDto) {
        NursingPlan nursingPlan = new NursingPlan();
        BeanUtils.copyProperties(nursingPlanDto, nursingPlan);
        // 设置公共字段
        nursingPlan.setCreateBy(UserThreadLocal.getMgtUserId());
        nursingPlan.setCreateTime(LocalDateTime.now());
        nursingPlan.setUpdateTime(LocalDateTime.now());
        nursingPlan.setStatus(1); // 默认启用状态

        nursingPlanMapper.insert(nursingPlan);

        // 保存关联关系 - 使用前端传递的完整数据
        if (nursingPlanDto.getProjectPlans() != null && !nursingPlanDto.getProjectPlans().isEmpty()) {
            for (NursingProjectPlanDto projectPlan : nursingPlanDto.getProjectPlans()) {
                if (projectPlan.getProjectId() != null) {
                    // 设置默认值，如果前端没有传递的话
                    String executeTime = projectPlan.getExecuteTime() != null ? projectPlan.getExecuteTime() : "08:00";
                    Integer executeCycle = projectPlan.getExecuteCycle() != null ? projectPlan.getExecuteCycle() : 1;
                    Integer executeFrequency = projectPlan.getExecuteFrequency() != null ? projectPlan.getExecuteFrequency() : 1;

                    nursingPlanMapper.insertPlanProject(
                        nursingPlan.getId(),
                        projectPlan.getProjectId(),
                        UserThreadLocal.getMgtUserId(),
                        executeTime,
                        executeCycle,
                        executeFrequency
                    );
                }
            }
        }
    }

    @Override
    @Transactional
    public void update(NursingPlanDto nursingPlanDto) {
        NursingPlan nursingPlan = new NursingPlan();
        BeanUtils.copyProperties(nursingPlanDto, nursingPlan);
        // 设置公共字段
        nursingPlan.setUpdateBy(1L);
        nursingPlan.setUpdateTime(LocalDateTime.now());

        nursingPlanMapper.update(nursingPlan);

        // 删除原有的关联关系
        nursingPlanMapper.deletePlanProjectByPlanId(nursingPlanDto.getId());

        // 重新插入护理计划项目关联关系 - 使用前端传递的完整数据
        if (nursingPlanDto.getProjectPlans() != null && !nursingPlanDto.getProjectPlans().isEmpty()) {
            for (NursingProjectPlanDto projectPlan : nursingPlanDto.getProjectPlans()) {
                if (projectPlan.getProjectId() != null) {
                    // 设置默认值，如果前端没有传递的话
                    String executeTime = projectPlan.getExecuteTime() != null ? projectPlan.getExecuteTime() : "08:00";
                    Integer executeCycle = projectPlan.getExecuteCycle() != null ? projectPlan.getExecuteCycle() : 1;
                    Integer executeFrequency = projectPlan.getExecuteFrequency() != null ? projectPlan.getExecuteFrequency() : 1;

                    nursingPlanMapper.insertPlanProject(
                        nursingPlanDto.getId(),
                        projectPlan.getProjectId(),
                        UserThreadLocal.getMgtUserId(),
                        executeTime,
                        executeCycle,
                        executeFrequency
                    );
                }
            }
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 检查护理计划是否存在
        NursingPlan plan = nursingPlanMapper.selectById(id);
        if (plan == null) {
            throw new RuntimeException("护理计划不存在");
        }

        // 检查护理计划状态，只有禁用状态才能删除
        if (plan.getStatus() != 0) {
            throw new RuntimeException("只有禁用状态的护理计划才能删除");
        }

        // 检查护理计划是否有关联的护理等级
        if (plan.getName() != null) {
            // 查询是否有护理等级关联到此计划
            List<NursingLevel> levels = nursingLevelMapper.selectByPlanId(id);
            if (levels != null && !levels.isEmpty()) {
                throw new RuntimeException("该护理计划已关联护理等级，无法删除");
            }
        }

        // 删除护理计划项目关联关系（允许删除关联了项目的计划）
        nursingPlanMapper.deletePlanProjectByPlanId(id);
        // 删除护理计划
        nursingPlanMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        NursingPlan nursingPlan = new NursingPlan();
        nursingPlan.setId(id);
        nursingPlan.setStatus(status);
        nursingPlan.setUpdateBy(1L);
        nursingPlan.setUpdateTime(LocalDateTime.now());
        nursingPlanMapper.update(nursingPlan);
    }
}