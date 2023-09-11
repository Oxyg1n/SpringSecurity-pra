package com.lin.ss17dynamicauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.ss17dynamicauth.entity.Menu;

import java.util.List;

/**
 * @author Linine
 * @since 2023/8/30 15:09
 */
public interface MenuService extends IService<Menu> {


    List<Menu> getAllMenus();
}
