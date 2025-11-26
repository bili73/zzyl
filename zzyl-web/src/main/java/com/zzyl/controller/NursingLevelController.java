package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nursingLevel")
@Api(tags = "护理等级管理接口")
public class NursingLevelController extends BaseController {

    @Autowired
    private NursingLevelService nursingLevelService;

    @GetMapping("/listAll")
    @ApiOperation(value = "获取所有护理等级", notes = "获取所有启用的护理等级信息")
    public ResponseResult<List<NursingLevelVo>> getAll() {
        List<NursingLevelVo> nursingLevels = nursingLevelService.getAll();
        return success(nursingLevels);
    }

    @GetMapping("/listByPage")
    @ApiOperation(value = "分页查询护理等级", notes = "根据名称和状态分页查询护理等级")
    public ResponseResult<PageResponse<NursingLevelVo>> getByPage(
            @ApiParam(value = "护理等级名称") @RequestParam(required = false) String name,
            @ApiParam(value = "状态（0：禁用，1：启用）") @RequestParam(required = false) Integer status,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<NursingLevelVo> pageResponse = nursingLevelService.getByPage(name, status, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询护理等级", notes = "根据护理等级ID查询详细信息")
    public ResponseResult<NursingLevelVo> getById(
            @ApiParam(value = "护理等级ID", required = true) @PathVariable("id") Long id) {
        NursingLevelVo nursingLevelVo = nursingLevelService.getById(id);
        return success(nursingLevelVo);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "新增护理等级", notes = "创建新的护理等级")
    public ResponseResult addNursingLevel(@RequestBody NursingLevelDto nursingLevelDto) {
        nursingLevelService.add(nursingLevelDto);
        return success("新增护理等级成功");
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改护理等级", notes = "更新护理等级信息")
    public ResponseResult updateNursingLevel(@RequestBody NursingLevelDto nursingLevelDto) {
        nursingLevelService.update(nursingLevelDto);
        return success("修改护理等级成功");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除护理等级", notes = "根据ID删除护理等级")
    public ResponseResult deleteNursingLevel(
            @ApiParam(value = "护理等级ID", required = true) @PathVariable("id") Long id) {
        nursingLevelService.delete(id);
        return success("删除护理等级成功");
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation(value = "修改护理等级状态", notes = "启用或禁用护理等级")
    public ResponseResult updateStatus(
            @ApiParam(value = "护理等级ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "状态（0：禁用，1：启用）", required = true) @PathVariable("status") Integer status) {
        nursingLevelService.updateStatus(id, status);
        return success("状态修改成功");
    }
}