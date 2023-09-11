package com.lin.ss14cros;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/8/21 20:56
 */
@RestController
public class DemoController {

    // @CrossOrigin // 允许跨域访问
    @RequestMapping("/demo")
    public String demo() {
        System.out.println("demo ok!");
        return "demo Ok";
    }
}
