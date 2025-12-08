package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.PostDto;
import com.zzyl.entity.Post;
import com.zzyl.mapper.PostMapper;
import com.zzyl.mapper.DeptMapper;
import com.zzyl.entity.Dept;
import com.zzyl.service.PostService;
import com.zzyl.vo.PostVo;
import com.zzyl.vo.DeptVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 岗位服务实现类
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public PageResponse<PostVo> findPostPage(PostDto postDto, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Post> postList = postMapper.selectList(postDto);
        Page<Post> page = (Page<Post>) postList;

        List<PostVo> postVoList = new ArrayList<>();
        for (Post post : postList) {
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(post, postVo);

            // 查询部门信息
            if (post.getDeptNo() != null) {
                DeptVo deptVo = new DeptVo();
                Dept dept = deptMapper.selectByDeptNo(post.getDeptNo());
                if (dept != null) {
                    BeanUtils.copyProperties(dept, deptVo);
                } else {
                    deptVo.setDeptNo(post.getDeptNo());
                }
                postVo.setDeptVo(deptVo);
            }

            postVoList.add(postVo);
        }

        PageResponse<PostVo> pageResponse = new PageResponse<>();
        pageResponse.setPage(page.getPageNum());
        pageResponse.setPageSize(page.getPageSize());
        pageResponse.setTotal(page.getTotal());
        pageResponse.setPages((long) page.getPages());
        pageResponse.setRecords(postVoList);

        return pageResponse;
    }

    @Override
    public PostVo createPost(PostDto postDto) {
        // 检查参数
        if (postDto.getDeptNo() == null || postDto.getDeptNo().isEmpty()) {
            throw new IllegalArgumentException("部门编号不能为空");
        }
        if (postDto.getPostName() == null || postDto.getPostName().isEmpty()) {
            throw new IllegalArgumentException("岗位名称不能为空");
        }

        // 自动生成postNo： deptNo + 3位数字
        String maxPostNo = postMapper.selectMaxPostNoByDeptNo(postDto.getDeptNo());
        String newPostNo;

        if (maxPostNo == null) {
            // 该部门还没有职位，从001开始
            newPostNo = postDto.getDeptNo() + "001";
        } else {
            // 获取最后3位数字并递增
            String lastThree = maxPostNo.substring(maxPostNo.length() - 3);
            int nextNumber = Integer.parseInt(lastThree) + 1;
            newPostNo = postDto.getDeptNo() + String.format("%03d", nextNumber);
        }

        Post post = new Post();
        post.setDeptNo(postDto.getDeptNo());
        post.setPostNo(newPostNo);
        post.setPostName(postDto.getPostName());
        post.setDataState(postDto.getDataState() != null ? postDto.getDataState() : "0");
        post.setSortNo(postDto.getSortNo() != null ? postDto.getSortNo() : 0);
        post.setRemark(postDto.getRemark());

        postMapper.insertSelective(post);

        PostVo postVo = new PostVo();
        BeanUtils.copyProperties(post, postVo);
        return postVo;
    }

    @Override
    public Boolean updatePost(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return postMapper.updateByPrimaryKeySelective(post) > 0;
    }

    @Override
    public void isEnable(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setDataState(postDto.getDataState());
        postMapper.updateByPrimaryKeySelective(post);
    }

    @Override
    public Integer deletePostById(String postId) {
        return postMapper.deletePostById(postId);
    }

    @Override
    public List<PostVo> findPostList(PostDto postDto) {
        List<Post> postList = postMapper.selectList(postDto);
        List<PostVo> postVoList = new ArrayList<>();
        for (Post post : postList) {
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(post, postVo);

            // 查询部门信息
            if (post.getDeptNo() != null) {
                DeptVo deptVo = new DeptVo();
                Dept dept = deptMapper.selectByDeptNo(post.getDeptNo());
                if (dept != null) {
                    BeanUtils.copyProperties(dept, deptVo);
                } else {
                    deptVo.setDeptNo(post.getDeptNo());
                }
                postVo.setDeptVo(deptVo);
            }

            postVoList.add(postVo);
        }
        return postVoList;
    }

    @Override
    public List<PostVo> findPostVoListByUserId(Long userId) {
        // 简化实现，返回空列表
        return new ArrayList<>();
    }

    @Override
    public Page<Post> selectPostByDeptNo(String deptNo, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PostDto postDto = new PostDto();
        postDto.setDeptNo(deptNo);
        List<Post> postList = postMapper.selectList(postDto);
        return (Page<Post>) postList;
    }
}