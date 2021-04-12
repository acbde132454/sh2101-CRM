package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    //列表查询和分页查询用一个方法
    @RequestMapping("/workbench/activity/list")
    @ResponseBody
    public ResultVo list(int page,int pageSize){
        //pageNum:当前页 pageSize:每页显示的记录数 等同于把limit a,b结果已经计算出来的
        PageHelper.startPage(page,pageSize);
        List<Activity> activities = activityService.list();
        PageInfo pageInfo = new PageInfo(activities);
        ResultVo resultVo = new ResultVo();
        resultVo.setPageInfo(pageInfo);
        return resultVo;
    }
}
