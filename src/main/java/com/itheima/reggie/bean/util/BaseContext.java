package com.itheima.reggie.bean.util;

/*
    1. 用来包装ThreadLocal
        1.1 定义出来ThreadLocal的对象
        1.2 暴露存数据的方法
        1.3 暴露取数据的方法
 */
public class BaseContext {

    //1. 构建ThreadLocal对象
    private static  ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 2. 暴露一个存值的方法
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 3. 暴露一个取值的方法
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
