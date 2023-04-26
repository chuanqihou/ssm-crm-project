package com.chuanqihou.crm.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Result 返回结果类
 * @author 传奇后
 * @date 2023/4/25 17:22
 * @description
 */
@Data
@AllArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;
    private Integer total;

    public static final Result DATE_FORMAT_ERROR = new Result(-1, "格式错误");

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result() {
        this.code = 200;
    }
}
