package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {


    @Autowired
    private UserService userService;
    //传统方式
    @RequestMapping("/settings/user/login")
    public String login(User user, Model model,HttpServletRequest request){
        try{
            //remote:远程的
            String remoteAddr = request.getRemoteAddr();
            user.setAllowIps(remoteAddr);
            userService.login(user);
        }catch (CrmException e){
            //addAttribute:等同于向request域中放入值
            model.addAttribute("message",e.getMessage());
        }
        return "../login";
    }


    //异步登录
    @RequestMapping("/settings/user/loginAsync")
    @ResponseBody
    public ResultVo loginAsync(User user, HttpServletRequest request, HttpSession session){
        ResultVo resultVo = new ResultVo();
        try{
            //remote:远程的
            String remoteAddr = request.getRemoteAddr();
            user.setAllowIps(remoteAddr);
            user = userService.login(user);
            resultVo.setOk(true);
            resultVo.setMessage("登录成功^_^");
            session.setAttribute("user",user);
        }catch (CrmException e){
            resultVo.setOk(false);
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //登出
    @RequestMapping("/settings/user/loginOut")
    public String loginOut(HttpSession session){
        //从session移除用户
        session.removeAttribute("user");
        return "../login";
    }


}
