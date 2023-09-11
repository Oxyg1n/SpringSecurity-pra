package com.lin.ss19jwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/9/5 10:22
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo(){
        return "demo";
    }
}
