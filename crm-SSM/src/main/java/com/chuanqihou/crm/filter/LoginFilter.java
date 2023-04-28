package com.chuanqihou.crm.filter;

import com.chuanqihou.crm.common.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 传奇后
 * @date 2023/4/27 14:09
 * @description 登录过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //获取HttpServletRequest和HttpServletResponse
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取登录凭证
        HttpSession session = request.getSession();
        Object account = session.getAttribute(Constants.ACCOUNT_SESSION_KEY);
        //获取请求URI
        String requestURI = request.getRequestURI();
        //放行
        if (account != null || requestURI.endsWith("/login.html")
                || requestURI.endsWith("captcha/getCaptcha.do")
                || requestURI.endsWith("getAccountByLogin.do")
                || requestURI.contains("resources")) {
            filterChain.doFilter(request,response);
            return;
        }
        //重定向回登录页面
        response.sendRedirect(request.getContextPath()+"/views/login.html");
    }
}
