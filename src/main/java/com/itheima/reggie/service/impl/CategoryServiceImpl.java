package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


@Transactional
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper mapper;

    /**
     * 员工查询
     * @param request
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Category> getPage(HttpServletRequest request, int page, int pageSize,int type) {
        int setoff = (page-1)*pageSize;
        List<Category> employees = mapper.getPage(setoff,pageSize,type);
        int total = mapper.getTotal();
        Page<Category> page1 = new Page<>(employees,total);
        return page1;
    }

    @Override
    public Integer addCategory(HttpServletRequest request, Category category, long id) throws SQLIntegrityConstraintViolationException {
        Integer l = Math.toIntExact(mapper.addCategory(category, id));
        return l;
    }

    @Override
    public Integer editCategory(HttpServletRequest request, Category category, long id) {
        Integer l = Math.toIntExact(mapper.editCategory(category, id));
        return l;
    }

    @Override
    public Integer deleteCategory(long id) {
        Integer l = Math.toIntExact(mapper.deleteById(id));
        return l;
    }


}
