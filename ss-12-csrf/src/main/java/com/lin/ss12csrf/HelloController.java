package com.lin.ss12csrf;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Linine
 * @since 2023/8/21 12:37
 */
@RestController
public class HelloController {

    private int account = 1000;

    @RequestMapping("/demo")
    public String demo() {
        System.out.println("正在转账！！！");
        return "转账成功  剩余金额：" + (account -= 100);
    }
}
