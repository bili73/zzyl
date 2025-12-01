package com.zzyl.mapper;

import com.zzyl.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<User> list);

    // 自定义查询方法
    List<User> selectAll(User user);

    User selectByUsername(@Param("username") String username);

    User selectByOpenId(@Param("openId") String openId);
    User selectByRealName(@Param("realName") String realName);

    int deleteByIds(@Param("list") List<Long> ids);
}