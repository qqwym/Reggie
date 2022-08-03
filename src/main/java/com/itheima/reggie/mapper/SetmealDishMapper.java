package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.bean.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {
    int addSetmealDish(@Param("setmealDish") SetmealDish setmealDish, @Param("id") long id);
}
