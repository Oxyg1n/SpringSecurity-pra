package com.lin.ss16auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/8/27 0:27
 */
@RestController
public class DemoController {

    @GetMapping("/admin") // ROLE_ADMIN
    public String admin() {
        return "admin ok";
    }

    @GetMapping("/user") // ROLE_USER
    public String user() {
        return "user ok";
    }

    @GetMapping("/getInfo") // READ_INFO
    public String getInfo() {
        return "info ok";
    }

}
