package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.AlertRule;
import com.zzyl.mapper.AlertRuleMapper;
import com.zzyl.service.AlertRuleService;
import com.zzyl.vo.AlertRuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警规则Service实现类
 */
@Service
public class AlertRuleServiceImpl implements AlertRuleService {

    @Autowired
    private AlertRuleMapper alertRuleMapper;

    @Override
    public List<AlertRuleVo> getAll() {
        List<AlertRule> alertRules = alertRuleMapper.selectAll();
        return alertRules.stream()
                .map(alertRule -> BeanUtil.toBean(alertRule, AlertRuleVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public PageResponse<AlertRuleVo> getByPage(String iotId, String productKey, String alertRuleName,
                                               String functionName, Integer alertDataType, Integer status,
                                               Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<AlertRule> alertRules = (Page<AlertRule>) alertRuleMapper.selectByPage(iotId, productKey, alertRuleName, functionName, alertDataType, status);

        List<AlertRuleVo> alertRuleVos = alertRules.stream()
                .map(alertRule -> BeanUtil.toBean(alertRule, AlertRuleVo.class))
                .collect(java.util.stream.Collectors.toList());

        PageResponse<AlertRuleVo> pageResponse = new PageResponse<>();
        pageResponse.setRecords(alertRuleVos);
        pageResponse.setTotal(alertRules.getTotal());
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPages(Long.valueOf(alertRules.getPages()));

        return pageResponse;
    }

    @Override
    public AlertRuleVo getById(Long id) {
        AlertRule alertRule = alertRuleMapper.selectById(id);
        return alertRule != null ? BeanUtil.toBean(alertRule, AlertRuleVo.class) : null;
    }

    @Override
    public List<AlertRuleVo> getByIotId(String iotId) {
        List<AlertRule> alertRules = alertRuleMapper.selectByIotId(iotId);
        return alertRules.stream()
                .map(alertRule -> BeanUtil.toBean(alertRule, AlertRuleVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<AlertRuleVo> getByProductKey(String productKey) {
        List<AlertRule> alertRules = alertRuleMapper.selectByProductKey(productKey);
        return alertRules.stream()
                .map(alertRule -> BeanUtil.toBean(alertRule, AlertRuleVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public void add(AlertRule alertRule) {
        alertRule.setCreateTime(LocalDateTime.now());
        alertRule.setUpdateTime(LocalDateTime.now());
        alertRuleMapper.insert(alertRule);
    }

    @Override
    @Transactional
    public void update(AlertRule alertRule) {
        alertRule.setUpdateTime(LocalDateTime.now());
        alertRuleMapper.update(alertRule);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        AlertRule alertRule = alertRuleMapper.selectById(id);
        if (alertRule != null) {
            alertRule.setStatus(status);
            alertRule.setUpdateTime(LocalDateTime.now());
            alertRuleMapper.update(alertRule);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        alertRuleMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        alertRuleMapper.deleteByIds(ids);
    }
}
