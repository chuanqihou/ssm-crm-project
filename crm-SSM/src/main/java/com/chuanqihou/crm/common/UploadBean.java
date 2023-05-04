package com.chuanqihou.crm.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/5/4 14:07
 * @description 文件上传位置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadBean {

    /**
     * 文件默认存储位置
     */
    private String baseUrl;

}
