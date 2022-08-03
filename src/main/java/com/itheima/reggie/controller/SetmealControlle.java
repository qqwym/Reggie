package com.itheima.reggie.controller;

import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.SetmealDish;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.bean.utilBean.SetmealDto;
import com.itheima.reggie.common.R;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealControlle {

    @Autowired
    private SetmealService setmealService;


    @GetMapping("/page")
    public R<Page<SetmealDto>> getPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        R<Page<SetmealDto>> result = setmealService.getPage(request, page, pageSize,name);
        return result;
    }

    @PostMapping("")
    public R<Integer> addSetmeal(@RequestBody SetmealDto setmealDto, HttpServletRequest request){
        HttpSession session = request.getSession();
        Object employee = session.getAttribute("employee");
        if (employee==null){
            return R.error("未登录");
        }
        long id =Long.parseLong(employee.toString());
        Setmeal setmeal = setmealDto;
        List<SetmealDish> list = setmealDto.getSetmealDishes();
        R<Integer> r = setmealService.addSetmeal(request, setmeal, list, id);
        return r;
    }


}
