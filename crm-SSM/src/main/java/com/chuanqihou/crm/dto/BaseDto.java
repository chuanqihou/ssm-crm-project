package com.chuanqihou.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 传奇后
 * @date 2023/4/26 14:21
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDto {

    private Integer pageNum = 1;
    private Integer pageSize = 5;

}
