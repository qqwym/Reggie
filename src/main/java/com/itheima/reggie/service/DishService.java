package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.utilBean.DishDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DishService extends IService<Dish> {

    R<Page<DishDto>> getPage(HttpServletRequest request, int page, int pageSize, String name);

    R<Integer> addDish(HttpServletRequest request, Dish dish, List<DishFlavor> dishFlavor,long id);

    R<Integer> editDish(HttpServletRequest request,Dish dish,List<DishFlavor> dishFlavors,long id);

    R<Integer> editStatus(HttpServletRequest request,List<Long> id);

    R<Integer> editStatus1(HttpServletRequest request,List<Long> id);

    R<Integer> delete(HttpServletRequest request,List<Long> id);

    R<List<DishDto>> getList(long id, HttpServletRequest request, long status);

}
