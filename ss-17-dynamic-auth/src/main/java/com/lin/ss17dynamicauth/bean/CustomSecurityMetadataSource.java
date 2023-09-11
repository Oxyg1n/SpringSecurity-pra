package com.lin.ss17dynamicauth.bean;

import com.lin.ss17dynamicauth.entity.Menu;
import com.lin.ss17dynamicauth.entity.Role;
import com.lin.ss17dynamicauth.service.MenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Linine
 * @since 2023/8/30 11:30
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final MenuService menuService;

    public CustomSecurityMetadataSource(MenuService menuService) {
        this.menuService = menuService;
    }

    // 路径对比器
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    // 自定义动态资源权限的元数据信息
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        // 获得请求路径
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        // 查询数据库所有路径
        List<Menu> allMenu = menuService.getAllMenus();
        for (Menu menu : allMenu) {
            // 匹配上数据库路径
            if (antPathMatcher.match(menu.getPattern(), request.getRequestURI())) {
                // 遍历角色 组成数组
                String[] array = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                System.out.println("这个接口所需的角色信息:" + Arrays.toString(array));
                return SecurityConfig.createList(array);
            }
        }
        return null;

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
