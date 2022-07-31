package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface DishService extends IService<Dish> {

    R<Page<Dish>> getPage(HttpServletRequest request, int page, int pageSize, String name);

}
