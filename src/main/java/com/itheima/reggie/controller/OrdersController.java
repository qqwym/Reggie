package com.itheima.reggie.controller;

import com.itheima.reggie.bean.Orders;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    public R<Page<Orders>> getPage(HttpServletRequest request){
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        String name = request.getParameter("name");
        R<Page<Orders>> result = ordersService.getPage(request, page, pageSize,name);
        return result;
    }

}
