package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.entity.NursingLevel;
import com.zzyl.mapper.NursingLevelMapper;
import com.zzyl.service.NursingLevelService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.NursingLevelVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NursingLevelServiceImpl implements NursingLevelService {
    @Autowired
    private NursingLevelMapper nursingLevelMapper;

    @Override
    public List<NursingLevelVo> getAll() {
        List<NursingLevel> nursingLevels = nursingLevelMapper.selectAll();
        return BeanUtil.copyToList(nursingLevels, NursingLevelVo.class);
    }

    @Override
    public PageResponse<NursingLevelVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<NursingLevel> nursingLevels = nursingLevelMapper.selectByPage(name, status);
        PageResponse<NursingLevelVo> levelVoPageResponse = PageResponse.of(nursingLevels, NursingLevelVo.class);
        return levelVoPageResponse;
    }

    @Override
    public NursingLevelVo getById(Long id) {
        NursingLevel nursingLevel = nursingLevelMapper.selectById(id);
        return BeanUtil.toBean(nursingLevel, NursingLevelVo.class);
    }

    @Override
    public void add(NursingLevelDto nursingLevelDto) {
        NursingLevel nursingLevel = new NursingLevel();
        BeanUtils.copyProperties(nursingLevelDto, nursingLevel);
        // 设置公共字段
        nursingLevel.setCreateBy(UserThreadLocal.getMgtUserId());
        nursingLevel.setCreateTime(LocalDateTime.now());
        nursingLevel.setUpdateTime(LocalDateTime.now());
        nursingLevelMapper.insert(nursingLevel);
    }

    @Override
    public void update(NursingLevelDto nursingLevelDto) {
        NursingLevel nursingLevel = new NursingLevel();
        BeanUtils.copyProperties(nursingLevelDto, nursingLevel);
        // 设置公共字段
        nursingLevel.setUpdateBy(UserThreadLocal.getMgtUserId());
        nursingLevel.setUpdateTime(LocalDateTime.now());
        nursingLevelMapper.update(nursingLevel);
    }

    @Override
    public void delete(Long id) {
        nursingLevelMapper.deleteById(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        NursingLevel nursingLevel = new NursingLevel();
        nursingLevel.setId(id);
        nursingLevel.setStatus(status);
        nursingLevel.setUpdateBy(UserThreadLocal.getMgtUserId());
        nursingLevel.setUpdateTime(LocalDateTime.now());
        nursingLevelMapper.update(nursingLevel);
    }
}