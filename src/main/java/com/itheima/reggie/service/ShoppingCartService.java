package com.itheima.reggie.service;


import com.itheima.reggie.bean.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 加入购物车
     * @param shoppingCart
     * @return
     */
    ShoppingCart add(ShoppingCart shoppingCart);

    /**
     * 查询指定用户的购物车数据
     * @return
     */
    List<ShoppingCart> list();

    /**
     * 清空购物车
     * @return
     */
    int clean();

}
