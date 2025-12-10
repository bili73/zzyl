package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingPlan;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingPlanService;
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

        // 查询关联的护理项目
        List<Long> projectIds = nursingPlanMapper.selectProjectIdsByPlanId(id);
        if (projectIds != null && !projectIds.isEmpty()) {
            List<NursingProjectVo> nursingProjects = projectIds.stream()
                    .map(projectId -> {
                        NursingProject project = nursingProjectMapper.selectById(projectId);
                        if (project == null) {
                            return null;
                        }
                        return BeanUtil.toBean(project, NursingProjectVo.class);
                    })
                    .filter(Objects::nonNull) // 过滤掉null值
                    .collect(Collectors.toList());
            nursingPlanVo.setNursingProjects(nursingProjects);
            // 设置 projectPlans 字段用于前端回显
            nursingPlanVo.setProjectPlans(nursingProjects.stream()
                    .map(project -> {
                        if (project == null) {
                            return null;
                        }
                        NursingProjectPlanDto planProject = new NursingProjectPlanDto();
                        planProject.setProjectId(project.getId());
                        planProject.setProjectName(project.getName());
                        // 设置默认值，实际应该从数据库查询
                        planProject.setExecuteCycle(1);
                        planProject.setExecuteFrequency(1);
                        planProject.setExecuteTime("08:00");
                        return planProject;
                    })
                    .filter(Objects::nonNull) // 过滤掉null值
                    .collect(Collectors.toList()));
        }

        return nursingPlanVo;
    }

    @Override
    @Transactional
    public void add(NursingPlanDto nursingPlanDto) {
        NursingPlan nursingPlan = new NursingPlan();
        BeanUtils.copyProperties(nursingPlanDto, nursingPlan);
        // 设置公共字段
        nursingPlan.setCreateBy(1L);
        nursingPlan.setCreateTime(LocalDateTime.now());
        nursingPlan.setUpdateTime(LocalDateTime.now());

        nursingPlanMapper.insert(nursingPlan);

        // 插入护理计划项目关联关系
        if (nursingPlanDto.getNursingProjectIds() != null && !nursingPlanDto.getNursingProjectIds().isEmpty()) {
            for (Long projectId : nursingPlanDto.getNursingProjectIds()) {
                nursingPlanMapper.insertPlanProject(nursingPlan.getId(), projectId);
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

        // 重新插入护理计划项目关联关系
        if (nursingPlanDto.getNursingProjectIds() != null && !nursingPlanDto.getNursingProjectIds().isEmpty()) {
            for (Long projectId : nursingPlanDto.getNursingProjectIds()) {
                nursingPlanMapper.insertPlanProject(nursingPlanDto.getId(), projectId);
            }
        }
    }

    @Override
    public void delete(Long id) {
        // 删除护理计划项目关联关系
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