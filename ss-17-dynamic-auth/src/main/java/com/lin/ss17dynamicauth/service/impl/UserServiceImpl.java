package com.lin.ss17dynamicauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lin.ss17dynamicauth.entity.Role;
import com.lin.ss17dynamicauth.entity.User;
import com.lin.ss17dynamicauth.mapper.RoleMapper;
import com.lin.ss17dynamicauth.mapper.UserMapper;
import com.lin.ss17dynamicauth.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Linine
 * @since 2023/8/30 11:23
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        // 获得用户信息
        User user = userMapper.selectOne(wrapper);
        if (user != null) {
            // 获得用户角色信息
            user.setRoles(roleMapper.getUserRoles(user.getId()));
            System.out.println("打印登录用户信息" + user);
            return user;
        } else throw new UsernameNotFoundException("用户不存在");

    }
}
