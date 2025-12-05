package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.PostDto;
import com.zzyl.vo.PostVo;

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
}