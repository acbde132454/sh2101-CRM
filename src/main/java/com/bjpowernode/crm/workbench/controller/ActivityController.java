package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    //列表查询和分页查询用一个方法，加上列表条件查询
    @RequestMapping("/workbench/activity/list")
    @ResponseBody
    //http://localhost:8080/crm/workbench/activity/list?page=1&pageSize=10
    //request.getParamter("page")
    public ResultVo list(int page, int pageSize,Activity activity){
        //pageNum:当前页 pageSize:每页显示的记录数 等同于把limit a,b结果已经计算出来的
        PageHelper.startPage(page,pageSize);
        List<Activity> activities = activityService.list(activity);
        PageInfo pageInfo = new PageInfo(activities);
        ResultVo resultVo = new ResultVo();
        resultVo.setPageInfo(pageInfo);

        //request.setAttribute("resultVo",resultVo)

        //model.addAttribute("resultVo",resultVo);

        //request.getRequestDispather("xx.jsp").forward(request,response);
        return resultVo;
    }


    //异步查询所有者信息
    @RequestMapping("/workbench/queryAllOwners")
    @ResponseBody
    public List<User> queryAllOwners(){
        List<User> users = activityService.queryAllOwners();
        return users;
    }

    //异步添加市场活动
    @RequestMapping("/workbench/createOrUpdate")
    @ResponseBody
    public ResultVo createOrUpdate(Activity activity, HttpSession session){
        ResultVo resultVo = null;
        try{
            User user = (User) session.getAttribute("user");
            resultVo = activityService.createOrUpdate(activity,user);
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //根据id号查询市场活动
    @RequestMapping("/workbench/queryById")
    @ResponseBody
    public Activity queryById(String id){
        Activity activity = activityService.queryById(id);
        return activity;
    }

    //单删和批量删除
    @RequestMapping("/workbench/deleteActivity")
    @ResponseBody
    public ResultVo deleteActivity(String ids){
        ResultVo resultVo = new ResultVo();
        try{
            activityService.deleteActivity(ids);
            resultVo.setOk(true);
            resultVo.setMessage("删除市场活动成功");
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //跳转到市场活动详情页
    @RequestMapping("/workbench/toDetail")
    public String toDetail(String id,Model model){
        Activity activity = activityService.queryDetail(id);
        model.addAttribute("activity",activity);
        return "/workbench/activity/detail";
    }

    //异步添加市场活动备注 /workbench/saveActivityRemark
    @RequestMapping("/workbench/saveActivityRemark")
    @ResponseBody
    public ResultVo saveActivityRemark(ActivityRemark activityRemark,HttpSession session){
        ResultVo resultVo = new ResultVo();
        try{
            User user = (User) session.getAttribute("user");
            activityRemark.setCreateBy(user.getName());
            activityRemark.setOwner(user.getId());
            activityRemark.setImg(user.getImg());
            activityRemark = activityService.saveActivityRemark(activityRemark);
            resultVo.setOk(true);
            resultVo.setMessage("添加市场活动备注成功");
            resultVo.setT(activityRemark);
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
}
