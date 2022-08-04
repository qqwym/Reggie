package com.itheima.reggie.controller;


import com.itheima.reggie.bean.User;
import com.itheima.reggie.bean.util.SMSUtils;
import com.itheima.reggie.bean.util.ValidateCodeUtils;
import com.itheima.reggie.common.R;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sendCode/{phone}")
    public R sendCode(@PathVariable("phone") String phone, HttpSession session) {
        String code = ValidateCodeUtils.generateValidateCode4String(4);
        //SMSUtils.sendMessage("签名","模板代号",phone,code);
        session.setAttribute(phone, code);

        return R.success(code);
    }

    @PostMapping("/login")
    public R login(@RequestBody Map<String, String> s, HttpSession session) {
        String phone = s.get("phone");
        String code = s.get("code");
        if (!session.getAttribute(phone).equals(code)) {
            return R.error("验证码错误");
        }

        User user = userService.findUser(s.get("phone"));
        if (user != null) {
            session.setAttribute("user", user);
        } else {
            User newUser = new User();
            newUser.setPhone(s.get("phone"));
            newUser.setName("iii" + s.get("phone"));
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            long id1 = Long.parseLong(dateTimeFormatter.format(LocalDateTime.now()));
            newUser.setId(id1);
            newUser.setStatus(1);
            int i = userService.addUser(newUser);
            session.setAttribute("user", newUser);
        }
        return R.success("登录成功");
    }

}
