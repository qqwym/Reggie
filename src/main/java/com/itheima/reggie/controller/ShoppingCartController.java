package com.itheima.reggie.controller;

import com.itheima.reggie.bean.ShoppingCart;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @GetMapping("/list")
    public R<ShoppingCart> getPage(HttpServletRequest request){
        return null;
    }

}
