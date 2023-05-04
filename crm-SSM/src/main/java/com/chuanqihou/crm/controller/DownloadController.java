package com.chuanqihou.crm.controller;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.common.UploadBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 传奇后
 * @date 2023/5/4 14:50
 * @description
 */
@Controller
@RequestMapping("/download")
public class DownloadController {

    @Autowired
    private UploadBean uploadBean;

    @RequestMapping("/headImg.do")
    public void downloadHeadImg(HttpServletResponse response,String imgUrl) {
        InputStream in = null;
        ServletOutputStream os = null;
        try {
            //数据效验
            if (imgUrl == null) {
                return;
            }
            //获取文件路径
            String url = uploadBean.getBaseUrl() + imgUrl;
            //判断该路径是否存在
            File file = new File(url);
            if (!file.exists()) {
                return;
            }
            //获取输入流
            in = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = -1;
            //获取输出流
            os = response.getOutputStream();
            //边读边写
            while ((len = in.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
