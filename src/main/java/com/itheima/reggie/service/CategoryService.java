package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

public interface CategoryService extends IService<Category> {

    Page<Category> getPage(HttpServletRequest request, int page, int pageSize,int type);

    Integer addCategory(HttpServletRequest request, Category category,long id)throws SQLIntegrityConstraintViolationException;

    Integer editCategory(HttpServletRequest request,Category category,long id);

    Integer deleteCategory(long id);

}
