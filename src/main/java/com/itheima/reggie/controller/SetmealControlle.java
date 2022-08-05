package com.itheima.reggie.controller;

import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.SetmealDish;
import com.itheima.reggie.bean.utilBean.DishDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.bean.utilBean.SetmealDto;
import com.itheima.reggie.common.R;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
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
@RequestMapping("/setmeal")
public class SetmealControlle {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;


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

    @GetMapping("/{id}")
    public R<SetmealDto> getSetmeal(@PathVariable long id,HttpServletRequest request){
        R<SetmealDto> setmeal = setmealService.getSetmeal(request, id);
        return setmeal;
    }

    @PutMapping("")
    public R<Integer> editSetmeal(@RequestBody SetmealDto setmealDto,HttpServletRequest request){
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        HttpSession session = request.getSession();
        Object employee = session.getAttribute("employee");
        long id =Long.parseLong(employee.toString());
        Setmeal setmeal = setmealDto;
        R<Integer> r = setmealService.editSetmeal(request, setmealDishes, setmeal,id);
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
        return setmealService.editStatus(request, ids);
    }

    @PostMapping("/status/1")
    public R<Integer> editStatus1(HttpServletRequest request) throws IOException {
        String strids = request.getParameter("ids");
        String[] sids = strids.split(",");
        List<Long> ids = new ArrayList<>();
        for (String sid : sids) {
            ids.add(Long.valueOf(sid));
        }
        return setmealService.editStatus1(request, ids);
    }

    @DeleteMapping("")
    public R<Integer> deleteSt(HttpServletRequest request){
        String id = request.getParameter("ids");
        String[] s = id.split(" ");
        List<Long> ids = new ArrayList<>();
        for (String sid : s) {
            ids.add(Long.valueOf(sid));
        }
        return setmealService.delete(request,ids);
    }

    @GetMapping("/list")
    public R getList(HttpServletRequest request){
        String categoryId = request.getParameter("categoryId");
        String status = request.getParameter("status");
        R<List<DishDto>> list = dishService.getList(Long.parseLong(categoryId), request, Long.parseLong(status));
        return list;
    }

}
