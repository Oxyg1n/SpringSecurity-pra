package com.lin.ss17dynamicauth.config;

import com.lin.ss17dynamicauth.bean.CustomSecurityMetadataSource;
import com.lin.ss17dynamicauth.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.UrlAuthorizationConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * @author Linine
 * @since 2023/8/30 11:22
 */
@EnableWebSecurity
public class MySecurityConfig {

    private final UserService userService;
    private final CustomSecurityMetadataSource customSecurityMetadataSource;

    public MySecurityConfig(UserService userService, CustomSecurityMetadataSource customSecurityMetadataSource) {
        this.userService = userService;
        this.customSecurityMetadataSource = customSecurityMetadataSource;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userService).and().build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // 1.获取工厂对象
        ApplicationContext applicationContext = httpSecurity.getSharedObject(ApplicationContext.class);
        // 2.设置自定义的url权限处理
        httpSecurity.apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customSecurityMetadataSource);
                        // 是否拒绝公共资源访问
                        object.setRejectPublicInvocations(false);
                        return object;
                    }
                });
        return httpSecurity.formLogin().and().build();
    }
}
