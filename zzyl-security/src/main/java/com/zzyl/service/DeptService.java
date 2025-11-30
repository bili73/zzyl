package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.dto.DeptDto;
import com.zzyl.entity.Dept;
import com.zzyl.vo.DeptVo;
import com.zzyl.vo.TreeVo;

import java.util.List;

public interface DeptService {

    /**
     * 创建部门
     * @param deptDto 部门信息
     * @return 是否成功
     */
    Boolean createDept(DeptDto deptDto);

    /**
     * 更新部门
     * @param deptDto 部门信息
     * @return 是否成功
     */
    Boolean updateDept(DeptDto deptDto);

    /**
     * 查询部门列表
     * @param deptDto 查询条件
     * @return 部门列表
     */
    List<DeptVo> findDeptList(DeptDto deptDto);

    /**
     * 根据部门编号列表查询部门
     * @param deptNos 部门编号列表
     * @return 部门列表
     */
    List<DeptVo> findDeptInDeptNos(List<String> deptNos);

    /**
     * 创建部门编号
     * @param parentDeptNo 父部门编号
     * @return 部门编号
     */
    String createDeptNo(String parentDeptNo);

    /**
     * 根据角色ID查询部门列表
     * @param roleIdSet 角色ID集合
     * @return 部门列表
     */
    List<DeptVo> findDeptVoListInRoleId(List<Long> roleIdSet);

    /**
     * 删除部门
     * @param deptId 部门ID
     * @return 删除数量
     */
    int deleteDeptById(String deptId);

    /**
     * 启用/禁用部门
     * @param deptDto 部门信息
     * @return 是否成功
     */
    Boolean isEnable(DeptDto deptDto);

    /**
     * 构建部门树
     * @param dto 查询条件
     * @return 树形结构
     */
    TreeVo deptTreeVo(DeptDto dto);

    /**
     * 判断是否为最底层部门
     * @param dept 部门编号
     * @return 是否为最底层
     */
    boolean isLowestDept(String dept);

    /**
     * 部门树结构（兼容版本）
     * @return 部门树
     */
    List<DeptVo> selectDeptTree();
}