package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.vo.BillVo;

/**
 * 账单Service接口
 */
public interface BillService {

    /**
     * 分页查询账单列表（客户端）
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param transactionStatus 交易状态（可选）
     * @param elderId 老人ID（可选）
     * @return 分页结果
     */
    PageResponse<BillVo> getBillsByPage(Integer pageNum, Integer pageSize, Integer transactionStatus, Long elderId);

    /**
     * 根据ID获取账单详情
     *
     * @param id 账单ID
     * @return 账单详情
     */
    BillVo getBillById(Long id);
}