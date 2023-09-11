package com.lin.ss19jwt.config;

import com.lin.ss19jwt.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Linine
 * @since 2023/9/6 11:30
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor())
                // 放通静态资源和所有登录接口
                .excludePathPatterns("/login")
                .addPathPatterns("/**");

    }
}
