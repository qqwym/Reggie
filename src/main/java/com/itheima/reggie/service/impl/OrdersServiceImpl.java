package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.bean.Orders;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Transactional
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper mapper;

    @Override
    public R<Page<Orders>> getPage(HttpServletRequest request, int page, int pageSize, String name) {
        int setoff = (page-1)*pageSize;
        List<Orders> dish = mapper.getPage(setoff,pageSize,name);
        int total = mapper.getTotal(name);
        Page<Orders> page1 = new Page<>(dish,total);
        return R.success(page1);
    }
}
