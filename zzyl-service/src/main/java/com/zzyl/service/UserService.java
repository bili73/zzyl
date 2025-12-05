package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.vo.UserVo;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户登录
     * @param userDto 用户登录信息
     * @return 登录结果
     */
    UserVo login(UserDto userDto);

    /**
     * 分页查询用户
     * @param userDto 用户查询条件
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResponse<UserVo> pageQuery(UserDto userDto, Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    UserVo getById(Long id);

    /**
     * 新增用户
     * @param userDto 用户信息
     * @return 操作结果
     */
    UserVo add(UserDto userDto);

    /**
     * 修改用户
     * @param userDto 用户信息
     * @return 操作结果
     */
    UserVo update(UserDto userDto);

    /**
     * 删除用户
     * @param ids 用户ID数组
     * @return 操作结果
     */
    int delete(Long[] ids);

    /**
     * 启用/禁用用户
     * @param userDto 用户信息
     * @return 操作结果
     */
    UserVo updateStatus(UserDto userDto);

    /**
     * 重置用户密码
     * @param userDto 用户信息
     * @return 操作结果
     */
    UserVo resetPassword(UserDto userDto);
}