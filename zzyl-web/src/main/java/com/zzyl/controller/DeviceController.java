package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.entity.Device;
import com.zzyl.service.DeviceService;
import com.zzyl.vo.DeviceVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备管理Controller
 */
@RestController
@RequestMapping("/device")
@Api(tags = "设备管理接口")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    @ApiOperation(value = "获取所有设备", notes = "获取所有设备信息")
    public ResponseResult<List<DeviceVo>> getAll() {
        List<DeviceVo> devices = deviceService.getAll();
        return success(devices);
    }

    @GetMapping("/search")
    @ApiOperation(value = "分页查询设备", notes = "根据设备名称和物联网ID分页查询设备")
    public ResponseResult<PageResponse<DeviceVo>> getByPage(
            @ApiParam(value = "设备名称") @RequestParam(required = false) String deviceName,
            @ApiParam(value = "物联网设备ID") @RequestParam(required = false) String iotId,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<DeviceVo> pageResponse = deviceService.getByPage(deviceName, iotId, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询设备", notes = "根据设备ID查询详细信息")
    public ResponseResult<DeviceVo> getById(
            @ApiParam(value = "设备ID", required = true) @PathVariable("id") Long id) {
        DeviceVo deviceVo = deviceService.getById(id);
        return success(deviceVo);
    }

    @PostMapping
    @ApiOperation(value = "新增设备", notes = "创建新的设备")
    public ResponseResult addDevice(@RequestBody Device device) {
        deviceService.add(device);
        return success("新增设备成功");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改设备", notes = "更新设备信息")
    public ResponseResult updateDevice(
            @ApiParam(value = "设备ID", required = true) @PathVariable("id") Long id,
            @RequestBody Device device) {
        device.setId(id);
        deviceService.update(device);
        return success("修改设备成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除设备", notes = "根据ID删除设备")
    public ResponseResult deleteDevice(
            @ApiParam(value = "设备ID", required = true) @PathVariable("id") Long id) {
        deviceService.delete(id);
        return success("删除设备成功");
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除设备", notes = "根据ID列表批量删除设备")
    public ResponseResult deleteDevices(
            @ApiParam(value = "设备ID列表", required = true) @RequestBody List<Long> ids) {
        deviceService.deleteByIds(ids);
        return success("批量删除设备成功");
    }
}
