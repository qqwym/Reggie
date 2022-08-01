package com.itheima.reggie.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishControlle {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<Page<DIshDto>> getPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String name="";
        R<Page<DIshDto>> result = dishService.getPage(request, page, pageSize,name);
        return result;
    }

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
            DIshDto dishd = JSON.parseObject(request.getInputStream(),DIshDto.class);
            List<DishFlavor> dishf = dishd.getFlavors();
            Dish dish = dishd;
            dish.setSort(0);
            r = dishService.addDish(request, dish, dishf,id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return r;

    }

}
