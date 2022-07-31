package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.common.R;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Mapper
@Repository
public interface EmployeeMapper extends BaseMapper<Employee>{
    //查询
    List<Employee> getPage(@Param("setoff") int page,@Param("pageSize") int pageSize,@Param("name") String name,@Param("id")Long id);

    //获取数量
    int getTotal(String name);

    //添加员工
    @Transactional
    long addEmployee(@Param("employee") Employee employee,@Param("id") long id);

    long editEmployee(@Param("employee") Employee employee,@Param("id") long id);

}