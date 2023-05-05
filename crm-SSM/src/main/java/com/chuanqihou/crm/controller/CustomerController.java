package com.chuanqihou.crm.controller;

import com.chuanqihou.crm.common.Result;
import com.chuanqihou.crm.domain.Customer;
import com.chuanqihou.crm.dto.BaseDto;
import com.chuanqihou.crm.dto.CustomerDto;
import com.chuanqihou.crm.dto.CustomerSearchDto;
import com.chuanqihou.crm.service.CustomerService;
import com.chuanqihou.crm.util.DataValidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author 传奇后
 * @date 2023/4/25 19:16
 * @description 客户控制器
 */
@Transactional
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 添加客户控制器
     * @param customerDto   客户信息
     * @param bindingResult
     * @return
     */
    @PostMapping("/addCustomer.do")
    public Result addCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult) {
        Result result = DataValidUtil.dataValid(bindingResult);
        if (result != null) {
            return result;
        }
        //调用业务层并返回
        return customerService.saveCustomer(customerDto);
    }

    /**
     * 分页查询
     * @param baseDto
     * @return
     */
    @PostMapping("/getCustomerByPage.do")
    public Result getCustomerByPage(BaseDto baseDto) {
        //调用业务层方法并返回数据
        return customerService.findCustomerByPage(baseDto.getPageNum(),baseDto.getPageSize());
    }

/*    *//**
     * 分页查询客户信息
     * @param pageNum   第几页
     * @param pageSize  每页几条数据【非必须，默认值为5】
     * @return  result
     *//*
    @PostMapping("/getCustomerByPage.do")
    public Result getCustomerByPage(Integer pageNum,@RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize) {
        return customerService.findCustomerByPage(pageNum, pageSize);
    }*/

    /**
     * 单条删除
     * @param id
     * @return
     */
    @PostMapping("/cutOneCustomer.do")
    public Result cutOneCustomer(Long id) {
        return customerService.removeCustomer(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/cutManyCustomer.do")
    public Result cutOneCustomer(String ids) {
        return customerService.removeManyCustomer(ids);
    }

    /**
     * 根据条件查询搜索customer
     * @param customerSearchDto 条件实体
     * @param bindingResult
     * @return
     */
    @PostMapping("/getCustomerBySearch.do")
    public Result getCustomerBySearch(@Valid CustomerSearchDto customerSearchDto,BindingResult bindingResult) {
        //数据效验
        System.out.println(customerSearchDto);
        Result result = DataValidUtil.dataValid(bindingResult);
        if (result != null) {
            return result;
        }
        //调用业务方法并返回结果
        return customerService.findCustomerBySearch(customerSearchDto);
    }

    /**
     * 修改客户信息
     * @param customerDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/editCustomer.do")
    public Result editCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult) {
        System.out.println(customerDto);
        //数据效验
        Result result = DataValidUtil.dataValid(bindingResult);
        if (result != null) {
            return result;
        }
        if (customerDto.getId() == null || customerDto.getId() <= 0) {
            return Result.DATE_FORMAT_ERROR;
        }
        //执行业务方法并返回
        return customerService.modifyCustomer(customerDto);
    }

    /**
     * 导出客户信息为excel文件
     * @param response
     * @throws IOException
     */
    @PostMapping("/download.do")
    public void exportExcel(HttpServletResponse response) throws IOException {
        //获取数据
        Result customerByPage = customerService.findCustomerByPage(1, 0);

        List<Customer> customers = (List<Customer>) customerByPage.getData();

        //设置响应头
        response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode("客户信息.xls","utf-8"));
        //response.setHeader("Content-Disposition","attachment;fileName="+ new String("客户信息.xls".getBytes(StandardCharsets.UTF_8)));
        response.setContentType("application/x-excel;charset=utf-8");
        //下载
        ServletOutputStream out = response.getOutputStream();
        customerService.exportExcel(customers,out);
        //关闭
        out.close();
    }

    @RequestMapping("/getSexCount.do")
    public Result getSexCount() {
        return customerService.findSexCount();
    }

/*    @GetMapping("/download.do")
    public void downloadCustomer(HttpServletResponse response) {
        //定义输出Excel文件路径和名称
        String fileName = "customer.xlsx";
        //定义Excel表头
        List<String> headList = Arrays.asList("编号", "姓名", "生日","性别","手机号","薪资","职位","地址","部门");
        List<List<String>> head = new ArrayList<>();
        head.add(headList);
        //定义Excel数据
        //List<List<Object>> dataList = getDataListFromDatabase();
        Result customerByPage = customerService.findCustomerByPage(1, 0);
        //创建ExcelWriter对象
        ExcelWriter excelWriter = EasyExcel.write(fileName).build();
        //创建第一个sheet
        WriteSheet sheet = EasyExcel.writerSheet(0, "客户信息").head(head).build();
        //将数据写入sheet
        List<Customer> customers = (List<Customer>) customerByPage.getData();
        for (Customer customer : customers) {
            customer.setDept(null);
        }
        ExcelWriter write = excelWriter.write(customers, sheet);
        //关闭流
        excelWriter.finish();

        //设置HTTP响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (OutputStream outputStream = response.getOutputStream()) {
            FileInputStream inputStream = new FileInputStream(new File(fileName));
            byte[] buffer = new byte[8192];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}
