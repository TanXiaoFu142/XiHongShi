package com.cws.configure;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName : User
 * @Description : TODO
 * @Author : Tan.
 * @Date: 2022-09-21 22:49
 */
@Data
public class User implements Serializable {

    private String name;
    private String birthday;

}
