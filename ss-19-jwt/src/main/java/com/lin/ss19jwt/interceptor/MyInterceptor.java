package com.lin.ss19jwt.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.ss19jwt.util.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Linine
 * @since 2023/9/6 1:16
 */
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 设置返回类型json和字符集格式
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("token");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 400);
        if (token == null || token.isEmpty()) {
            jsonObject.put("msg", "缺少token参数");
            response.getWriter().write(JSON.toJSONString(jsonObject));
        } else {

            try {
                System.out.println(JWTUtils.getUserInfo(token));
                return true;
            } catch (RuntimeException e) {
                jsonObject.put("msg", e.getMessage());
                response.getWriter().write(JSON.toJSONString(jsonObject));
            }
        }
        return false;
    }
}
