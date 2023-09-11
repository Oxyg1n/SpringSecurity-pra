package com.lin.ss19jwt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lin.ss19jwt.dto.LoginDto;
import com.lin.ss19jwt.service.UserService;
import com.lin.ss19jwt.util.JWTUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Linine
 * @since 2023/9/5 11:54
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public JSONObject userLogin(LoginDto loginDto) {
        JSONObject jsonObject = new JSONObject();
        if (!"admin".equals(loginDto.getUserName()) || !"admin".equals(loginDto.getPassword())) {
            jsonObject.put("code", 400);
            jsonObject.put("msg", "用户密码错误");
            return jsonObject;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", "admin");
        map.put("userId", "1");
        String token = JWTUtils.getToken(map);
        jsonObject.put("code", 200);
        jsonObject.put("msg", "登录成功");
        jsonObject.put("token", token);
        return jsonObject;
    }
}
