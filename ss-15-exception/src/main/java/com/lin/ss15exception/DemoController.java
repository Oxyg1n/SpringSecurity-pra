package com.lin.ss15exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/8/26 23:24
 */
@RestController
public class DemoController {

    @RequestMapping("/demo")
    public String demo() {
        return "demo.Ok";
    }
}
