package com.bjpowernode.crm.base.interceptor;

import com.bjpowernode.crm.settings.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrmLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录/登出的时候不能拦截，得放行
        //1、获取用户请求的url/uri地址
        String url = request.getRequestURL().toString();
        if(url.contains("login")){
            return true;
        }


        //用户登录成功之后做些操作不能拦截，得放行
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            //2、用户未登录，跳转到登录界面
            response.sendRedirect("/crm/login.jsp");
        }else{
            return true;
        }
        return false;
    }
}
