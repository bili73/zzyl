package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.Dept;
import com.zzyl.entity.Post;
import com.zzyl.entity.Role;
import com.zzyl.entity.User;
import com.zzyl.entity.UserRole;
import com.zzyl.mapper.DeptMapper;
import com.zzyl.mapper.PostMapper;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.UserMapper;
import com.zzyl.mapper.UserRoleMapper;
import com.zzyl.service.UserService;
import com.zzyl.utils.PasswordUtil;
import com.zzyl.vo.UserVo;
import com.zzyl.config.CacheConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserVo login(UserDto userDto) {
        // 这个方法应该在LoginServiceImpl中实现，这里暂时返回null
        return null;
    }

    @Override
    @Cacheable(value = CacheConstant.USER_LIST_CACHE,
               key = "'user_list_' + (#userDto?.username != null ? #userDto.username : '') + '_' + (#userDto?.dataState != null ? #userDto.dataState : '') + '_' + (#userDto?.realName != null ? #userDto.realName : '') + '_' + (#userDto?.mobile != null ? #userDto.mobile : '') + '_' + (#userDto?.email != null ? #userDto.email : '') + '_' + (#userDto?.sex != null ? #userDto.sex : '') + '_' + (#userDto?.deptNo != null ? #userDto.deptNo : '') + '_' + (#userDto?.postNo != null ? #userDto.postNo : '') + '_' + #pageNum + '_' + #pageSize",
               unless = "#result == null or #result.total == 0")
    public PageResponse<UserVo> pageQuery(UserDto userDto, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);

        User user = new User();
        if (userDto != null) {
            BeanUtils.copyProperties(userDto, user);
        }

        List<User> userList = userMapper.selectAll(user);
        Page<User> userPage = (Page<User>) userList;

        // 转换为VO并填充角色信息
        List<UserVo> userVoList = userPage.stream().map(u -> {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(u, userVo);

            // 获取用户角色信息
            List<UserRole> userRoles = userRoleMapper.selectByUserId(u.getId());
            if (!CollectionUtils.isEmpty(userRoles)) {
                Set<String> roleIds = userRoles.stream()
                    .map(ur -> ur.getRoleId().toString())
                    .collect(Collectors.toSet());
                userVo.setRoleVoIds(roleIds);

                // 获取角色名称
                Set<String> roleNames = userRoles.stream()
                    .map(ur -> {
                        Role role = roleMapper.selectByPrimaryKey(ur.getRoleId());
                        return role != null ? role.getRoleName() : "";
                    })
                    .collect(Collectors.toSet());
                userVo.setRoleLabels(roleNames);
            }

            // 填充部门名称
            if (u.getDeptNo() != null && !u.getDeptNo().isEmpty()) {
                Dept dept = deptMapper.selectByDeptNo(u.getDeptNo());
                if (dept != null) {
                    userVo.setDeptName(dept.getDeptName());
                }
            }

            // 填充职位名称
            if (u.getPostNo() != null && !u.getPostNo().isEmpty()) {
                Post post = postMapper.selectByPostNo(u.getPostNo());
                if (post != null) {
                    userVo.setPostName(post.getPostName());
                }
            }

            return userVo;
        }).collect(Collectors.toList());

        // 手动构建PageResponse
        PageResponse<UserVo> pageResponse = new PageResponse<>();
        pageResponse.setPage(userPage.getPageNum());
        pageResponse.setPageSize(userPage.getPageSize());
        pageResponse.setTotal(userPage.getTotal());
        pageResponse.setPages((long) userPage.getPages());
        pageResponse.setRecords(userVoList);

        return pageResponse;
    }

    @Override
    @Cacheable(value = CacheConstant.USER_CACHE, key = "#id", unless = "#result == null")
    public UserVo getById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return null;
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);

        // 获取用户角色信息
        List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (!CollectionUtils.isEmpty(userRoles)) {
            Set<String> roleIds = userRoles.stream()
                .map(ur -> ur.getRoleId().toString())
                .collect(Collectors.toSet());
            userVo.setRoleVoIds(roleIds);

            // 获取角色名称
            Set<String> roleNames = userRoles.stream()
                .map(ur -> {
                    Role role = roleMapper.selectByPrimaryKey(ur.getRoleId());
                    return role != null ? role.getRoleName() : "";
                })
                .collect(Collectors.toSet());
            userVo.setRoleLabels(roleNames);
        }

        // 填充部门名称
        if (user.getDeptNo() != null && !user.getDeptNo().isEmpty()) {
            Dept dept = deptMapper.selectByDeptNo(user.getDeptNo());
            if (dept != null) {
                userVo.setDeptName(dept.getDeptName());
            }
        }

        // 填充职位名称
        if (user.getPostNo() != null && !user.getPostNo().isEmpty()) {
            Post post = postMapper.selectByPostNo(user.getPostNo());
            if (post != null) {
                userVo.setPostName(post.getPostName());
            }
        }

        return userVo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public UserVo add(UserDto userDto) {

        // 检查用户名是否已存在
        if (userDto.getUsername() != null) {
            User existingUser = userMapper.selectByUsername(userDto.getUsername());
            if (existingUser != null) {
                throw new RuntimeException("用户名已存在: " + userDto.getUsername());
            }
        }
        // 检查真实姓名是否已存在
        if (userDto.getRealName() != null && !userDto.getRealName().trim().isEmpty()) {
            User existingUserByRealName = userMapper.selectByRealName(userDto.getRealName().trim());
            if (existingUserByRealName != null) {
                throw new RuntimeException("真实姓名已存在: " + userDto.getRealName().trim());
            }
        }

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        // 设置默认值
        if (user.getDataState() == null) {
            user.setDataState("0"); // 0启用
        }
        if (user.getUserType() == null) {
            user.setUserType("0"); // 0系统用户
        }
        if (user.getSex() == null) {
            user.setSex("0"); // 0男
        }
        // 设置username - 优先使用email，如果没有email则使用realName生成一个
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            if (userDto.getEmail() != null && !userDto.getEmail().trim().isEmpty()) {
                // 优先使用邮箱作为用户名
                user.setUsername(userDto.getEmail().trim());
            } else if (userDto.getRealName() != null && !userDto.getRealName().trim().isEmpty()) {
                // 如果没有邮箱，使用真实姓名的拼音作为用户名，这里简化处理，直接使用真实姓名
                user.setUsername(userDto.getRealName().trim());
            } else {
                user.setUsername("user_" + System.currentTimeMillis());
            }
        } else {
        }

        // 设置nickName - 如果没有昵称，使用真实姓名
        if (user.getNickName() == null || user.getNickName().trim().isEmpty()) {
            if (userDto.getRealName() != null && !userDto.getRealName().trim().isEmpty()) {
                user.setNickName(userDto.getRealName().trim());
            } else {
                user.setNickName("用户");
            }
        }

        // 设置isDelete默认值
        if (user.getIsDelete() == null) {
            user.setIsDelete(0); // 0未删除
        }

        // 设置isLeader默认值
        if (user.getIsLeader() == null) {
            user.setIsLeader(0); // 0不是leader
        }

        // 设置默认密码
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword(PasswordUtil.encodePassword("123456"));
        } else {
            user.setPassword(PasswordUtil.encodePassword(user.getPassword()));
        }

        userMapper.insertSelective(user);

        // 保存用户角色关联
        if (!CollectionUtils.isEmpty(userDto.getRoleVoIds())) {
            saveUserRoles(user.getId(), userDto.getRoleVoIds());
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        userVo.setRoleVoIds(userDto.getRoleVoIds());

        return userVo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public UserVo update(UserDto userDto) {

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        // 更新用户基本信息
        boolean result = userMapper.updateByPrimaryKeySelective(user) > 0;

        // 更新用户角色关联
        if (!CollectionUtils.isEmpty(userDto.getRoleVoIds())) {
            // 先删除原有角色关联
            userRoleMapper.deleteByUserId(userDto.getId());
            // 保存新的角色关联
            saveUserRoles(userDto.getId(), userDto.getRoleVoIds());
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        return result ? userVo : null;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public int delete(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return 0;
        }

        int count = 0;
        for (Long id : ids) {
            // 删除用户角色关联
            userRoleMapper.deleteByUserId(id);
            // 删除用户
            if (userMapper.deleteByPrimaryKey(id) > 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public UserVo updateStatus(UserDto userDto) {

        User user = new User();
        user.setId(userDto.getId());
        user.setDataState(userDto.getDataState());

        boolean result = userMapper.updateByPrimaryKeySelective(user) > 0;
        if (!result) {
            return null;
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        return userVo;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public UserVo resetPassword(UserDto userDto) {

        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(PasswordUtil.encodePassword("123456"));

        boolean result = userMapper.updateByPrimaryKeySelective(user) > 0;
        if (!result) {
            return null;
        }

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userDto, userVo);
        return userVo;
    }

    /**
     * 保存用户角色关联
     */
    private void saveUserRoles(Long userId, Set<String> roleIds) {
        if (!CollectionUtils.isEmpty(roleIds)) {
            for (String roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(Long.parseLong(roleId));
                userRole.setDataState("0");
                userRoleMapper.insertSelective(userRole);
            }
        }
    }

    // 实现Controller需要的缺失方法
    @Override
    public PageResponse<UserVo> findUserVoPage(UserDto userDto, int pageNum, int pageSize) {
        return pageQuery(userDto, pageNum, pageSize);
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public UserVo createUser(UserDto userDto) {
        return add(userDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public Boolean updateUser(UserDto userDto) {
        UserVo result = update(userDto);
        return result != null;
    }

    @Override
    public UserVo getCurrentUser() {
        // 这里应该从Spring Security上下文中获取当前用户，简化实现
        return new UserVo();
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public Boolean isEnable(Long id, String status) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setDataState(status);
        UserVo result = updateStatus(userDto);
        return result != null;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public Boolean removeUser(Long userId) {
        Long[] ids = {userId};
        int result = delete(ids);
        return result > 0;
    }

    @Override
    @Transactional
    @CacheEvict(value = {CacheConstant.USER_LIST_CACHE}, allEntries = true)
    public Boolean resetPassword(Long userId) {
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        UserVo result = resetPassword(userDto);
        return result != null;
    }
}