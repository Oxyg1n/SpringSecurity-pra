package com.lin.ss19jwt.util;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Linine
 * @since 2023/9/5 11:20
 */
public class JWTUtils {
    private static final String SALT = "@$%^$#REWw@#%WA12)*(F";

    /**
     * 生成token
     */
    public static String getToken(Map<String, String> map) {

        Calendar instance = Calendar.getInstance(); // 获取日历对象
        instance.add(Calendar.DATE, 7); // 默认七天过期

        JWTCreator.Builder builder = JWT.create();//创建 jwt builder
        //EL表达式 存放数据
        map.forEach(builder::withClaim);

        return builder.withExpiresAt(instance.getTime()) // 指定过期时间
                .sign(Algorithm.HMAC256(SALT));
    }

    /**
     * 验证token合法性
     * 如果有错误会报错，获取不到信息
     */
    public static DecodedJWT decodedToken(String token) {
        return JWT.require(Algorithm.HMAC256(SALT)).build().verify(token);
    }

    public static JSONObject getUserInfo(String token) throws RuntimeException {
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = decodedToken(token);
        } catch (SignatureVerificationException e) {
            throw new RuntimeException("签名不一致异常");
        } catch (TokenExpiredException e) {
            throw new RuntimeException("令牌过期异常");
        } catch (AlgorithmMismatchException e) {
            throw new RuntimeException("算法匹配错误异常");
        }
        String userId = decodedJWT.getClaims().get("userId").asString();
        String userName = decodedJWT.getClaims().get("userName").asString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", userName);
        jsonObject.put("userId", userId);
        return jsonObject;
    }
}
