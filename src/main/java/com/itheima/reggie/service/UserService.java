package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.bean.User;

public interface UserService extends IService<User> {

    User findUser(String phone);

    int addUser(User user);

}
