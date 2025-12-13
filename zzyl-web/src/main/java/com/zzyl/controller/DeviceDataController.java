package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.entity.DeviceData;
import com.zzyl.service.DeviceDataService;
import com.zzyl.vo.DeviceDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备数据Controller
 */
@RestController
@RequestMapping("/deviceData")
@Api(tags = "设备数据管理接口")
public class DeviceDataController extends BaseController {

    @Autowired
    private DeviceDataService deviceDataService;

    @GetMapping
    @ApiOperation(value = "获取所有设备数据", notes = "获取所有设备数据信息")
    public ResponseResult<List<DeviceDataVo>> getAll() {
        List<DeviceDataVo> deviceDataList = deviceDataService.getAll();
        return success(deviceDataList);
    }

    @GetMapping("/search")
    @ApiOperation(value = "分页查询设备数据", notes = "根据条件分页查询设备数据")
    public ResponseResult<PageResponse<DeviceDataVo>> getByPage(
            @ApiParam(value = "物联网设备ID") @RequestParam(required = false) String iotId,
            @ApiParam(value = "设备名称") @RequestParam(required = false) String deviceName,
            @ApiParam(value = "功能ID") @RequestParam(required = false) String functionId,
            @ApiParam(value = "开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam(value = "结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<DeviceDataVo> pageResponse = deviceDataService.getByPage(iotId, deviceName, functionId, startTime, endTime, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询设备数据", notes = "根据设备数据ID查询详细信息")
    public ResponseResult<DeviceDataVo> getById(
            @ApiParam(value = "设备数据ID", required = true) @PathVariable("id") Long id) {
        DeviceDataVo deviceDataVo = deviceDataService.getById(id);
        return success(deviceDataVo);
    }

    @GetMapping("/iot/{iotId}")
    @ApiOperation(value = "根据物联网ID查询设备数据", notes = "根据物联网设备ID查询设备数据")
    public ResponseResult<List<DeviceDataVo>> getByIotId(
            @ApiParam(value = "物联网设备ID", required = true) @PathVariable("iotId") String iotId) {
        List<DeviceDataVo> deviceDataList = deviceDataService.getByIotId(iotId);
        return success(deviceDataList);
    }

    @GetMapping("/timeRange")
    @ApiOperation(value = "根据时间范围查询设备数据", notes = "根据物联网设备ID和时间范围查询设备数据")
    public ResponseResult<List<DeviceDataVo>> getByTimeRange(
            @ApiParam(value = "物联网设备ID", required = true) @RequestParam String iotId,
            @ApiParam(value = "开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {

        List<DeviceDataVo> deviceDataList = deviceDataService.getByTimeRange(iotId, startTime, endTime);
        return success(deviceDataList);
    }

    @PostMapping
    @ApiOperation(value = "新增设备数据", notes = "创建设备数据")
    public ResponseResult addDeviceData(@RequestBody DeviceData deviceData) {
        deviceDataService.add(deviceData);
        return success("新增设备数据成功");
    }

    @PostMapping("/batch")
    @ApiOperation(value = "批量新增设备数据", notes = "批量创建设备数据")
    public ResponseResult addDeviceDataBatch(@RequestBody List<DeviceData> deviceDataList) {
        deviceDataService.addBatch(deviceDataList);
        return success("批量新增设备数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改设备数据", notes = "更新设备数据信息")
    public ResponseResult updateDeviceData(
            @ApiParam(value = "设备数据ID", required = true) @PathVariable("id") Long id,
            @RequestBody DeviceData deviceData) {
        deviceData.setId(id);
        deviceDataService.update(deviceData);
        return success("修改设备数据成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除设备数据", notes = "根据ID删除设备数据")
    public ResponseResult deleteDeviceData(
            @ApiParam(value = "设备数据ID", required = true) @PathVariable("id") Long id) {
        deviceDataService.delete(id);
        return success("删除设备数据成功");
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除设备数据", notes = "根据ID列表批量删除设备数据")
    public ResponseResult deleteDeviceDataBatch(
            @ApiParam(value = "设备数据ID列表", required = true) @RequestBody List<Long> ids) {
        deviceDataService.deleteByIds(ids);
        return success("批量删除设备数据成功");
    }
}
