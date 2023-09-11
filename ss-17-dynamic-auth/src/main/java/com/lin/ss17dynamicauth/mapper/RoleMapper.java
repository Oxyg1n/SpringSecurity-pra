package com.lin.ss17dynamicauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.ss17dynamicauth.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Linine
 * @since 2023/8/30 12:01
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select r.* from user_role ur join role r on ur.rid=r.id where ur.uid=#{userId} ")
    List<Role> getUserRoles(int userId);
}
