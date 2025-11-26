package com.zzyl.mapper;

import com.zzyl.entity.Bill;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 账单Mapper接口
 */
@Mapper
public interface BillMapper {

    /**
     * 插入账单
     */
    void insert(Bill bill);

    /**
     * 根据ID查询账单
     */
    Bill selectById(Long id);

    /**
     * 根据条件查询账单列表
     */
    List<Bill> selectByCondition(Long userId, Integer transactionStatus, Long elderId);

    /**
     * 根据订单ID查询账单数量
     */
    Long countByOrderId(Long orderId);
}