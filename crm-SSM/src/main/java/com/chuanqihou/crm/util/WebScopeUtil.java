package com.chuanqihou.crm.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 传奇后
 * @date 2023/4/26 15:53
 * @description web作用域获取工具类
 */
public class WebScopeUtil {

    /**
     * 获取HttpServletRequest
     * @return
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取HttpSession
     * @return
     */
    public static HttpSession getHttpSession() {
        return getRequest().getSession();
    }

    /**
     * 获取servletContext
     * @return
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

}
