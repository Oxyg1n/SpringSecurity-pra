package com.lin.ss17dynamicauth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 路径信息
 * @author Linine
 * @since 2023/8/30 11:17
 */
@Getter
@Setter
public class Menu {

    private Integer id;

    private String pattern;

    // 这个菜单所需要的角色信息
    @TableField(exist = false)
    private List<Role> roles;
}
