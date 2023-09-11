package com.lin.ss19jwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.lin.ss19jwt.dto.LoginDto;
import com.lin.ss19jwt.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/9/5 11:51
 */
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public JSONObject userLogin(@RequestBody LoginDto loginDto) {
        return userService.userLogin(loginDto);
    }
}
