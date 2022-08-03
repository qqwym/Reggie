package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.bean.Dish;
import com.itheima.reggie.bean.utilBean.DIshDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DishMapper extends BaseMapper<Dish>{
    //查询
    List<DIshDto> getPage(@Param("setoff") int page, @Param("pageSize") int pageSize, @Param("name")String name);

    int getTotal(String name);

    int addDish(@Param("dish") Dish dIsh,@Param("id") long id);

    int editDish(@Param("dish") Dish dish,@Param("id") long id);

    int editStatus(long id);

    int editStatus1(long id);

    int deleteId(long id);

    List<Dish> getList(long categotyId);


}