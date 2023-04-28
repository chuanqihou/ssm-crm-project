package com.chuanqihou.crm.mapper;

import com.chuanqihou.crm.domain.Dept;
import com.chuanqihou.crm.dto.CustomerDto;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/25 18:46
 * @description dept 映射接口
 */
public interface DeptMapper {

    /**
     * 根据部门编号查询部门是否存在
     * @param customerDto  只需要部门编号
     * @return 0 不存在 大于0 存在
     */
    int selectDeptIsExist(CustomerDto customerDto);

    /**
     * 根据部门编号查询部门信息
     * @param deptId    部门id
     * @return 部门信息
     */
    Dept selectDeptById(Integer deptId);

    /**
     * 查询所有部门信息
     * @return  部门信息
     */
    List<Dept> selectAllDept();

}
