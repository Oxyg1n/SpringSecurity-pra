package com.lin.ss11session.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Linine
 * @since 2023/8/21 6:36
 */
@EnableWebSecurity
@EnableRedisHttpSession
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout().and() // 允许注销登录
                .csrf().disable()
                .sessionManagement() // 开启会话管理
                .maximumSessions(1) // 允许会话最大并发只能是一个客户端，也就是一个账号只能在一个地方登录
                .expiredSessionStrategy(this::onExpiredSessionDetected)
                .maxSessionsPreventsLogin(true) // 一旦登录成功，其他用户禁止登录当前账号
                .sessionRegistry(sessionRegistry()) // 指定session交给谁去管理
                .and()
                .and().build();
    }

    @Autowired
    public RedisIndexedSessionRepository repository;

    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(repository);
    }


    // session过期处理策略
    void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 400);
        jsonObject.put("msg", "会话已过期，请重新登录");
        String jsonString = JSONObject.toJSONString(jsonObject);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonString);
        response.flushBuffer();
    }

    // 监听会话的创建和过期，过期移除
    // 添加session-redis就不需要这个了
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
}
