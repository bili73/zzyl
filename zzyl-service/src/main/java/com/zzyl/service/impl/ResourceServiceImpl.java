package com.zzyl.service.impl;

import com.zzyl.dto.ResourceDto;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;
import com.zzyl.vo.TreeItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源服务实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<ResourceVo> findResourceList(ResourceDto resourceDto) {
        return new ArrayList<>();
    }

    @Override
    public TreeVo resourceTreeVo(ResourceDto resourceDto) {
        TreeVo treeVo = new TreeVo();
        treeVo.setItems(new ArrayList<>());
        return treeVo;
    }

    @Override
    public List<String> getUserButtons(Long userId) {
        return new ArrayList<>();
    }
}