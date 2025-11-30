package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.service.MemberService;
import com.zzyl.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户管理
 */
@Slf4j
@Api(tags = "客户管理")
@RestController
@RequestMapping("/customer/user")
public class CustomerUserController {

    @Autowired
    MemberService memberService;

    /**
     * C端用户登录--微信登录
     * @param userLoginRequestDto 用户登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    @ApiOperation("小程序端登录")
    public ResponseResult<LoginVo> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        LoginVo loginVo = memberService.login(userLoginRequestDto);
        return ResponseResult.success(loginVo);
    }

}

/**
 * 客户用户分页查询控制器
 */
@Slf4j
@Api(tags = "客户用户管理")
@RestController
@RequestMapping("/c-user")
class CustomerUserPageController {

    /**
     * 分页查询客户用户列表
     * @param pageSize 每页记录数
     * @param pageNum 当前页码
     * @return 分页结果
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询客户用户",notes = "分页查询客户用户")
    public ResponseResult<PageResponse<Map<String, Object>>> getCUserPage(
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNum) {
        // 暂时返回空的分页数据，等后续实现了CustomerUserService后再完善
        PageResponse<Map<String, Object>> pageResponse = new PageResponse<>();
        pageResponse.setRecords(new java.util.ArrayList<>());
        pageResponse.setTotal(0L);
        pageResponse.setPage(pageNum);
        pageResponse.setPageSize(pageSize);
        return ResponseResult.success(pageResponse);
    }

}