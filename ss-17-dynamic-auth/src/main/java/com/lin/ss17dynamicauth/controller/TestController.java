package com.lin.ss17dynamicauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/8/30 11:20
 */
@RestController
public class TestController {

    /*
    /admin/** admin角色
    /user/** user角色
    /test/** user guest角色

    admin用户有admin和user角色
    user用户只有user角色
    lin用户只有guest角色

     */
    @GetMapping("/admin/hello")
    public String admin() {
        return "hello admin";
    }

    @GetMapping("/user/hello")
    public String user() {
        return "hello user";
    }

    @GetMapping("/guest/hello")
    public String guest() {
        return "hello guest";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
