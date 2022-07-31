package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.bean.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category>{
    //查询
    List<Category> getPage(@Param("setoff") int page, @Param("pageSize") int pageSize,@Param("type") int type);

    //获取数量
    int getTotal();

    //添加员工
    @Transactional
    long addCategory(@Param("category") Category category,@Param("id") long id);

    long editCategory(@Param("category") Category category,@Param("id") long id);

    long deleteById(long id);

}