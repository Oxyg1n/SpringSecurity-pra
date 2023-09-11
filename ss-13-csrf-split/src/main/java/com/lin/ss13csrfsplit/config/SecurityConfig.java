package com.lin.ss13csrfsplit.config;

import com.alibaba.fastjson.JSONObject;
import com.lin.ss13csrfsplit.filter.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Linine
 * @since 2023/8/21 18:52
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123456").roles("admin").build());
        return inMemoryUserDetailsManager;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(inMemoryUserDetailsManager()).and().build();
    }

    @Bean
    public LoginFilter loginFilter(HttpSecurity httpSecurity) throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setUsernameParameter("username");
        loginFilter.setPasswordParameter("password");
        loginFilter.setFilterProcessesUrl("/doLogin");
        loginFilter.setAuthenticationSuccessHandler(this::onAuthenticationSuccess);
        loginFilter.setAuthenticationFailureHandler(this::onAuthenticationFailure);
        loginFilter.setAuthenticationManager(authenticationManager(httpSecurity));
        return loginFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and().addFilterAt(loginFilter(httpSecurity), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(this::commence)
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // 将令牌保存到token中 允许cookie前端获取
                .and().build();
    }


    // 认证成功
    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("msg", "登录成功");
        jsonObject.put("data", authentication);
        response.setContentType("application/json;charset=UTF-8");
        String jsonString = JSONObject.toJSONString(jsonObject);
        response.getWriter().println(jsonString);
    }

    // 认证失败
    void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                 AuthenticationException exception) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 404);
        jsonObject.put("msg", "登录失败");
        jsonObject.put("data", exception);
        response.setContentType("application/json;charset=UTF-8");
        String jsonString = JSONObject.toJSONString(jsonObject);
        response.getWriter().println(jsonString);
    }

    // 未认证处理响应数据
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 403);
        jsonObject.put("msg", "请先登录");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        String jsonString = JSONObject.toJSONString(jsonObject);
        response.getWriter().println(jsonString);
    }
}
