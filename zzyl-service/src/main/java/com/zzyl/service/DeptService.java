package com.zzyl.service;

import com.zzyl.dto.DeptDto;
import com.zzyl.vo.DeptVo;
import com.zzyl.vo.TreeVo;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DeptService {

    /**
     * 创建部门
     * @param deptDto 部门信息
     * @return 创建结果
     */
    Boolean createDept(DeptDto deptDto);

    /**
     * 更新部门
     * @param deptDto 部门信息
     * @return 更新结果
     */
    Boolean updateDept(DeptDto deptDto);

    /**
     * 启用/禁用部门
     * @param deptDto 部门信息
     * @return 操作结果
     */
    Boolean isEnable(DeptDto deptDto);

    /**
     * 删除部门
     * @param deptId 部门ID
     * @return 删除结果
     */
    Integer deleteDeptById(String deptId);

    /**
     * 查询部门列表
     * @param deptDto 查询条件
     * @return 部门列表
     */
    List<DeptVo> findDeptList(DeptDto deptDto);

    /**
     * 构建部门树
     * @param deptDto 查询条件
     * @return 部门树
     */
    TreeVo deptTreeVo(DeptDto deptDto);
}