package com.lin.ss19jwt.service;

import com.alibaba.fastjson.JSONObject;
import com.lin.ss19jwt.dto.LoginDto;

/**
 * @author Linine
 * @since 2023/9/5 11:53
 */
public interface UserService {
    JSONObject userLogin(LoginDto loginDto);
}
