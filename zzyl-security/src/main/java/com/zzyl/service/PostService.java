package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.PostDto;
import com.zzyl.entity.Post;
import com.zzyl.vo.PostVo;

import java.util.List;

public interface PostService {

    /**
     * 分页查询岗位列表
     * @param postDto 岗位查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    PageResponse<PostVo> findPostPage(PostDto postDto, int pageNum, int pageSize);

    /**
     * 创建岗位
     * @param postDto 岗位信息
     * @return 岗位信息
     */
    PostVo createPost(PostDto postDto);

    /**
     * 更新岗位
     * @param postDto 岗位信息
     * @return 是否成功
     */
    Boolean updatePost(PostDto postDto);

    /**
     * 启用/禁用岗位
     * @param postDto 岗位信息
     */
    void isEnable(PostDto postDto);

    /**
     * 查询岗位列表
     * @param postDto 查询条件
     * @return 岗位列表
     */
    List<PostVo> findPostList(PostDto postDto);

    /**
     * 根据用户ID查询岗位列表
     * @param userId 用户ID
     * @return 岗位列表
     */
    List<PostVo> findPostVoListByUserId(Long userId);

    /**
     * 删除岗位
     * @param postIds 岗位ID
     * @return 删除数量
     */
    int deletePostById(String postIds);

    /**
     * 分页查询岗位列表（兼容版本）
     * @param postDto 岗位查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    Page<Post> selectPostByDeptNo(String deptNo, Integer pageNum, Integer pageSize);
}