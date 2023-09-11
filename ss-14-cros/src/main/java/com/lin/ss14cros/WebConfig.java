package com.lin.ss14cros;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Linine
 * @since 2023/8/21 21:10
 */
@Configuration // 仅供演示
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 对哪些请求进行跨域处理
                .allowCredentials(false)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .maxAge(3600);
    }
}
