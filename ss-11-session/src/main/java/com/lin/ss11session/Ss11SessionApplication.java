package com.lin.ss11session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
public class Ss11SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ss11SessionApplication.class, args);
    }

}
