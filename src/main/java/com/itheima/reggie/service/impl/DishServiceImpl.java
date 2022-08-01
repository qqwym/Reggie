package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.bean.Category;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.mapper.DishFlavorMapper;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Transactional
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper mapper;

    @Autowired
    private DishFlavorMapper dfmapper;

    @Override
    public R<Page<DIshDto>> getPage(HttpServletRequest request, int page, int pageSize,String name) {
        int setoff = (page-1)*pageSize;
        List<DIshDto> dish = mapper.getPage(setoff,pageSize,name);
        int total = mapper.getTotal(name);
        Page<DIshDto> page1 = new Page<>(dish,total);
        return R.success(page1);
    }

    @Override
    public R<Integer> addDish(HttpServletRequest request, Dish dish, List<DishFlavor> dishFlavor,long id) {
        Random random = new Random();
        dish.setUpdateTime(LocalDateTime.now());
        dish.setCreateTime(LocalDateTime.now());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        long id1 = Long.parseLong(dateTimeFormatter.format(LocalDateTime.now()));
        dish.setId(id1);
        int i = mapper.addDish(dish,id);
        int r = 1;
        for (DishFlavor flavor : dishFlavor) {
            flavor.setId(id1+ random.nextInt(10));
            flavor.setCreateTime(LocalDateTime.now());
            flavor.setUpdateTime(LocalDateTime.now());
            flavor.setDishId(dish.getId());
            flavor.setIsDeleted(0);
            r = dfmapper.addDishFlavor(flavor,id);
        }
        if (i>0&&r>0){
            return R.success(1);
        }
        return R.error("错误");
    }

}
