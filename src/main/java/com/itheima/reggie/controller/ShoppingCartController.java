package com.itheima.reggie.controller;

import com.itheima.reggie.bean.ShoppingCart;
import com.itheima.reggie.common.R;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R getPage(HttpServletRequest request){
        List<ShoppingCart> cartList = shoppingCartService.list();
        return R.success(cartList);
    }

    @DeleteMapping("/clean")
    public R clean(){
        int row = shoppingCartService.clean();
        if (row>0){
            return R.success("成功");
        }
        return R.error("失败");
    }

    @PostMapping("/add")
    public R add(@RequestBody ShoppingCart shoppingCart){
        try {
            ShoppingCart cart = shoppingCartService.add(shoppingCart);
            return R.success(cart);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("失败");
        }
    }

}
