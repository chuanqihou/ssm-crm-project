package com.chuanqihou.crm.service.impl;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.dto.CustomerSearchDto;
import com.chuanqihou.crm.mapper.CustomerMapper;
import com.chuanqihou.crm.mapper.DeptMapper;
import com.chuanqihou.crm.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 传奇后
 * @date 2023/4/25 18:49
 * @description 客户业务实现类
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 保存客户信息
     *
     * @param customerDto 客户参数
     * @return result
     */
    @Override
    @Transactional
    public Result saveCustomer(CustomerDto customerDto) {
        //判断部门编号是否存在
        int deptIsExist = deptMapper.selectDeptIsExist(customerDto);
        if (deptIsExist == 0) {
            return new Result(-1, "部门编号不存在!");
        }
        //判断该客户是否存在
        int customerIsExist = customerMapper.selectCustomerIsExist(customerDto);
        if (customerIsExist != 0) {
            return new Result(-1, "客户已存在!");
        }
        //保存客户信息
        int insertUser = customerMapper.insertUser(customerDto);
        if (insertUser == 0) {
            return new Result(-1, "保存客户信息失败，请联系管理员！");
        }
        return new Result();
    }

    /**
     * 分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Result findCustomerByPage(Integer pageNum, Integer pageSize) {
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //查询数据
        List<Customer> customers = customerMapper.selectCustomerByPage();
        //获得pageInfo对象
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);
        //返回结果
        return new Result(200, "success", customerPageInfo.getList(), customerPageInfo.getTotal());
    }

    /**
     * 根据客户id删除单条客户信息
     *
     * @param id 客户id
     * @return result
     */
    @Override
    @Transactional
    public Result removeCustomer(Long id) {
        //数据效验
        if (id == null || id < 0) {
            return Result.DATE_FORMAT_ERROR;
        }
        //执行删除
        int deleteCustomer = customerMapper.deleteCustomer(id);
        if (deleteCustomer == 0) {
            return new Result(-1, "删除失败");
        }
        return new Result();
    }

    /**
     * 根据客户id批量删除
     *
     * @param ids 客户id 【格式：1,2,3】
     * @return result
     */
    @Override
    @Transactional
    public Result removeManyCustomer(String ids) {
        //数据效验
        if (ids == null || !ids.matches("^\\d+(,\\d+)*$")) {
            return Result.DATE_FORMAT_ERROR;
        }
        //执行删除
        int deleteManyCustomer = customerMapper.deleteManyCustomer(ids);
        //判断删除结果
        if (deleteManyCustomer == 0) {
            return new Result(-1, "删除失败");
        }
        //返回结果
        return new Result();
    }


    /**
     * 根据条件动态搜索
     *
     * @param customerSearchDto 搜索条件实体
     * @return result
     */
    @Override
    public Result findCustomerBySearch(CustomerSearchDto customerSearchDto) {
        //开启分页
        PageHelper.startPage(customerSearchDto.getPageNum(), customerSearchDto.getPageSize());
        //查询数据
        List<Customer> customers = customerMapper.selectCustomersBySearch(customerSearchDto);
        //加入分页
        PageInfo<Customer> customerPageInfo = new PageInfo<>(customers);
        //返回数据
        return new Result(200, "success", customerPageInfo.getList(), customerPageInfo.getTotal());
    }

    /**
     * 修改客户信息
     *
     * @param customerDto
     * @return
     */
    @Override
    @Transactional
    public Result modifyCustomer(CustomerDto customerDto) {
        //判断部门编号是否存在
        int customerIsExist = deptMapper.selectDeptIsExist(customerDto);
        if (customerIsExist == 0) {
            return new Result(-1, "部门不存在");
        }
        //判断客户是否修改
        int i = customerMapper.selectCustomerIsExist(customerDto);
        if (i != 0) {
            return new Result(-1, "请修改");
        }
        //执行修改
        int updateCustomer = customerMapper.updateCustomer(customerDto);
        if (updateCustomer == 0) {
            return new Result(-1, "修改失败");
        }
        //返回结果
        return new Result();
    }

    /**
     * 导出excel
     * @param customerList
     * @param out
     */
    @Override
    public void exportExcel(List<Customer> customerList, OutputStream out) throws IOException {
        //创建工作簿
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = hssfWorkbook.createSheet("客户信息管理");
        //第一行【表名】
        HSSFRow row1 = sheet.createRow(0);
        //第一列
        HSSFCell cell1 = row1.createCell(0);
        //内容
        cell1.setCellValue("客户信息表");

        //第二行【标题】
        HSSFRow row2 = sheet.createRow(1);
        //准备数据
        String[] row2Value = {"编号", "姓名", "生日","性别","手机号","薪资","职位","地址","部门名称","部门地址"};
        //循环创建单元格并设置内容
        for (int i = 0; i < row2Value.length; i++) {
            HSSFCell cell2 = row2.createCell(i);
            cell2.setCellValue(row2Value[i]);
        }

        //第三行【数据】
        for (int i = 0; i < customerList.size(); i++) {
            HSSFRow rowData = sheet.createRow(i + 2);
            rowData.createCell(0).setCellValue(customerList.get(i).getId());
            rowData.createCell(1).setCellValue(customerList.get(i).getCustomerName());
            rowData.createCell(2).setCellValue(customerList.get(i).getBirthday());
            rowData.createCell(3).setCellValue("1".equals(customerList.get(i).getSex())?"男":"女");
            rowData.createCell(4).setCellValue(customerList.get(i).getTel());
            rowData.createCell(5).setCellValue(customerList.get(i).getSal());
            String profession = customerList.get(i).getProfession();
            rowData.createCell(6).setCellValue("1".equals(profession)?"java软件开发工程师":"2".equals(profession)?"前端开发工程师":"软件测试工程师");
            rowData.createCell(7).setCellValue(customerList.get(i).getAddress());
            rowData.createCell(8).setCellValue(customerList.get(i).getDept().getName());
            rowData.createCell(9).setCellValue(customerList.get(i).getDept().getLoc());
        }
        //导出
        hssfWorkbook.write(out);
    }

    @Override
    public Result findSexCount() {

        List<Map<String, String>> maps = customerMapper.selectSexCount();
        System.out.println(maps);
        return new Result(200,"success",maps);
    }
}
