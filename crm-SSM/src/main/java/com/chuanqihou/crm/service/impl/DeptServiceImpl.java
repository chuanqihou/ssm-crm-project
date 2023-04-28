package com.chuanqihou.crm.service.impl;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Dept;
import com.chuanqihou.crm.mapper.DeptMapper;
import com.chuanqihou.crm.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/27 20:37
 * @description
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有部门信息
     * @return result
     */
    @Override
    public Result findAllDept() {
        List<Dept> deptList = deptMapper.selectAllDept();
        System.out.println(deptList);
        return new Result(200, "success", deptList);
    }
}
