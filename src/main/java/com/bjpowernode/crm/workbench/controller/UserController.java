package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller("userController2")
@RequestMapping("user")
public class UserController {


    @Autowired
    UserService userService;

    //handlermapping
    @RequestMapping("list")


    /**
     *   修饰符  返回值  方法名（形参）{  return }
     */

    //1.使用servlet api
    /*void list(HttpServletRequest request, HttpServletResponse response)throws Exception{

        //1.接收请求当中的参数
        //request.getParameter("a")
        //2.调用模型层
        List<User> list = userService.list();

        //3.往作用域放值(page request session application)
        request.setAttribute("list",list);

        //4.响应用户
        request.getRequestDispatcher("/user/list.jsp").forward(request,response);

    }*/
    //2.使用ModelAndView(SpringMVC)

    /*ModelAndView list(int a){

        //1.接收请求当中的参数
        //request.getParameter("a")

        ModelAndView modelAndView = new ModelAndView();

        //2.调用模型层
        List<User> list = userService.list();

        //3.往作用域放值
        modelAndView.addObject("list",list);
        //request.setAttribute("list",list);

        //model  被放在reqeuest作用域的那个attribute被称为model

        //4.响应用户
        //view   视图
        //modelAndView.setView(new InternalResourceView("/user/list.jsp"));//请求转发

        //   /WEB-INF/user/list.jsp

        //viewName:  介于视图解释器(InternalResourceViewResolver)的前辍和后辍之间的那个字符串被称为viewName
        //modelAndView.setViewName("user/list");


        //modelAndView.setView(new RedirectView("http://www.baidu.com"));

        //modelAndView.setViewName("redirect:http://www.baidu.com");

        // /WEB-INF/user/list.jsp
        //request.getRequestDispatcher("/WEB-INF/user/list.jsp").forward(request,response);
        return modelAndView;

    }*/

    /**
     * 以下所有的方式全部有前置条件：视图必须介于视图解释器的前辍和后辍之间
     *
     */
   /* //3.返回viewname

   String list(ArrayList<User> list,int a){
        //1.接收请求当中的参数

        //2.调用模型层
        List<User> listDB = userService.list();
        //3.往作用域放值
        //当方法的形参类型为Collection类型(List,Set)、Bean(狭义)类型、Map时，这个对象会自动进入model
        //request.setAttribute("list",list);
        list.addAll(listDB);
        //Bean:  广义上Bean认为，只要是对象就是Bean。狭义上认为：一个类有属性，有 Getter和Setter，那么些类产生的对象称为Bean



        //4.响应用户

        return "/user/list";
    }*/

   //4.使用约定  (只要没有告诉springmvc如何去响应，那么默认的响应是请求转发到 namespace/action(视图的名字))
    List<User> list(){
        //1.接收请求当中的参数

        //2.调用模型层

        List<User> list = userService.list();

        //3.往作用域放值   (返回值只要是Collection类型(List,Set)、Bean(狭义)类型、Map时，这个对象会自动进入model)

        //4.响应用户

        return list;

    }

    //5.返回viewname (第3种的变体)
   /* String list(Map<String,Object> map, int a){//map会自动实例化
        //1.接收请求当中的参数

        //2.调用模型层
        List<User> listDB = userService.list();
        //3.往作用域放值
        //当方法的形参类型为Collection类型(List,Set)、Bean(狭义)类型、Map时，这个对象会自动进入model
        //request.setAttribute("list",list);

        //Bean:  广义上Bean认为，只要是对象就是Bean。狭义上认为：一个类有属性，有 Getter和Setter，那么些类产生的对象称为Bean
        map.put("list",listDB);


        //4.响应用户

        return "/user/list";
    }*/

   //6.使用约定  (只要没有告诉springmvc如何去响应，那么默认的响应是请求转发到 namespace/action(视图的名字))
 /* Map<String,Object> list(int a){
        //1.接收请求当中的参数
        Map<String,Object> map = new HashMap<>();

        //2.调用模型层
        List<User> listDB = userService.list();

        //3.往作用域放值(返回值只要是Collection类型(List,Set)、Bean(狭义)类型、Map时，这个对象会自动进入model)
        map.put("list",listDB);

        //4.响应用户
        return map;

    }*/

     //7.使用约定
    /*void list(Map<String,Object> map,int a){
        //1.接收请求当中的参数

        //2.调用模型层
        List<User> listDB = userService.list();

        //3.往作用域放值(形参只要是Collection类型(List,Set)、Bean(狭义)类型、Map时，这个对象会自动进入model)
        map.put("list",listDB);
        //4.响应用户

    }*/
    // 8.使用约定
   /* void list(ArrayList<User> list,int a){
        //1.接收请求当中的参数

        //2.调用模型层
        List<User> listDB = userService.list();

        //3.往作用域放值(形参只要是Collection类型(List,Set)、Bean(狭义)类型、Map时，这个对象会自动进入model)
        list.addAll(listDB);

        //request.setAttribute("xxList",list);  推断：因为集合是List类型所以 Attribute的名字是: xxList，因为List当中放的是User所以，xxList=> userList

        //list = listDB;
        //4.响应用户
    }*/


    @RequestMapping("add")
    void add(){


    }

    @RequestMapping("save")
    String save(User user){//如果传入类型为Bean类型时，会自动接收请求当中的参数并且进行封装

        user.setId(UUIDUtil.getUUID());
        userService.addUser(user);


        //return "forward:list";  //如果操作会对数据库数据有影响必须重定向
        return "redirect:list";
    }


   /* @RequestMapping("update")
    String update(String id){//如果传入类型为Bean类型时，会自动接收请求当中的参数并且进行封装

        userService.deleteUser(id);

        //return "forward:list";  如果操作会对数据库数据有影响必须重定向
        return "redirect:list";
    }*/

    @RequestMapping("update")
    User update(String id){//如果传入类型为Bean类型时，会自动接收请求当中的参数并且进行封装


       return userService.queryById(id);//reqeust.setAttribute("user",user)
        //userService.deleteUser(id);

        //return "forward:list";  如果操作会对数据库数据有影响必须重定向
       // return "redirect:list";
    }

    @RequestMapping("saveUpdate")

    String saveUpdate(User user){

        userService.updateUser(user);

        return "redirect:list";
    }

    @RequestMapping("del")

    String saveUpdate(String id){

        userService.deleteUser(id);

        return "redirect:list";
    }


}
