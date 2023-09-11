package com.lin.ss16auth;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Linine
 * @since 2023/8/27 0:28
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true, // 后面两种使用比较少
        securedEnabled = true,
        jsr250Enabled = true
)
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        // 用户的role会自动加上ROLE_ 所以他的完整权限是ROLE_ADMIN
        inMemoryUserDetailsManager.createUser(User.withUsername("root").password("{noop}123456").roles("ADMIN", "USER").build()); // 访问INFO出现403
        inMemoryUserDetailsManager.createUser(User.withUsername("user").password("{noop}123456").roles("USER").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("win7").password("{noop}123456").authorities("READ_INFO").build()); // 注意这里是写权限
        return inMemoryUserDetailsManager;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsManager()).and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // 基于URL的角色权限控制
        return httpSecurity.authorizeRequests()
                .mvcMatchers("/admin").hasRole("ADMIN") // 角色底层会叫上前缀ROLE_
                .mvcMatchers("/user").hasRole("USER")
                .mvcMatchers("/info").hasAuthority("READ_INFO") // 权限底层不会加上前缀 所以要手动加上自己定义的前缀
                .mvcMatchers(HttpMethod.POST, "/test").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin()
                .and().build();
    }
}
