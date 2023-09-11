package com.lin.ss11session.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/8/21 6:35
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo(){
        return "受限资源访问成功";
    }
}
