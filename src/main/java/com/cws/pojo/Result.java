package com.cws.pojo;

import lombok.Data;

/**
 * Result 响应数据对象
 *
 * @author cws
 * @date 2022/8/24 9:25
 */
@Data
public class Result {
    private String code;
    private String message;
    private Object data;
}
