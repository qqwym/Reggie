package com.itheima.reggie.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.zip.DataFormatException;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/login")
    public String getIndex(){
        return "backend/page/login/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public R<Employee> login(HttpServletRequest request) throws IOException {
        Employee employee = JSON.parseObject(request.getInputStream(), Employee.class);
        R<Employee> result = employeeService.login(request, employee);
        return result;
    }

    @GetMapping("/page")
    public R<Page<Employee>> getPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        R<Page<Employee>> result = employeeService.getPage(request, page, pageSize,name,null);
        return result;
    }

    @PostMapping("")
    @ResponseBody
    //@Transactional(rollbackFor = Exception.class)
    public R<Integer> addEmployee(HttpServletRequest request) throws IOException {
        Employee data = JSON.parseObject(request.getInputStream(), Employee.class);
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(LocalDateTime.now());
        data.setPassword("123456");
        HttpSession session = request.getSession();
        if (session==null){
            return R.error("登录已失效");
        }
        Object employee = session.getAttribute("employee");
        try {
            long id = Long.parseLong(employee.toString());
            R<Integer> result = employeeService.addEmployee(request,data,id);
            return result;
        }catch (SqlSessionException e){
            return R.error("登录已失效");
        }
    }

    @PutMapping("")
    public R<Integer> editEmployee(HttpServletRequest request,@RequestBody Employee employee) throws IOException {
        employee.setUpdateTime(LocalDateTime.now());
        HttpSession session = request.getSession();
        if (session==null){

            return R.error("登录已失效");
        }
        Object oo = session.getAttribute("employee");
        try {
            long id = Long.parseLong(oo.toString());
            R<Integer> result = employeeService.editEmployee(request,employee,id);
            return result;
        }catch (SqlSessionException e){
            return R.error("登录已失效");
        }catch (NullPointerException e){
            return R.error("参数含空");
        }
    }

}  