package com.chuanqihou.crm.util;

import com.chuanqihou.crm.common.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/26 16:45
 * @description
 */
public class DataValidUtil {

    /**
     * 数据效验方法
     * @param bindingResult 数据绑定结果
     * @return 通过效验:null 未通过:result
     */
    public static Result dataValid(BindingResult bindingResult) {
        //数据效验
        if (bindingResult.hasErrors()) {
            //取出所有
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                //返回第一个
                return new Result(-1, fieldError.getDefaultMessage());
            }
        }
        //通过效验则返回null
        return null;
    }

}
