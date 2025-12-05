package com.zzyl.config;

/**
 * 缓存常量定义
 */
public class CacheConstant {

    // ==================== 权限模块缓存 ====================
    /** 资源缓存 */
    public static final String RESOURCE_CACHE = "resource";
    /** 资源列表缓存 */
    public static final String RESOURCE_LIST_CACHE = "resource_list";
    /** 资源树缓存 */
    public static final String RESOURCE_TREE_CACHE = "resource_tree";

    /** 角色缓存 */
    public static final String ROLE_CACHE = "role";
    /** 角色列表缓存 */
    public static final String ROLE_LIST_CACHE = "role_list";

    // ==================== 组织架构模块缓存 ====================
    /** 部门缓存 */
    public static final String DEPT_CACHE = "dept";
    /** 部门列表缓存 */
    public static final String DEPT_LIST_CACHE = "dept_list";
    /** 部门树缓存 */
    public static final String DEPT_TREE_CACHE = "dept_tree";

    /** 岗位缓存 */
    public static final String POST_CACHE = "post";
    /** 岗位列表缓存 */
    public static final String POST_LIST_CACHE = "post_list";

    // ==================== 用户模块缓存 ====================
    /** 用户缓存 */
    public static final String USER_CACHE = "user";
    /** 用户列表缓存 */
    public static final String USER_LIST_CACHE = "user_list";
    /** 用户角色关联缓存 */
    public static final String USER_ROLE_CACHE = "user_role";

    // ==================== 业务模块缓存 ====================
    /** 护理项目缓存 */
    public static final String NURSING_PROJECT_CACHE = "nursing_project";
    /** 护理等级缓存 */
    public static final String NURSING_LEVEL_CACHE = "nursing_level";
    /** 护理计划缓存 */
    public static final String NURSING_PLAN_CACHE = "nursing_plan";

    // ==================== 缓存TTL配置 ====================
    /** 默认缓存时间：1小时 */
    public static final long DEFAULT_TTL = 3600L;
    /** 短期缓存时间：30分钟 */
    public static final long SHORT_TTL = 1800L;
    /** 长期缓存时间：2小时 */
    public static final long LONG_TTL = 7200L;
}