package com.zzyl.mapper;

import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.vo.MenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ResourceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<Resource> list);

    List<Resource> selectList(ResourceDto resourceDto);

    @Select("SELECT * FROM sys_resource WHERE resource_no = #{resourceNo}")
    Resource selectByResourceNo(String resourceNo);

    /**
     * 根据资源编号删除角色资源关联
     * @param resourceNo
     */
    void deleteRoleResourceByResourceNo(String resourceNo);

    /**
     * 根据用户ID查询资源列表
     * @param userId 用户ID
     * @return 资源列表
     */
    List<Resource> selectListByUserId(Long userId);

    /**
     * 根据用户ID查询菜单数据
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<MenuVo> findListByUserId(Long userId);

    /**
     * 根据用户ID查询URL权限列表
     * @param userId 用户ID
     * @return URL列表
     */
    @Select("select sr.request_path from sys_user_role sur, sys_role_resource srr, sys_resource sr where sur.role_id = srr.role_id and srr.resource_no = sr.resource_no and sr.data_state = '0' and sr.resource_type = 'r' and sur.user_id = #{userId}")
    List<String> selectUrlListByUserId(Long userId);
}
