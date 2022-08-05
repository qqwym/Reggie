package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.itheima.reggie.bean.ShoppingCart;
import com.itheima.reggie.bean.util.BaseContext;
import com.itheima.reggie.mapper.ShoppingCartMapper;
import com.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 加入购物车
     *   1. 加入购物车其实也是一个添加的动作，但是不能直接认为添加就完事了。
     *   2. 添加之前，先查询购物车有没有这件菜品|套餐
     *      2.1 按照user_id 和 dish_id | setmeal_id 来查询。
     *      2.2 如果查询出来，有数据，那么就执行更新操作。
     *          a. 就更新数量即可。 让原有的数量 + 1
     *      2.3 如果没有查询出来，那么表示这是第一次添加的数据
     *          a. 设置数量是 1.
     *
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart add(ShoppingCart shoppingCart) {  // 张三 ， 红烧肉
        //1. 先查询【按照用户id 和 菜品id | 套餐id】

        //1.1 构建条件对象
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();

        //1.2 追加条件
        //a. 设置用户id
        lqw.eq(ShoppingCart::getUserId , BaseContext.getCurrentId());

        //b. 设置菜品或者套餐的id
        if(shoppingCart.getDishId() != null){
            //如果从页面过来的是菜品，那么就设置菜品的id
            lqw.eq(ShoppingCart::getDishId , shoppingCart.getDishId());
        }else{
            //如果从页面过来的是套餐，那么就设置套餐的id
            lqw.eq( ShoppingCart::getSetmealId , shoppingCart.getSetmealId());
        }

        //1.3 执行查询
        ShoppingCart cartInDB = shoppingCartMapper.selectOne(lqw);

        //2 判断到底有还是没有数据
        if(cartInDB == null){
            // 表明在数据库里面这个用户没有添加过这个菜品|套餐 :: 添加的操作

            //补充数据
            shoppingCart.setUserId(BaseContext.getCurrentId()); //用户id
            shoppingCart.setCreateTime(LocalDateTime.now()); //创建时间
            shoppingCart.setNumber(1); //设置数量
            //添加
            shoppingCartMapper.insert(shoppingCart);

            return shoppingCart;

        }else{
            // 表明在数据库里面这个用户有添加过这个菜品|套餐 :: 更新的操作
            cartInDB.setNumber(cartInDB.getNumber() + 1);

            shoppingCartMapper.updateById(cartInDB);

            return cartInDB;
        }




    }

    /**
     * 查询指定用户的购物车数据
     *
     * @return
     */
    @Override
    public List<ShoppingCart> list() {

        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId , BaseContext.getCurrentId());

        return shoppingCartMapper.selectList(lqw);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @Override
    public int clean() {
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId , BaseContext.getCurrentId());
        return shoppingCartMapper.delete(lqw);
    }
}
