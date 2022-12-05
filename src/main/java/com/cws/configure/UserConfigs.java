package com.cws.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName : UserConfigs
 * @Description : TODO
 * @Author : Tan.
 * @Date: 2022-09-21 22:51
 */
@Component
@ConfigurationProperties("user-info")
public class UserConfigs {

    private static List<User> users;

    public static List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        UserConfigs.users = users;
    }
}
