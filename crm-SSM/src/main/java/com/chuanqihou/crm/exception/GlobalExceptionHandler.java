package com.chuanqihou.crm.exception;

import com.chuanqihou.crm.common.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author 传奇后
 * @date 2023/4/26 10:38
 * @description
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public Result handlerException(Exception e) {
        e.printStackTrace();
        logger.debug(e);
        return new Result(-1, "后台请求出错!");
    }

}
