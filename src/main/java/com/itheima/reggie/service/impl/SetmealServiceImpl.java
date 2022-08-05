package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.SetmealDish;
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
        List<SetmealDto> dish = mapper.getPage(setoff,pageSize,name,0);
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

    // RestFul 风格
    // 原始： localhost:8080/aaa/deleteStu?id=3
    // restful :  localhost:8080/aaa/stu/3
    // restful  搭配请求方式
            /*
                添加 ：  post
                查询：  get
                更新：  put
                删除 : delete
             */
    @Override
    public R<SetmealDto> getSetmeal(HttpServletRequest request, long id) {
        List<SetmealDto> page = mapper.getPage(0, 1, null, id);
        SetmealDto setmealDto = page.get(0);
        setmealDto.setSetmealDishes(dfmapper.getList(setmealDto.getId()));
        return R.success(setmealDto);
    }

    @Override
    public R<Integer> editSetmeal(HttpServletRequest request, List<SetmealDish> setmealDishes, Setmeal setmeal,long id) {
        int i = dfmapper.deleteBysetmealId(setmeal.getId());
        Random random = new Random();
        for (SetmealDish setmealDish : setmealDishes) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            long id1 = Long.parseLong(dateTimeFormatter.format(LocalDateTime.now()));
            setmealDish.setId(id1+ random.nextInt(10));
            setmealDish.setCreateTime(LocalDateTime.now());
            setmealDish.setUpdateTime(LocalDateTime.now());
            setmealDish.setIsDeleted(0);
            setmealDish.setSort(0);
            setmealDish.setSetmealId(setmeal.getId());
            dfmapper.addSetmealDish(setmealDish,id);
        }
        setmeal.setUpdateTime(LocalDateTime.now());
        int i1 = mapper.editSetmeal(setmeal, id);
        return R.success(i);
    }

    @Override
    public R<Integer> editStatus(HttpServletRequest request, List<Long> ids) {
        int i = 0;
        for (Long id : ids) {
            i += mapper.editStatus(id);
        }
        if (i>0){
            return R.success(i);
        }
        return R.error("0");
    }

    @Override
    public R<Integer> editStatus1(HttpServletRequest request, List<Long> ids) {
        int i = 0;
        for (Long id : ids) {
            i += mapper.editStatus1(id);
        }
        if (i>0){
            return R.success(i);
        }
        return R.error("0");
    }

    @Override
    public R<Integer> delete(HttpServletRequest request, List<Long> ids) {
            int i = 0;
            for (Long id : ids) {
                dfmapper.deleteBysetmealId(id);
                i+=mapper.deleteById(id);
            }
            if (i>0){
                return R.success(i);
            }
            return R.error("0");

    }


}
