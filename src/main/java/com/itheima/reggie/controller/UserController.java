package com.itheima.reggie.controller;


import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/sendCode/{id}")
    public R<Long> sendCode(@PathVariable("id") long phone){
        Random random = new Random();
        long s=random.nextInt(9)+1;
        for (int i =0;i<5;i++){
            s = s*10+random.nextInt(10);
        }
        return R.success(s);

    }

}
