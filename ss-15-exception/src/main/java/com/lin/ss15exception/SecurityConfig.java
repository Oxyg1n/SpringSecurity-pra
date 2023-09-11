package com.lin.ss15exception;

import com.alibaba.fastjson.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Linine
 * @since 2023/8/26 23:22
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123456").roles("admin").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("lisi").password("{noop}123456").roles("user").build());
        return inMemoryUserDetailsManager;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(inMemoryUserDetailsManager()).and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests()
                .mvcMatchers("/demo").hasRole("admin") // 访问demo必须具有admin权限
                .anyRequest().authenticated()
                .and().formLogin()
                .and().exceptionHandling().authenticationEntryPoint(this::commence)
                .accessDeniedHandler(this::handle)
                .and().build();
    }


    // 未认证处理响应数据
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 401);
        jsonObject.put("msg", "请先登录");
        jsonObject.put("data",authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String jsonString = JSONObject.toJSONString(jsonObject);
        response.getWriter().println(jsonString);
    }

    void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("msg", "没有权限");
        jsonObject.put("data",accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        String jsonString = JSONObject.toJSONString(jsonObject);
        response.getWriter().println(jsonString);
    }
}
