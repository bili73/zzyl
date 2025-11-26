package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.service.BillService;
import com.zzyl.vo.BillVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 小程序端-账单
 */
@RestController
@RequestMapping("/customer/bill")
@Api(tags = "小程序端-账单")
public class CustomerBillController extends BaseController {

    @Autowired
    private BillService billService;

    @GetMapping("/page")
    @ApiOperation("分页查询账单列表")
    public ResponseResult<PageResponse<BillVo>> getBillsByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer transactionStatus,
            @RequestParam(required = false) Long elderId) {

        PageResponse<BillVo> pageResponse = billService.getBillsByPage(pageNum, pageSize, transactionStatus, elderId);
        return success(pageResponse);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取账单详情")
    public ResponseResult<BillVo> getBillById(@PathVariable Long id) {
        BillVo billVo = billService.getBillById(id);
        return success(billVo);
    }
}