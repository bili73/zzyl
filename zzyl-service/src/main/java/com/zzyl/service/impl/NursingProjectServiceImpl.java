package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NursingProjectServiceImpl implements NursingProjectService {
    @Autowired
    private NursingProjectMapper nursingProjectMapper;
    @Override
    public PageResponse<NursingProjectVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingProject> nursingProjects = nursingProjectMapper.selectByPage(name, status);
        PageResponse<NursingProjectVo> projectVoPageResponse = PageResponse.of(nursingProjects, NursingProjectVo.class);
        return projectVoPageResponse;
    }

    @Override
    public void add(NursingProjectDto nursingProjectDTO) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDTO, nursingProject);
        // 设置公共字段
        nursingProject.setCreateBy(1L);
        nursingProject.setCreateTime(LocalDateTime.now());
        nursingProject.setUpdateTime(LocalDateTime.now());
        nursingProjectMapper.insert(nursingProject);
    }

    /**
     * 根据id查询护理项目
     * @param id
     * @return
     */
    @Override
    public NursingProjectVo getById(Long id) {
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        return BeanUtil.toBean(nursingProject, NursingProjectVo.class);
    }

    /**
     * 修改护理项目
     * @param nursingProjectDTO
     */
    @Override
    public void update(NursingProjectDto nursingProjectDTO) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDTO, nursingProject);
        // 设置公共字段
        nursingProject.setUpdateBy(1L);
        nursingProject.setUpdateTime(LocalDateTime.now());
        nursingProjectMapper.update(nursingProject);
    }

    /**
     * 删除护理项目（只能删除禁用状态的护理项目）
     * @param id
     */
    @Override
    public void delete(Long id) {
        // 先查询护理项目状态
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        if (nursingProject == null) {
            throw new RuntimeException("护理项目不存在");
        }

        // 检查状态：只能删除禁用状态（status=0）的护理项目
        if (nursingProject.getStatus() != null && nursingProject.getStatus().equals(0)) {
            nursingProjectMapper.deleteById(id);
        } else {
            throw new RuntimeException("只能删除禁用状态的护理项目，请先禁用该护理项目");
        }
    }

    /**
     * 修改护理项目状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
        NursingProject nursingProject = new NursingProject();
        nursingProject.setId(id);
        nursingProject.setStatus(status);
        nursingProject.setUpdateBy(1L);
        nursingProject.setUpdateTime(LocalDateTime.now());
        nursingProjectMapper.update(nursingProject);
    }

    /**
     * 小程序端查询启用的护理项目列表
     * @return
     */
    @Override
    public List<NursingProjectVo> listEnabledProjects() {
        List<NursingProject> projects = nursingProjectMapper.listByStatus(1); // 1表示启用状态
        return cn.hutool.core.bean.BeanUtil.copyToList(projects, NursingProjectVo.class);
    }
}
