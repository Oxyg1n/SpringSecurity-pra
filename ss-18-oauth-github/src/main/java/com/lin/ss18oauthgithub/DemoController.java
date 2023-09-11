package com.lin.ss18oauthgithub;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/9/1 22:03
 */
@RestController
public class DemoController {

    @GetMapping("/hello")
    public DefaultOAuth2User hello() {
        System.out.println("hello ");
        // 打印认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (DefaultOAuth2User) authentication.getPrincipal();
    }

}
