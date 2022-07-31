package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.bean.Employee;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

    R<Employee> login(HttpServletRequest request, @RequestBody Employee employee);

    R<Page<Employee>> getPage(HttpServletRequest request, int page, int pageSize, String name,Long id);

    R<Integer> addEmployee(HttpServletRequest request, Employee employee,long id);

    R<Integer> editEmployee(HttpServletRequest request,Employee employee,long id);


}
