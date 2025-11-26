package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/orders/project")
@Api(tags = "小程序端-护理项目")
public class CustomerNursingProjectController extends BaseController {

    @Autowired
    private NursingProjectService nursingProjectService;

    @GetMapping("/page")
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> page(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        // 如果没有指定状态，默认查询启用状态的
        if (status == null) {
            status = 1;
        }
        PageResponse<NursingProjectVo> pageResponse = nursingProjectService.getByPage(name, status, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据编号查询护理项目信息")
    public ResponseResult<NursingProjectVo> getById(@PathVariable Long id) {
        NursingProjectVo vo = nursingProjectService.getById(id);
        return success(vo);
    }
}