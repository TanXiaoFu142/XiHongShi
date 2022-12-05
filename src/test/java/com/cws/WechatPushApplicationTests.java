package com.cws;

import com.cws.configure.PushConfigure;
import com.cws.configure.UserConfigs;
import com.cws.pojo.Result;
import com.cws.utils.WeatherUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.attribute.standard.PrinterInfo;
import java.util.List;

@SpringBootTest
class WechatPushApplicationTests {

    @Autowired
    public UserConfigs userConfigs;//1定义并获取值


    @Test
    void contextLoads() {
        System.out.println(PushConfigure.getDistrict_id());
        //拿到集合中的数组对象
        System.out.println(userConfigs.getUsers());

    }

}
