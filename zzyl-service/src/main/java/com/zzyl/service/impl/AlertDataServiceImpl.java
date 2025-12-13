package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.AlertData;
import com.zzyl.mapper.AlertDataMapper;
import com.zzyl.service.AlertDataService;
import com.zzyl.vo.AlertDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警数据Service实现类
 */
@Service
public class AlertDataServiceImpl implements AlertDataService {

    @Autowired
    private AlertDataMapper alertDataMapper;

    @Override
    public List<AlertDataVo> getAll() {
        List<AlertData> alertDataList = alertDataMapper.selectAll();
        return alertDataList.stream()
                .map(alertData -> BeanUtil.toBean(alertData, AlertDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public PageResponse<AlertDataVo> getByPage(String iotId, String deviceName, Long alertRuleId,
                                               Integer type, Integer status,
                                               LocalDateTime startTime, LocalDateTime endTime,
                                               Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<AlertData> alertDataList = (Page<AlertData>) alertDataMapper.selectByPage(iotId, deviceName, alertRuleId, type, status, startTime, endTime);

        List<AlertDataVo> alertDataVos = alertDataList.stream()
                .map(alertData -> BeanUtil.toBean(alertData, AlertDataVo.class))
                .collect(java.util.stream.Collectors.toList());

        PageResponse<AlertDataVo> pageResponse = new PageResponse<>();
        pageResponse.setRecords(alertDataVos);
        pageResponse.setTotal(alertDataList.getTotal());
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPages(Long.valueOf(alertDataList.getPages()));

        return pageResponse;
    }

    @Override
    public AlertDataVo getById(Long id) {
        AlertData alertData = alertDataMapper.selectById(id);
        return alertData != null ? BeanUtil.toBean(alertData, AlertDataVo.class) : null;
    }

    @Override
    public List<AlertDataVo> getByIotId(String iotId) {
        List<AlertData> alertDataList = alertDataMapper.selectByIotId(iotId);
        return alertDataList.stream()
                .map(alertData -> BeanUtil.toBean(alertData, AlertDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<AlertDataVo> getByAlertRuleId(Long alertRuleId) {
        List<AlertData> alertDataList = alertDataMapper.selectByAlertRuleId(alertRuleId);
        return alertDataList.stream()
                .map(alertData -> BeanUtil.toBean(alertData, AlertDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<AlertDataVo> getByTimeRange(String iotId, LocalDateTime startTime, LocalDateTime endTime) {
        List<AlertData> alertDataList = alertDataMapper.selectByTimeRange(iotId, startTime, endTime);
        return alertDataList.stream()
                .map(alertData -> BeanUtil.toBean(alertData, AlertDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public void add(AlertData alertData) {
        alertData.setCreateTime(LocalDateTime.now());
        alertData.setUpdateTime(LocalDateTime.now());
        alertDataMapper.insert(alertData);
    }

    @Override
    @Transactional
    public void addBatch(List<AlertData> alertDataList) {
        if (alertDataList != null && !alertDataList.isEmpty()) {
            alertDataList.forEach(alertData -> {
                alertData.setCreateTime(LocalDateTime.now());
                alertData.setUpdateTime(LocalDateTime.now());
            });
            alertDataMapper.insertBatch(alertDataList);
        }
    }

    @Override
    @Transactional
    public void update(AlertData alertData) {
        alertData.setUpdateTime(LocalDateTime.now());
        alertDataMapper.update(alertData);
    }

    @Override
    @Transactional
    public void processAlert(Long id, String processingResult, Long processorId, String processorName) {
        AlertData alertData = alertDataMapper.selectById(id);
        if (alertData != null) {
            alertData.setProcessingResult(processingResult);
            alertData.setProcessorId(processorId);
            alertData.setProcessorName(processorName);
            alertData.setProcessingTime(LocalDateTime.now());
            alertData.setStatus(1); // 设置为已处理状态
            alertData.setUpdateTime(LocalDateTime.now());
            alertDataMapper.update(alertData);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        alertDataMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        alertDataMapper.deleteByIds(ids);
    }
}
