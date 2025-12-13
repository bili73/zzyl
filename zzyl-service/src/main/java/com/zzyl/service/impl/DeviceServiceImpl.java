package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.Device;
import com.zzyl.mapper.DeviceMapper;
import com.zzyl.service.DeviceService;
import com.zzyl.vo.DeviceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备Service实现类
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<DeviceVo> getAll() {
        List<Device> devices = deviceMapper.selectAll();
        return devices.stream()
                .map(device -> BeanUtil.toBean(device, DeviceVo.class))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public PageResponse<DeviceVo> getByPage(String deviceName, String iotId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Device> devices = (Page<Device>) deviceMapper.selectByPage(deviceName, iotId);

        List<DeviceVo> deviceVos = devices.stream()
                .map(device -> BeanUtil.toBean(device, DeviceVo.class))
                .collect(java.util.stream.Collectors.toList());

        PageResponse<DeviceVo> pageResponse = new PageResponse<>();
        pageResponse.setRecords(deviceVos);
        pageResponse.setTotal(devices.getTotal());
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);
        pageResponse.setPages(Long.valueOf(devices.getPages()));

        return pageResponse;
    }

    @Override
    public DeviceVo getById(Long id) {
        Device device = deviceMapper.selectById(id);
        return device != null ? BeanUtil.toBean(device, DeviceVo.class) : null;
    }

    @Override
    public Device getByIotId(String iotId) {
        return deviceMapper.selectByIotId(iotId);
    }

    @Override
    @Transactional
    public void add(Device device) {
        device.setCreateTime(LocalDateTime.now());
        device.setUpdateTime(LocalDateTime.now());
        deviceMapper.insert(device);
    }

    @Override
    @Transactional
    public void update(Device device) {
        device.setUpdateTime(LocalDateTime.now());
        deviceMapper.update(device);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        deviceMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        deviceMapper.deleteByIds(ids);
    }
}
