package com.lin.ss17dynamicauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.ss17dynamicauth.entity.Menu;
import com.lin.ss17dynamicauth.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Linine
 * @since 2023/8/30 12:01
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getAllByRoles();
}
