package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.dto.MemberElderDto;
import com.zzyl.service.MemberElderService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.MemberElderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/memberElder")
@Api(tags = "小程序端-用户老人绑定")
public class MemberElderController extends BaseController {

    @Autowired
    private MemberElderService memberElderService;

    @PostMapping("/add")
    @ApiOperation("绑定老人")
    public ResponseResult<Long> bindElder(@RequestBody MemberElderDto memberElderDto) {
        Long relationId = memberElderService.bindElder(memberElderDto);
        return success(relationId);
    }

    @DeleteMapping("/{elderId}")
    @ApiOperation("解除绑定老人")
    public ResponseResult<Void> unbindElder(@PathVariable Long elderId) {
        memberElderService.unbindElder(elderId);
        return success();
    }

    @GetMapping("/list-by-page")
    @ApiOperation("分页查询用户绑定的老人列表")
    public ResponseResult<PageResponse<MemberElderVo>> listByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        PageResponse<MemberElderVo> pageResponse = memberElderService.pageUserElders(pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/list")
    @ApiOperation("查询用户绑定的老人列表")
    public ResponseResult<java.util.List<MemberElderVo>> list() {
        java.util.List<MemberElderVo> elderList = memberElderService.listUserElders();
        return success(elderList);
    }

    @GetMapping("/{elderId}")
    @ApiOperation("获取用户与老人的绑定关系")
    public ResponseResult<MemberElderVo> getRelation(@PathVariable Long elderId) {
        MemberElderVo relation = memberElderService.getUserElderRelation(elderId);
        return success(relation);
    }
}