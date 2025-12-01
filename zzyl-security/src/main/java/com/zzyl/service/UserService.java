package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.vo.UserVo;

import java.util.List;

public interface UserService {

    /**
     * 分页查询用户列表
     * @param user 用户查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    Page<User> selectUserByPage(User user, Integer pageNum, Integer pageSize);

    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户信息
     */
    User selectUserById(Long id);

    /**
     * 新增用户
     * @param user 用户信息
     * @return 是否成功
     */
    Boolean insertUser(User user);

    /**
     * 更新用户
     * @param user 用户信息
     * @return 是否成功
     */
    Boolean updateUser(User user);

    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    Boolean deleteUserById(Long id);

    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return 是否成功
     */
    Boolean deleteUserByIds(List<Long> ids);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户信息
     */
    User selectUserByUsername(String username);

    /**
     * 根据openId获取用户
     * @param openId 微信openId
     * @return 用户信息
     */
    User selectUserByOpenId(String openId);

    /**
     * 修改用户密码
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    Boolean updatePassword(Long id, String newPassword);

    /**
     * 修改用户状态
     * @param id 用户ID
     * @param dataState 状态
     * @return 是否成功
     */
    Boolean updateDataState(Long id, String dataState);

    // ==================== 扩展业务方法 ====================

    /**
     * 分页查询用户（包含角色信息）
     */
    PageResponse<UserVo> findUserVoPage(UserDto userDto, Integer pageNum, Integer pageSize);

    /**
     * 创建用户（包含角色关联）
     */
    UserVo createUser(UserDto userDto);

    /**
     * 更新用户（包含角色关联）
     */
    Boolean updateUser(UserDto userDto);

    /**
     * 获取当前用户信息
     */
    UserVo getCurrentUser();

    /**
     * 启用或禁用用户
     */
    Boolean isEnable(Long id, String status);

    /**
     * 删除用户
     */
    Boolean removeUser(Long userId);

    /**
     * 重置密码
     */
    Boolean resetPassword(Long userId);
}