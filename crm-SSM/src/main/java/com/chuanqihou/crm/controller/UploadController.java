package com.chuanqihou.crm.controller;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.common.UploadBean;
import com.chuanqihou.crm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;
import java.util.UUID;

/**
 * @author 传奇后
 * @date 2023/5/4 14:10
 * @description
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadBean uploadBean;

    @Autowired
    private AccountService accountService;

    /**
     * 上传头像
     * @param headImg 头像文件
     * @return result
     */
    @PostMapping("/uploadHeadImg.do")
    public Result uploadHeadImg(@RequestParam(value = "headImg") MultipartFile headImg){
        try {
            if (headImg == null) {
                return new Result(-1, "文件不能为空");
            }
            //获取原始文件名
            String fileName = headImg.getOriginalFilename();
            //截取原始文件后缀名
            String substring = fileName.substring(fileName.lastIndexOf("."));

            //文件保存路径【前缀】
            String prefix = uploadBean.getBaseUrl();

            //拼接文件名【使用UUID】
            String uuid = UUID.randomUUID().toString();
            fileName = prefix+uuid+substring;

            //判断该路径是否存在
            File f = new File(prefix);
            if (!f.exists()) {
                f.mkdirs();
            }
            //保存文件
            headImg.transferTo(new File(fileName));

            //保存文件地址到数据库
            Result result = accountService.modifyHeadImgById(uuid + substring);

            //返回结果
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(-1, "上传头像失败！");
    }

    /**
     * 上传头像
     * @param file
     * @return
     */
    @PostMapping("/uploadHeadImg2.do")
    public Result uploadHeadImg2(@RequestParam(value = "file") MultipartFile file){
        InputStream in = null;
        OutputStream out = null;
        try {
            //获取原始文件名
            String fileName = file.getOriginalFilename();
            //截取原始文件后缀名
            String substring = fileName.substring(fileName.lastIndexOf("."));
            Random random = new Random();
            //文件保存路径【前缀】
            String prefix = "F:\\outputStream\\img\\";
            //拼接文件名【前缀+随机数+当前时间毫秒数+后缀】
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            System.out.println(fileName);
            //获取文件输入流
            in = file.getInputStream();
            //获取文件输出流
            out = new FileOutputStream(fileName);
            //将文件写入指定目录
            byte[] bytes = new byte[1024];
            int len = -1;
            //边读边写
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            //返回结果
            return new Result();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭连接
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Result(-1, "上传头像失败！");
    }

}
