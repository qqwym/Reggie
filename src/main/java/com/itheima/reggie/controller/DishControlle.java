package com.itheima.reggie.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.utilBean.DishDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishControlle {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<Page<DishDto>> getPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        R<Page<DishDto>> result = dishService.getPage(request, page, pageSize,name);
        return result;
    }

    //新增
    @PostMapping("")
    @ResponseBody
    public R<Integer> addDish(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        if (session==null){
            return R.error("登录已失效");
        }
        Object oo = session.getAttribute("employee");
        R<Integer> r = null;
        try {
            long id = Long.parseLong(oo.toString());
            if (request.getParameter("flavors")==null){
                Dish dish = JSON.parseObject(request.getInputStream(),Dish.class);
                dish.setSort(0);
                r = dishService.addDish(request,dish,null,id);
            }else {
                DishDto dishd = JSON.parseObject(request.getInputStream(), DishDto.class);
                List<DishFlavor> dishf = dishd.getFlavors();
                Dish dish = dishd;
                dish.setSort(0);
                r = dishService.addDish(request, dish, dishf,id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;

    }

    //修改
    @PutMapping
    @ResponseBody
    public R<Integer> editDish(@RequestBody DishDto dIshDto, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        if (session==null){
            return R.error("登录已失效");
        }
        Object employee = session.getAttribute("employee");
        R<Integer> r = null;
        try {
            long id = Long.parseLong(employee.toString());
            if (dIshDto.getFlavors()==null){
                Dish dish = dIshDto;
                dish.setSort(0);
                r = dishService.editDish(request, dish, null, id);
            }else {
                Dish dish = dIshDto;
                List<DishFlavor> dishf = dIshDto.getFlavors();
                dish.setSort(0);
                r = dishService.editDish(request, dish, dishf, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //HttpSession session = request.getSession();
        //if (session==null){
        //    return R.error("登录已失效");
        //}
        //Object oo = session.getAttribute("employee");
        //System.out.println("request = " + request);
        //R<Integer> r = null;
        //try {
        //    long id = Long.parseLong(oo.toString());
        //    if (request.getParameter("flavors")==null){
        //        //Dish dish = JSON.parseObject(request.getInputStream(),Dish.class);
        //        //dish.setSort(0);
        //        //r = dishService.editDish(request,dish,null,id);
        //        DIshDto dishd = JSON.parseObject(request.getInputStream(),DIshDto.class);
        //        List<DishFlavor> dishf = dishd.getFlavors();
        //        Dish dish = dishd;
        //        dish.setSort(0);
        //        r = dishService.editDish(request, dish, dishf,id);
        //    }else {
        //        //DIshDto dishd = JSON.parseObject(request.getInputStream(),DIshDto.class);
        //        //List<DishFlavor> dishf = dishd.getFlavors();
        //        //Dish dish = dishd;
        //        //dish.setSort(0);
        //        //r = dishService.editDish(request, dish, dishf,id);
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        return r;
    }

    @PostMapping("/status/0")
    public R<Integer> editstatus(HttpServletRequest request) throws IOException {
        String strids = request.getParameter("ids");
        String[] sids = strids.split(",");
        List<Long> ids = new ArrayList<>();
        for (String sid : sids) {
            ids.add(Long.valueOf(sid));
        }
        return dishService.editStatus(request, ids);
    }

    @PostMapping("/status/1")
    public R<Integer> editStatus1(HttpServletRequest request) throws IOException {
        String strids = request.getParameter("ids");
        String[] sids = strids.split(",");
        List<Long> ids = new ArrayList<>();
        for (String sid : sids) {
            ids.add(Long.valueOf(sid));
        }
        return dishService.editStatus1(request, ids);
    }

    @DeleteMapping("")
    public R<Integer> delete(HttpServletRequest request) throws IOException {
        String strids = request.getParameter("ids");
        String[] sids = strids.split(",");
        List<Long> ids = new ArrayList<>();
        for (String sid : sids) {
            ids.add(Long.valueOf(sid));
        }
        return dishService.delete(request, ids);
    }

    @GetMapping("/list")
    public R<List<DishDto>> gitList(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("categoryId"));
        R<List<DishDto>> r = dishService.getList(id, request,1);
        return r;
    }


}
