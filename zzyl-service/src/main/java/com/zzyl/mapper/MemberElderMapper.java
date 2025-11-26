package com.zzyl.mapper;

import com.zzyl.entity.MemberElder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户老人关联Mapper接口
 */
@Mapper
public interface MemberElderMapper {

    /**
     * 插入用户老人关联
     * @param memberElder 关联信息
     * @return 影响行数
     */
    int insert(MemberElder memberElder);

    /**
     * 根据用户ID查询关联的老人列表
     * @param userId 用户ID
     * @return 关联列表
     */
    List<MemberElder> findByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和老人ID查询关联
     * @param userId 用户ID
     * @param elderId 老人ID
     * @return 关联信息
     */
    MemberElder findByUserIdAndElderId(@Param("userId") Long userId, @Param("elderId") Long elderId);

    /**
     * 删除用户老人关联
     * @param id 关联ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据用户ID和老人ID删除关联
     * @param userId 用户ID
     * @param elderId 老人ID
     * @return 影响行数
     */
    int deleteByUserIdAndElderId(@Param("userId") Long userId, @Param("elderId") Long elderId);
}