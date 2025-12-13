package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.entity.AlertData;
import com.zzyl.service.AlertDataService;
import com.zzyl.vo.AlertDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警数据Controller
 */
@RestController
@RequestMapping("/alertData")
@Api(tags = "告警数据管理接口")
public class AlertDataController extends BaseController {

    @Autowired
    private AlertDataService alertDataService;

    @GetMapping
    @ApiOperation(value = "获取所有告警数据", notes = "获取所有告警数据信息")
    public ResponseResult<List<AlertDataVo>> getAll() {
        List<AlertDataVo> alertDataList = alertDataService.getAll();
        return success(alertDataList);
    }

    @GetMapping("/search")
    @ApiOperation(value = "分页查询告警数据", notes = "根据条件分页查询告警数据")
    public ResponseResult<PageResponse<AlertDataVo>> getByPage(
            @ApiParam(value = "物联网设备ID") @RequestParam(required = false) String iotId,
            @ApiParam(value = "设备名称") @RequestParam(required = false) String deviceName,
            @ApiParam(value = "告警规则ID") @RequestParam(required = false) Long alertRuleId,
            @ApiParam(value = "告警类型") @RequestParam(required = false) Integer type,
            @ApiParam(value = "告警状态") @RequestParam(required = false) Integer status,
            @ApiParam(value = "开始时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam(value = "结束时间") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<AlertDataVo> pageResponse = alertDataService.getByPage(iotId, deviceName, alertRuleId, type, status, startTime, endTime, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询告警数据", notes = "根据告警数据ID查询详细信息")
    public ResponseResult<AlertDataVo> getById(
            @ApiParam(value = "告警数据ID", required = true) @PathVariable("id") Long id) {
        AlertDataVo alertDataVo = alertDataService.getById(id);
        return success(alertDataVo);
    }

    @GetMapping("/iot/{iotId}")
    @ApiOperation(value = "根据物联网ID查询告警数据", notes = "根据物联网设备ID查询告警数据")
    public ResponseResult<List<AlertDataVo>> getByIotId(
            @ApiParam(value = "物联网设备ID", required = true) @PathVariable("iotId") String iotId) {
        List<AlertDataVo> alertDataList = alertDataService.getByIotId(iotId);
        return success(alertDataList);
    }

    @GetMapping("/rule/{alertRuleId}")
    @ApiOperation(value = "根据告警规则ID查询告警数据", notes = "根据告警规则ID查询告警数据")
    public ResponseResult<List<AlertDataVo>> getByAlertRuleId(
            @ApiParam(value = "告警规则ID", required = true) @PathVariable("alertRuleId") Long alertRuleId) {
        List<AlertDataVo> alertDataList = alertDataService.getByAlertRuleId(alertRuleId);
        return success(alertDataList);
    }

    @GetMapping("/timeRange")
    @ApiOperation(value = "根据时间范围查询告警数据", notes = "根据物联网设备ID和时间范围查询告警数据")
    public ResponseResult<List<AlertDataVo>> getByTimeRange(
            @ApiParam(value = "物联网设备ID", required = true) @RequestParam String iotId,
            @ApiParam(value = "开始时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @ApiParam(value = "结束时间", required = true) @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {

        List<AlertDataVo> alertDataList = alertDataService.getByTimeRange(iotId, startTime, endTime);
        return success(alertDataList);
    }

    @PostMapping
    @ApiOperation(value = "新增告警数据", notes = "创建告警数据")
    public ResponseResult addAlertData(@RequestBody AlertData alertData) {
        alertDataService.add(alertData);
        return success("新增告警数据成功");
    }

    @PostMapping("/batch")
    @ApiOperation(value = "批量新增告警数据", notes = "批量创建告警数据")
    public ResponseResult addAlertDataBatch(@RequestBody List<AlertData> alertDataList) {
        alertDataService.addBatch(alertDataList);
        return success("批量新增告警数据成功");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改告警数据", notes = "更新告警数据信息")
    public ResponseResult updateAlertData(
            @ApiParam(value = "告警数据ID", required = true) @PathVariable("id") Long id,
            @RequestBody AlertData alertData) {
        alertData.setId(id);
        alertDataService.update(alertData);
        return success("修改告警数据成功");
    }

    @PutMapping("/{id}/process")
    @ApiOperation(value = "处理告警", notes = "处理告警数据")
    public ResponseResult processAlert(
            @ApiParam(value = "告警数据ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "处理结果", required = true) @RequestParam String processingResult,
            @ApiParam(value = "处理人ID", required = true) @RequestParam Long processorId,
            @ApiParam(value = "处理人姓名", required = true) @RequestParam String processorName) {
        alertDataService.processAlert(id, processingResult, processorId, processorName);
        return success("处理告警成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除告警数据", notes = "根据ID删除告警数据")
    public ResponseResult deleteAlertData(
            @ApiParam(value = "告警数据ID", required = true) @PathVariable("id") Long id) {
        alertDataService.delete(id);
        return success("删除告警数据成功");
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除告警数据", notes = "根据ID列表批量删除告警数据")
    public ResponseResult deleteAlertDataBatch(
            @ApiParam(value = "告警数据ID列表", required = true) @RequestBody List<Long> ids) {
        alertDataService.deleteByIds(ids);
        return success("批量删除告警数据成功");
    }
}
