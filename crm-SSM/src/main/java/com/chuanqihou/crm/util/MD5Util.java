package com.chuanqihou.crm.util;

import cn.hutool.crypto.SecureUtil;

/**
 * @author 传奇后
 * @date 2023/4/27 8:39
 * @description MD5加密
 */
public class MD5Util {

    private static String init(String txt) {
        if (txt == null || txt.trim().equals("")) {
            return txt;
        }
        return SecureUtil.md5(txt);
    }

    public static String finalMd5(String txt) {
        return init(init(init(init(init(txt)+"c")+"q")+"h")+"cqh");
    }

    public static void main(String[] args) {
        String s = finalMd5("123456aA.");
        System.out.println(s);
    }

}
