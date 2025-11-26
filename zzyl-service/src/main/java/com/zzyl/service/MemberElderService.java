package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.MemberElderDto;
import com.zzyl.vo.MemberElderVo;

import java.util.List;

/**
 * 用户老人关联服务接口
 */
public interface MemberElderService {

    /**
     * 绑定老人
     * @param dto 绑定信息
     * @return 关联ID
     */
    Long bindElder(MemberElderDto dto);

    /**
     * 解除绑定
     * @param elderId 老人ID
     */
    void unbindElder(Long elderId);

    /**
     * 分页查询用户绑定的老人列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResponse<MemberElderVo> pageUserElders(Integer pageNum, Integer pageSize);

    /**
     * 查询用户绑定的老人列表
     * @return 老人列表
     */
    List<MemberElderVo> listUserElders();

    /**
     * 获取用户与老人的绑定关系
     * @param elderId 老人ID
     * @return 绑定关系
     */
    MemberElderVo getUserElderRelation(Long elderId);
}