package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.bean.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

    List<Orders> getPage(@Param("setoff") int page, @Param("pageSize") int pageSize, @Param("name")String name);

    int getTotal(String name);
}
