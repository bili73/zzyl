package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.DeviceData;
import com.zzyl.mapper.DeviceDataMapper;
import com.zzyl.service.DeviceDataService;
import com.zzyl.vo.DeviceDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备数据Service实现类
 */
@Service
public class DeviceDataServiceImpl implements DeviceDataService {

    @Autowired
    private DeviceDataMapper deviceDataMapper;

    @Override
    public List<DeviceDataVo> getAll() {
        List<DeviceData> deviceDataList = deviceDataMapper.selectAll();
        return deviceDataList.stream()
                .map(deviceData -> BeanUtil.toBean(deviceData, DeviceDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public PageResponse<DeviceDataVo> getByPage(String iotId, String deviceName, String functionId,
                                                LocalDateTime startTime, LocalDateTime endTime,
                                                Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<DeviceData> deviceDataList = (Page<DeviceData>) deviceDataMapper.selectByPage(iotId, deviceName, functionId, startTime, endTime);

        List<DeviceDataVo> deviceDataVos = deviceDataList.stream()
                .map(deviceData -> BeanUtil.toBean(deviceData, DeviceDataVo.class))
                .collect(java.util.stream.Collectors.toList());

        PageResponse<DeviceDataVo> pageResponse = new PageResponse<>();
        pageResponse.setRecords(deviceDataVos);
        pageResponse.setTotal(deviceDataList.getTotal());
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPages(Long.valueOf(deviceDataList.getPages()));

        return pageResponse;
    }

    @Override
    public DeviceDataVo getById(Long id) {
        DeviceData deviceData = deviceDataMapper.selectById(id);
        return deviceData != null ? BeanUtil.toBean(deviceData, DeviceDataVo.class) : null;
    }

    @Override
    public List<DeviceDataVo> getByIotId(String iotId) {
        List<DeviceData> deviceDataList = deviceDataMapper.selectByIotId(iotId);
        return deviceDataList.stream()
                .map(deviceData -> BeanUtil.toBean(deviceData, DeviceDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<DeviceDataVo> getByTimeRange(String iotId, LocalDateTime startTime, LocalDateTime endTime) {
        List<DeviceData> deviceDataList = deviceDataMapper.selectByTimeRange(iotId, startTime, endTime);
        return deviceDataList.stream()
                .map(deviceData -> BeanUtil.toBean(deviceData, DeviceDataVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    @Transactional
    public void add(DeviceData deviceData) {
        deviceData.setCreateTime(LocalDateTime.now());
        deviceData.setUpdateTime(LocalDateTime.now());
        deviceDataMapper.insert(deviceData);
    }

    @Override
    @Transactional
    public void addBatch(List<DeviceData> deviceDataList) {
        if (deviceDataList != null && !deviceDataList.isEmpty()) {
            deviceDataList.forEach(deviceData -> {
                deviceData.setCreateTime(LocalDateTime.now());
                deviceData.setUpdateTime(LocalDateTime.now());
            });
            deviceDataMapper.insertBatch(deviceDataList);
        }
    }

    @Override
    @Transactional
    public void update(DeviceData deviceData) {
        deviceData.setUpdateTime(LocalDateTime.now());
        deviceDataMapper.update(deviceData);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        deviceDataMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        deviceDataMapper.deleteByIds(ids);
    }
}
