package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nursing/plan")
@Api(tags = "护理计划管理接口")
public class NursingPlanController extends BaseController {

    @Autowired
    private NursingPlanService nursingPlanService;

    @GetMapping
    @ApiOperation(value = "获取所有护理计划", notes = "获取所有启用的护理计划信息")
    public ResponseResult<List<NursingPlanVo>> getAll() {
        List<NursingPlanVo> nursingPlans = nursingPlanService.getAll();
        return success(nursingPlans);
    }

    @GetMapping("/search")
    @ApiOperation(value = "分页查询护理计划", notes = "根据名称和状态分页查询护理计划")
    public ResponseResult<PageResponse<NursingPlanVo>> getByPage(
            @ApiParam(value = "护理计划名称") @RequestParam(required = false) String name,
            @ApiParam(value = "状态（0：禁用，1：启用）") @RequestParam(required = false) Integer status,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<NursingPlanVo> pageResponse = nursingPlanService.getByPage(name, status, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询护理计划", notes = "根据护理计划ID查询详细信息")
    public ResponseResult<NursingPlanVo> getById(
            @ApiParam(value = "护理计划ID", required = true) @PathVariable("id") Long id) {
        NursingPlanVo nursingPlanVo = nursingPlanService.getById(id);
        return success(nursingPlanVo);
    }

    @PostMapping
    @ApiOperation(value = "新增护理计划", notes = "创建新的护理计划")
    public ResponseResult addNursingPlan(@RequestBody NursingPlanDto nursingPlanDto) {
        nursingPlanService.add(nursingPlanDto);
        return success("新增护理计划成功");
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改护理计划", notes = "更新护理计划信息")
    public ResponseResult updateNursingPlan(
            @ApiParam(value = "护理计划ID", required = true) @PathVariable("id") Long id,
            @RequestBody NursingPlanDto nursingPlanDto) {
        nursingPlanDto.setId(id);
        nursingPlanService.update(nursingPlanDto);
        return success("修改护理计划成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除护理计划", notes = "根据ID删除护理计划")
    public ResponseResult deleteNursingPlan(
            @ApiParam(value = "护理计划ID", required = true) @PathVariable("id") Long id) {
        nursingPlanService.delete(id);
        return success("删除护理计划成功");
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation(value = "修改护理计划状态", notes = "启用或禁用护理计划")
    public ResponseResult updateStatus(
            @ApiParam(value = "护理计划ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "状态（0：禁用，1：启用）", required = true) @PathVariable("status") Integer status) {
        nursingPlanService.updateStatus(id, status);
        return success("状态修改成功");
    }
}