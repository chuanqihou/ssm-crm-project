package com.chuanqihou.crm.exception;

import com.chuanqihou.crm.common.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author 传奇后
 * @date 2023/4/26 10:38
 * @description 全局异常处理
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 记录日志
     */
    private Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public Result handlerException(Exception e) {
        //控制台打印错误信息
        e.printStackTrace();
        //记录日志
        logger.debug(e);
        //返回结果
        return new Result(-1, "后台请求出错!");
    }

}
