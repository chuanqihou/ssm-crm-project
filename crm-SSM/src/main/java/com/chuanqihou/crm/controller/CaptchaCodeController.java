package com.chuanqihou.crm.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import com.chuanqihou.crm.common.Constants;
import com.chuanqihou.crm.util.WebScopeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 传奇后
 * @date 2023/4/26 15:50
 * @description
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaCodeController {

    /**
     * 获取验证码接口
     * @param response
     */
    @PostMapping("/getCaptcha.do")
    public void getCaptcha(HttpServletResponse response) {
        //获取验证码
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(150, 45, 4);
        //获取session对象
        HttpSession session = WebScopeUtil.getHttpSession();
        //将验证码存放在session中
        session.setAttribute(Constants.CODE_SESSION_KEY, gifCaptcha.getCode());
        //输出验证码
        try {
            gifCaptcha.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
