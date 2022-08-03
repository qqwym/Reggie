package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.SetmealDish;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.bean.utilBean.SetmealDto;
import com.itheima.reggie.common.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    R<Page<SetmealDto>> getPage(HttpServletRequest request, int page, int pageSize, String name);

    R<Integer> addSetmeal(HttpServletRequest request, Setmeal setmeal, List<SetmealDish> setmealDish,long id);

}
