package com.lin.ss16auth;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Linine
 * @since 2023/8/27 14:09
 */
@RestController
@RequestMapping("/hello")
public class AuthMethodController {

    /**
     * @PreAuthorize：既可以验证角色，又可以验证权限字符串 支持and、or等连接符
     */
    @PreAuthorize("hasRole('ADMIN')  and authentication.name =='root'") // 验证具有ADMIN角色，且用户名是root
    @RequestMapping("/test")
    public String hello2(){
        return "hello2";
    }

    @PreAuthorize("hasAuthority('READ_INFO')") // 验证READ_INFO权限
    @GetMapping
    public String hello() {
        return "hello";
    }

    // win7登录 http://127.0.0.1:8080/hello/name?name=win7
    @PreAuthorize("authentication.name==#name") // EL表达式
    @GetMapping("/name")
    public String hello(String name) {
        return "hello:" + name;
    }

    // 通过规则：传入参数的users中的id为奇数，否则会被过滤掉（比如id=2）
    @PreFilter(value = "filterObject.id % 2 != 0", filterTarget = "users") // filterTarget必须是数组、集合类型，filterObject为数组元素对象
    @PostMapping("/users")
    public void addUsers(@RequestBody List<User> users) {
        System.out.println("users = " + users);
    }

    // 返回id==1的数据
    @PostAuthorize("returnObject.id==1")
    @GetMapping("/userId")
    public User getUserById(Integer id) {
        return new User(id, "blr");
    }

    @PostFilter("filterObject.id % 2 == 0")// 用来对方法返回值进行过滤
    @GetMapping("/lists")
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User(i, "blr:" + i));
        }
        return users;
    }

    /**
     * 以下这些注解用的比较少，了解即可：
     */
    @Secured({"ROLE_USER"}) // 只能判断角色
    @GetMapping("/secured")
    public User getUserByUsername() {
        return new User(99, "secured");
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"}) //具有其中一个即可
    @GetMapping("/username")
    public User getUserByUsername2(String username) {
        return new User(99, username);
    }

    @PermitAll
    @GetMapping("/permitAll")
    public String permitAll() {
        return "PermitAll";
    }

    @DenyAll
    @GetMapping("/denyAll")
    public String denyAll() {
        return "DenyAll";
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"}) // 具有其中一个角色即可
    @GetMapping("/rolesAllowed")
    public String rolesAllowed() {
        return "RolesAllowed";
    }
}
