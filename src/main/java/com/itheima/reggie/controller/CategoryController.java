package com.itheima.reggie.controller;


import com.alibaba.fastjson.JSON;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.utilBean.ListS;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取数据
     * @param request
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> getPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int type = 0;
        Page<Category> result = categoryService.getPage(request, page, pageSize,type);
        return R.success(result);
    }

    /**
     * 添加
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("")
    public R<Integer> addCategory(HttpServletRequest request) throws IOException {
        Category data = JSON.parseObject(request.getInputStream(), Category.class);
        data.setCreateTime(LocalDateTime.now());
        data.setUpdateTime(LocalDateTime.now());
        HttpSession session = request.getSession();
        data.setId((long) 123123);
        if (session==null){
            return R.error("登录已失效");
        }
        Object employee = session.getAttribute("employee");
        try {
            long id = Long.parseLong(employee.toString());
            Integer result = categoryService.addCategory(request,data,id);
            return R.success(result);
        }catch (SqlSessionException e){
            return R.error("登录已失效");
        }catch (SQLIntegrityConstraintViolationException e){
            return R.error("id已存在");
        }catch (Exception e){
            return R.error("错误");
        }
    }

    /**
     * 修改
     * @param request
     * @param category
     * @return
     */
    @PutMapping("")
    public R<Integer> editCategory(HttpServletRequest request,@RequestBody Category category){
        category.setUpdateTime(LocalDateTime.now());
        HttpSession session = request.getSession();
        if (session==null){
            return R.error("登录已失效");
        }
        Object oo = session.getAttribute("employee");
        try {
            long id = Long.parseLong(oo.toString());
            Integer result = categoryService.editCategory(request,category,id);
            return R.success(result);
        }catch (SqlSessionException e){
            return R.error("登录已失效");
        }catch (NullPointerException e){
            return R.error("参数含空");
        }
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @DeleteMapping("")
    public R<Integer> deleteCategory(HttpServletRequest request){
        Long id = Long.valueOf(request.getParameter("id"));
        HttpSession session = request.getSession();
        if (session==null){
            return R.error("登录已失效");
        }
        Object oo = session.getAttribute("employee");
        try {
            //long id1 = Long.parseLong(oo.toString());
            Integer result = categoryService.deleteCategory(id);
            return R.success(result);
        }catch (Exception e){
            return R.error("错误");
        }
    }
    @GetMapping("/list")
    public R<List<ListS>> getList(HttpServletRequest request){
        int type = Integer.parseInt(request.getParameter("type"));
        HttpSession session = request.getSession();
        if (session==null){
            return R.error("登录已失效");
        }
        Object oo = session.getAttribute("employee");
        try {
            long id = Long.parseLong(oo.toString());
            Page<Category> r = categoryService.getPage(request,0,1,type);
            List<Category> rs = r.getRecords();
            List<ListS> res =new ArrayList<>();
            for (Category category : rs) {
                res.add(new ListS(category.getName(),category.getId()));
            }
            return R.success(res);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("e");
        }
    }


}
