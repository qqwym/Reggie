package com.itheima.reggie.exception;

import com.itheima.reggie.common.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        e.printStackTrace();
        String message = e.getMessage();
        if (message.contains("Duplicate entry")){
            String s = message.split(" ")[2];
            return R.error(s+"已存在");
        }
        return R.error("未知错误");
    }

}