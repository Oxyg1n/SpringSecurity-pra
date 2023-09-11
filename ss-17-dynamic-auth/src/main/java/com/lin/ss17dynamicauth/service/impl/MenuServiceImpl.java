package com.lin.ss17dynamicauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.ss17dynamicauth.entity.Menu;
import com.lin.ss17dynamicauth.entity.Role;
import com.lin.ss17dynamicauth.mapper.MenuMapper;
import com.lin.ss17dynamicauth.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Linine
 * @since 2023/8/30 15:09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getAllMenus() {
        return getBaseMapper().getAllByRoles();
    }

}
