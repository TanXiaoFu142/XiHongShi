package com.cws.controller;


import com.cws.utils.PushUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试手动推送
 */
@RestController
public class TestController {

    @RequestMapping("test")
    public String test() {
        return new PushUtil().push();
//        return PushUtil.push();
    }
}
