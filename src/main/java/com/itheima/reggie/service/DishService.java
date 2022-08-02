package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DishService extends IService<Dish> {

    R<Page<DIshDto>> getPage(HttpServletRequest request, int page, int pageSize, String name);

    R<Integer> addDish(HttpServletRequest request, Dish dish, List<DishFlavor> dishFlavor,long id);

    R<Integer> editDish(HttpServletRequest request,Dish dish,List<DishFlavor> dishFlavors,long id);

    R<Integer> editStatus(HttpServletRequest request,List<Long> id);

    R<Integer> editStatus1(HttpServletRequest request,List<Long> id);

    R<Integer> delete(HttpServletRequest request,List<Long> id);

}
