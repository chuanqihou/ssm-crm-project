package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Dept;
import com.chuanqihou.crm.dto.CustomerDto;

/**
 * @author 传奇后
 * @date 2023/4/25 18:46
 * @description
 */
public interface DeptMapper {

    /**
     * 根据部门编号查询部门是否存在
     * @param customerDto
     * @return
     */
    int selectDeptIsExist(CustomerDto customerDto);

    Dept selectDeptById(Integer deptId);

}
