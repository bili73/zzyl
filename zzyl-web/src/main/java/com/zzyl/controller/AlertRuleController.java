package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.entity.AlertRule;
import com.zzyl.service.AlertRuleService;
import com.zzyl.vo.AlertRuleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 告警规则Controller
 */
@RestController
@RequestMapping("/alertRule")
@Api(tags = "告警规则管理接口")
public class AlertRuleController extends BaseController {

    @Autowired
    private AlertRuleService alertRuleService;

    @GetMapping
    @ApiOperation(value = "获取所有告警规则", notes = "获取所有告警规则信息")
    public ResponseResult<List<AlertRuleVo>> getAll() {
        List<AlertRuleVo> alertRules = alertRuleService.getAll();
        return success(alertRules);
    }

    @GetMapping("/search")
    @ApiOperation(value = "分页查询告警规则", notes = "根据条件分页查询告警规则")
    public ResponseResult<PageResponse<AlertRuleVo>> getByPage(
            @ApiParam(value = "物联网设备ID") @RequestParam(required = false) String iotId,
            @ApiParam(value = "产品密钥") @RequestParam(required = false) String productKey,
            @ApiParam(value = "告警规则名称") @RequestParam(required = false) String alertRuleName,
            @ApiParam(value = "功能名称") @RequestParam(required = false) String functionName,
            @ApiParam(value = "告警数据类型") @RequestParam(required = false) Integer alertDataType,
            @ApiParam(value = "状态") @RequestParam(required = false) Integer status,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<AlertRuleVo> pageResponse = alertRuleService.getByPage(iotId, productKey, alertRuleName, functionName, alertDataType, status, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询告警规则", notes = "根据告警规则ID查询详细信息")
    public ResponseResult<AlertRuleVo> getById(
            @ApiParam(value = "告警规则ID", required = true) @PathVariable("id") Long id) {
        AlertRuleVo alertRuleVo = alertRuleService.getById(id);
        return success(alertRuleVo);
    }

    @GetMapping("/iot/{iotId}")
    @ApiOperation(value = "根据物联网ID查询告警规则", notes = "根据物联网设备ID查询告警规则")
    public ResponseResult<List<AlertRuleVo>> getByIotId(
            @ApiParam(value = "物联网设备ID", required = true) @PathVariable("iotId") String iotId) {
        List<AlertRuleVo> alertRules = alertRuleService.getByIotId(iotId);
        return success(alertRules);
    }

    @GetMapping("/product/{productKey}")
    @ApiOperation(value = "根据产品密钥查询告警规则", notes = "根据产品密钥查询告警规则")
    public ResponseResult<List<AlertRuleVo>> getByProductKey(
            @ApiParam(value = "产品密钥", required = true) @PathVariable("productKey") String productKey) {
        List<AlertRuleVo> alertRules = alertRuleService.getByProductKey(productKey);
        return success(alertRules);
    }

    @PostMapping
    @ApiOperation(value = "新增告警规则", notes = "创建新的告警规则")
    public ResponseResult addAlertRule(@RequestBody AlertRule alertRule) {
        alertRuleService.add(alertRule);
        return success("新增告警规则成功");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改告警规则", notes = "更新告警规则信息")
    public ResponseResult updateAlertRule(
            @ApiParam(value = "告警规则ID", required = true) @PathVariable("id") Long id,
            @RequestBody AlertRule alertRule) {
        alertRule.setId(id);
        alertRuleService.update(alertRule);
        return success("修改告警规则成功");
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation(value = "修改告警规则状态", notes = "启用或禁用告警规则")
    public ResponseResult updateStatus(
            @ApiParam(value = "告警规则ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "状态（0：禁用，1：启用）", required = true) @PathVariable("status") Integer status) {
        alertRuleService.updateStatus(id, status);
        return success("修改告警规则状态成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除告警规则", notes = "根据ID删除告警规则")
    public ResponseResult deleteAlertRule(
            @ApiParam(value = "告警规则ID", required = true) @PathVariable("id") Long id) {
        alertRuleService.delete(id);
        return success("删除告警规则成功");
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除告警规则", notes = "根据ID列表批量删除告警规则")
    public ResponseResult deleteAlertRules(
            @ApiParam(value = "告警规则ID列表", required = true) @RequestBody List<Long> ids) {
        alertRuleService.deleteByIds(ids);
        return success("批量删除告警规则成功");
    }
}
