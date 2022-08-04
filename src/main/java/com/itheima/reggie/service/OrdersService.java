package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.Orders;
import com.itheima.reggie.bean.utilBean.DIshDto;
import com.itheima.reggie.bean.utilBean.Page;
import com.itheima.reggie.common.R;

import javax.servlet.http.HttpServletRequest;

public interface OrdersService extends IService<Orders> {
    R<Page<Orders>> getPage(HttpServletRequest request, int page, int pageSize, String name);
}
