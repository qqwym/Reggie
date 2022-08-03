package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.Setmeal;
import com.itheima.reggie.bean.utilBean.SetmealDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SetmealMapper extends BaseMapper<Setmeal> {

    //查询
    List<SetmealDto> getPage(@Param("setoff") int page, @Param("pageSize") int pageSize, @Param("name") String name);

    //获取数量
    int getTotal(String name);

    int addSetmeal(@Param("setmeal") Setmeal setmeal,@Param("id") long id);
}
