package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.bean.DishFlavor;
import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.SetmealDish;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.bean.utilBean.SetmealDto;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.SetmealDishMapper;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;


@Transactional
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper mapper;

    @Autowired
    private SetmealDishMapper dfmapper;


    @Override
    public R<Page<SetmealDto>> getPage(HttpServletRequest request, int page, int pageSize, String name) {
        int setoff = (page-1)*pageSize;
        List<SetmealDto> dish = mapper.getPage(setoff,pageSize,name);
        int total = mapper.getTotal(name);
        Page<SetmealDto> page1 = new Page<>(dish,total);
        return R.success(page1);
    }

    @Override
    public R<Integer> addSetmeal(HttpServletRequest request, Setmeal setmeal, List<SetmealDish> setmealDish,long id) {
        Random random = new Random();
        setmeal.setUpdateTime(LocalDateTime.now());
        setmeal.setCreateTime(LocalDateTime.now());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        long id1 = Long.parseLong(dateTimeFormatter.format(LocalDateTime.now()));
        setmeal.setId(id1);
        setmeal.setIsdeleted(0);
        int i = mapper.addSetmeal(setmeal,id);
        int r = 1;
        if (setmealDish==null){
            if (i>0){
                return R.success(1);
            }
        }else {
            for (SetmealDish flavor : setmealDish) {
                flavor.setId(id1+ random.nextInt(10));
                flavor.setCreateTime(LocalDateTime.now());
                flavor.setUpdateTime(LocalDateTime.now());
                flavor.setIsDeleted(0);
                flavor.setSort(0);
                flavor.setSetmealId(setmeal.getId());
                r = dfmapper.addSetmealDish(flavor,id);
            }
        }
        if (i>0&&r>0){
            return R.success(1);
        }
        return R.error("错误");
    }


}
