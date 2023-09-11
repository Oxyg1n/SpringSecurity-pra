package com.lin.ss14crossecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Linine
 * @since 2023/8/26 21:55
 */
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .and().cors() // 跨域处理方案
                .configurationSource(corsConfiguration())
                .and().csrf().disable().build();
    }

    private CorsConfigurationSource corsConfiguration(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); // 这个得加上，一些复杂的请求方式会带有header，不加上跨域会失效。
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader("*");
        corsConfiguration.setMaxAge(3600L);// 设置响应时间
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);// 允许所有路径进行跨域访问
        return source;
    }
}
