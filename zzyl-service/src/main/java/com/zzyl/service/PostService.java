package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.PostDto;
import com.zzyl.entity.Post;
import com.zzyl.vo.PostVo;

import java.util.List;

/**
 * 岗位服务接口
 */
public interface PostService {

    /**
     * 分页查询岗位
     * @param postDto 岗位查询条件
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResponse<PostVo> findPostPage(PostDto postDto, int pageNum, int pageSize);

    // 添加Controller需要的缺失方法
    PostVo createPost(PostDto postDto);

    Boolean updatePost(PostDto postDto);

    void isEnable(PostDto postDto);

    Integer deletePostById(String postId);

    List<PostVo> findPostList(PostDto postDto);

    /**
     * 根据用户ID查询岗位列表
     * @param userId 用户ID
     * @return 岗位列表
     */
    List<PostVo> findPostVoListByUserId(Long userId);

    /**
     * 分页查询岗位列表（兼容版本）
     * @param deptNo 部门编号
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    Page<Post> selectPostByDeptNo(String deptNo, Integer pageNum, Integer pageSize);
}