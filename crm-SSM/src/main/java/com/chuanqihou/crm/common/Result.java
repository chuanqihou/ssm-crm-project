package com.chuanqihou.crm.common;

import lombok.AllArgsConstructor;
import lombok.Data;

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
    private Long total;

    public static final Result DATE_FORMAT_ERROR = new Result(-1, "格式错误");

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
        this.code = 200;
        this.msg = "success";
    }
}
