package com.itheima.reggie.filter;



import com.itheima.reggie.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        if (uri.contains("login") || uri.contains("images")
                || uri.contains("plugins") || uri.contains("styles")
                || uri.endsWith(".js") || uri.endsWith(".ico")
                || uri.contains("sendCode")) {
            chain.doFilter(request, response);

            return;
        }

        HttpSession session = req.getSession();
        Object employee = session.getAttribute("employee");
        if (employee != null) {

            chain.doFilter(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        if (user!=null){
            chain.doFilter(request,response);
            return;
        }
        if (uri.contains("backend")){
            res.sendRedirect("/backend/page/login/login.html");
        }else {
            res.sendRedirect("/front/page/login.html");
        }

    }
}

