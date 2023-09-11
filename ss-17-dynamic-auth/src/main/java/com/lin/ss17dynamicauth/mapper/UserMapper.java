package com.lin.ss17dynamicauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.ss17dynamicauth.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Linine
 * @since 2023/8/30 11:25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
