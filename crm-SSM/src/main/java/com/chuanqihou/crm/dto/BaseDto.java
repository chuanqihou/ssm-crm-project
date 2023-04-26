package com.chuanqihou.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 传奇后
 * @date 2023/4/26 14:21
 * @description
 */
@Data
@AllArgsConstructor
public class BaseDto {

    private Integer pageNum;
    private Integer pageSize;

    public BaseDto() {
        this.pageNum = 1;
        this.pageSize = 5;
    }
}
