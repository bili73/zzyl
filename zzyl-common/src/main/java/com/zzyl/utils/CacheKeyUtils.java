package com.zzyl.utils;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 缓存键生成工具类
 * 用于生成稳定的、基于查询条件的缓存键
 */
public class CacheKeyUtils {

    /**
     * 为用户查询生成稳定的缓存键
     * 只基于实际的查询条件字段，忽略null值和空值
     *
     * @param username 用户名
     * @param dataState 数据状态
     * @param realName 真实姓名
     * @param mobile 手机号
     * @param email 邮箱
     * @param sex 性别
     * @param deptNo 部门编号
     * @param postNo 岗位编号
     * @param userType 用户类型
     * @return 稳定的哈希键
     */
    public static String generateUserQueryKey(String username, String dataState, String realName,
                                           String mobile, String email, String sex,
                                           String deptNo, String postNo, String userType) {
        StringBuilder keyBuilder = new StringBuilder();

        // 只包含有效的查询条件字段，按固定顺序拼接
        appendIfNotEmpty(keyBuilder, "username", username);
        appendIfNotEmpty(keyBuilder, "dataState", dataState);
        appendIfNotEmpty(keyBuilder, "realName", realName);
        appendIfNotEmpty(keyBuilder, "mobile", mobile);
        appendIfNotEmpty(keyBuilder, "email", email);
        appendIfNotEmpty(keyBuilder, "sex", sex);
        appendIfNotEmpty(keyBuilder, "deptNo", deptNo);
        appendIfNotEmpty(keyBuilder, "postNo", postNo);
        appendIfNotEmpty(keyBuilder, "userType", userType);

        // 如果没有任何查询条件，返回默认键
        if (keyBuilder.length() == 0) {
            return "all_users";
        }

        return String.valueOf(Objects.hash(keyBuilder.toString()));
    }

    /**
     * 为部门查询生成稳定的缓存键
     */
    public static String generateDeptQueryKey(String dataState, String parentDeptNo) {
        StringBuilder keyBuilder = new StringBuilder();
        appendIfNotEmpty(keyBuilder, "dataState", dataState);
        appendIfNotEmpty(keyBuilder, "parentDeptNo", parentDeptNo);

        if (keyBuilder.length() == 0) {
            return "all_depts";
        }

        return String.valueOf(Objects.hash(keyBuilder.toString()));
    }

    /**
     * 为岗位查询生成稳定的缓存键
     */
    public static String generatePostQueryKey(String dataState, String deptNo) {
        StringBuilder keyBuilder = new StringBuilder();
        appendIfNotEmpty(keyBuilder, "dataState", dataState);
        appendIfNotEmpty(keyBuilder, "deptNo", deptNo);

        if (keyBuilder.length() == 0) {
            return "all_posts";
        }

        return String.valueOf(Objects.hash(keyBuilder.toString()));
    }

    /**
     * 为角色查询生成稳定的缓存键
     */
    public static String generateRoleQueryKey(String dataState) {
        StringBuilder keyBuilder = new StringBuilder();
        appendIfNotEmpty(keyBuilder, "dataState", dataState);

        if (keyBuilder.length() == 0) {
            return "all_roles";
        }

        return String.valueOf(Objects.hash(keyBuilder.toString()));
    }

    /**
     * 为资源查询生成稳定的缓存键
     */
    public static String generateResourceQueryKey(String dataState) {
        StringBuilder keyBuilder = new StringBuilder();
        appendIfNotEmpty(keyBuilder, "dataState", dataState);

        if (keyBuilder.length() == 0) {
            return "all_resources";
        }

        return String.valueOf(Objects.hash(keyBuilder.toString()));
    }

    /**
     * 为用户查询生成稳定的缓存键（重载方法）
     *
     * @param userDto 用户查询条件对象
     * @return 稳定的哈希键
     */
    public static String generateUserQueryKey(Object userDto) {
        if (userDto == null) {
            return "all_users";
        }

        // 使用反射或其他方式获取字段值，这里简化处理
        // 实际使用时可以通过反射获取UserDto的字段值
        return "user_query_" + userDto.hashCode();
    }

    /**
     * 如果值不为空，则添加到键构建器中
     */
    private static void appendIfNotEmpty(StringBuilder builder, String fieldName, String value) {
        if (StringUtils.hasText(value)) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(fieldName).append("=").append(value.trim());
        }
    }
}