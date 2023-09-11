package com.lin.ss19jwt;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lin.ss19jwt.util.JWTUtils;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;
import java.util.HashMap;

//@SpringBootTest
class Ss19JwtApplicationTests {


    // 生成token
    @Test
    void contextLoads() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", "12");
        map.put("userName", "lin");
        String token = JWTUtils.getToken(map);
        System.out.println(token);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImxpbiIsImV4cCI6MTY5NDQ4OTIxMCwidXNlcklkIjoiMTIifQ.INL7aYYPXt6nKBKtdhsqpxph11X45_Cea5TEnXPu5TQ
        String[] split = token.split("\\.");
        // 尝试对第一二部分解码
        Base64.Decoder decoder = Base64.getDecoder();
        System.out.println(new String(decoder.decode(split[0]))); // {"typ":"JWT","alg":"HS256"}
        System.out.println(new String(decoder.decode(split[1]))); // {"userName":"lin","exp":1694490159,"userId":"12"}

        // JWT解码
        DecodedJWT decodedJWT = JWTUtils.decodedToken(token);
        System.out.println(decodedJWT.getHeader()); // 获得header
        System.out.println(decodedJWT.getPayload()); // 获得 payload
        System.out.println(decodedJWT.getClaims().get("userName").asString()); // lin
        System.out.println(decodedJWT.getClaims().get("userId").asString()); // 12
    }

}
