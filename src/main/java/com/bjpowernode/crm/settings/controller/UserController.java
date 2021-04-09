package com.bjpowernode.crm.settings.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

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

    //上传文件
    @RequestMapping("/settings/user/fileUpload")
    @ResponseBody
    public ResultVo fileUpload(MultipartFile img,HttpSession session,HttpServletRequest request){
        ResultVo resultVo = new ResultVo();
        try{
            String realPath = session.getServletContext().getRealPath("/upload");
            File file = new File(realPath);
            if(!file.exists()){
                //目录不存在则创建目录，创建多级目录
                file.mkdirs();
            }
            //不同用户可能上传同名的文件
            //获取上传文件名 437249832748美女.jpg
            String filename = img.getOriginalFilename();
            filename = System.currentTimeMillis() + filename;

            //把文件内容写入到磁盘上 .../upload/437249832748美女.jpg
            img.transferTo(new File(realPath + File.separator + filename));
            resultVo.setOk(true);
            resultVo.setMessage("上传图片成功!");
            //request.getContextPath():/crm
            String path = request.getContextPath() + File.separator + "upload" +File.separator + filename;
            resultVo.setT(path);
            //上传头像成功，要把路径保存在用户的img中
            User user = (User) session.getAttribute("user");
            user.setImg(path);
            //更新数据库
            userService.updateUser(user);
        }catch (Exception e){
            resultVo.setOk(false);
            resultVo.setMessage(e.getMessage());
        }

        return resultVo;
    }

    //异步校验用户输入的源密码是否正确
    @RequestMapping("/settings/user/verifyPwd")
    @ResponseBody
    public ResultVo verifyPwd(String oldPwd,HttpSession session){
        ResultVo resultVo = new ResultVo();
        User user = (User) session.getAttribute("user");
        if(!user.getLoginPwd().equals(MD5Util.getMD5(oldPwd))){
            resultVo.setOk(false);
            resultVo.setMessage("原始密码输入错误");
        }else{
            resultVo.setOk(true);
        }
      return resultVo;
    }

    //异步更新密码

    @RequestMapping("/settings/user/updatePwd")
    @ResponseBody
    public ResultVo updatePwd(String newPwd,HttpSession session){
        ResultVo resultVo = new ResultVo();
        try{
            User user = (User) session.getAttribute("user");
            user.setLoginPwd(MD5Util.getMD5(newPwd));
            userService.updatePwd(user);
            resultVo.setOk(true);
            resultVo.setMessage("修改密码成功");
        }catch (CrmException e){
            resultVo.setOk(false);
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
}
