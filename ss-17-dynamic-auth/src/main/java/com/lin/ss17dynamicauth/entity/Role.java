package com.lin.ss17dynamicauth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色信息
 * @author Linine
 * @since 2023/8/30 11:15
 */
@Getter
@Setter
@ToString
public class Role {

    private Integer id;
    private String name;
    private String nameZh;
}
