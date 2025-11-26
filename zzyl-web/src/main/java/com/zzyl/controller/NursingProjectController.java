package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nursing-project")
@Api(tags = "护理项目管理接口")
public class NursingProjectController extends BaseController {

    @Autowired
    private NursingProjectService nursingProjectService;

    @GetMapping("/all")
    @ApiOperation(value = "获取所有护理项目", notes = "获取所有护理项目信息")
    public ResponseResult<List<NursingProjectVo>> getAll() {
        // 调用分页查询但不传筛选条件，获取所有数据
        PageResponse<NursingProjectVo> allData = nursingProjectService.getByPage(null, null, 1, 1000);
        return success(allData.getRecords());
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询护理项目", notes = "根据名称和状态分页查询护理项目")
    public ResponseResult<PageResponse<NursingProjectVo>> getByPage(
            @ApiParam(value = "护理项目名称") @RequestParam(required = false) String name,
            @ApiParam(value = "状态（0：禁用，1：启用）") @RequestParam(required = false) Integer status,
            @ApiParam(value = "页码", required = true) @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页条数", required = true) @RequestParam(defaultValue = "10") Integer pageSize) {

        PageResponse<NursingProjectVo> pageResponse = nursingProjectService.getByPage(name, status, pageNum, pageSize);
        return success(pageResponse);
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增护理项目", notes = "创建新的护理项目")
    public ResponseResult addNursingProject(@RequestBody NursingProjectDto nursingProjectDto) {
        nursingProjectService.add(nursingProjectDto);
        return success("新增护理项目成功");
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查询护理项目", notes = "根据护理项目ID查询详细信息")
    public ResponseResult<NursingProjectVo> getById(
            @ApiParam(value = "护理项目ID", required = true) @PathVariable("id") Long id) {
        NursingProjectVo nursingProjectVo = nursingProjectService.getById(id);
        return success(nursingProjectVo);
    }

    @PutMapping("/update")
    @ApiOperation(value = "修改护理项目", notes = "更新护理项目信息")
    public ResponseResult updateNursingProject(@RequestBody NursingProjectDto nursingProjectDto) {
        nursingProjectService.update(nursingProjectDto);
        return success("修改护理项目成功");
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除护理项目", notes = "根据ID删除护理项目")
    public ResponseResult deleteNursingProject(
            @ApiParam(value = "护理项目ID", required = true) @PathVariable("id") Long id) {
        // 需要在Service中添加delete方法
        nursingProjectService.delete(id);
        return success("删除护理项目成功");
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation(value = "修改护理项目状态", notes = "启用或禁用护理项目")
    public ResponseResult updateStatus(
            @ApiParam(value = "护理项目ID", required = true) @PathVariable("id") Long id,
            @ApiParam(value = "状态（0：禁用，1：启用）", required = true) @PathVariable("status") Integer status) {
        // 需要在Service中添加updateStatus方法
        nursingProjectService.updateStatus(id, status);
        return success("状态修改成功");
    }
}