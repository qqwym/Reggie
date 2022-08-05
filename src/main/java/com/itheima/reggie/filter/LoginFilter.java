package com.itheima.reggie.filter;



import com.itheima.reggie.bean.Employee;
import com.itheima.reggie.bean.User;
import com.itheima.reggie.bean.util.BaseContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
    登录过滤器
        1. 定义过滤器
        2. 注册过滤器： @WebFilter("/*")
        3. 在springBoot项目里面，不认识servlet阶段的内容。
            3.1 需要在启动类上打上注解 @ServletComponentScan
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    //过滤请求
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {



        //0. 转型
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;


        //1. 获取当前的请求地址
        String uri = req.getRequestURI();
        // System.out.println("来到过滤器了~！~" + uri);
        /*
            2. 判断请求地址是不是属于直接放行的地址
                2.1 登录的页面和执行登录的请求地址， 都属于直接放行
         */
        if(uri.contains("login") || uri.contains("images")
                || uri.contains("plugins") || uri.contains("styles")
                || uri.endsWith(".js") || uri.endsWith(".ico")
                || uri.contains("sendCode")){
            //2.1 如果是，那么放行请求
            filterChain.doFilter(servletRequest, servletResponse);

            //方法执行到这里就回去了。后续的代码不要执行了。
            return ;
        }

        //2.2 如果不是， 就要判定有没有登录 【后台登录判定】
        HttpSession session = req.getSession();

        //3. 判定员工是否有登录，其实只要从session作用域里面取出来员工信息即可
        // 【因为之前在登录的时候，已经把员工的数据保存到了session作用域，KEY： employee】
        Employee employee = (Employee) session.getAttribute("employee");

        if(employee != null){

            // 3.0 把员工的id保存到threadlocal里面去，方法一会进行添加或者是更新的时候，能够快速的取出来id值
            BaseContext.setCurrentId(employee.getId());


            //3.1 如果取到的数据不为 null, 表示已经登录了，直接放行！
            filterChain.doFilter(servletRequest, servletResponse);

            //方法执行到这里就回去了。后续的代码不要执行了。
            return ;
        }

        //================前端判定=========================
        User user = (User) session.getAttribute("user");
        if(user != null){

            //使用BaseContext来设置id
            BaseContext.setCurrentId(user.getId());


            //已经登录过了，直接放行
            filterChain.doFilter(servletRequest, servletResponse);
            return ;
        }

        //如果希望分得清楚，那么把所有的后台的路径地址都加上 /api/  这样的路径。
        if(uri.contains("backend")){
            //3.2 如果取到的数据为 null, 表示没有登录了，跳转到登录页面！
            resp.sendRedirect("/backend/page/login/login.html");
        }else{
            resp.sendRedirect("/front/page/login.html");
        }


    }
}
