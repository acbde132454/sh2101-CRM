package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ClueService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ClueController {

    @Autowired
    private ClueService clueService;

    @RequestMapping("/workbench/saveClue")
    @ResponseBody
    public ResultVo saveClue(Clue clue, HttpSession session){
        ResultVo resultVo = new ResultVo();
        try {
            User user = (User) session.getAttribute("user");
            clue.setCreateBy(user.getName());
            clue.setCreateTime(DateTimeUtil.getSysTime());
            clueService.saveClue(clue);
            resultVo.setOk(true);
            resultVo.setMessage("添加线索成功");
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //查询线索详情
    @RequestMapping("/workbench/clue/clueDetail")
    public String clueDetail(String id, Model model){
        Clue clue = clueService.clueDetail(id);
        model.addAttribute("clue",clue);
        return "/workbench/clue/detail";
    }

    //模糊查询当前线索能够关联的市场活动,当前线索已经关联的市场活动没必要再次查询了
    @RequestMapping("/workbench/clue/queryRelationActivities")
    @ResponseBody
    public List<Activity> queryRelationActivities(String name,String id){
        List<Activity> activities = clueService.queryRelationActivities(name,id);
        return activities;
    }

    //绑定市场活动
    //activitIds:勾中市场活动的id号,id:线索的id
    @RequestMapping("/workbench/clue/bind")
    @ResponseBody
    public ResultVo bind(String activitIds,String id){
        ResultVo resultVo = new ResultVo();
        try {
            List<Activity> activities = clueService.bind(activitIds,id);
            resultVo.setOk(true);
            resultVo.setMessage("关联市场活动成功");
            resultVo.setT(activities);
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }

    //解除绑定
    @RequestMapping("/workbench/clue/unbind")
    @ResponseBody
    public ResultVo unbind(ClueActivityRelation clueActivityRelation){
        ResultVo resultVo = new ResultVo();
        try {
            List<Activity> activities = clueService.unbind(clueActivityRelation);
            resultVo.setOk(true);
            resultVo.setMessage("解除绑定市场活动成功");
            resultVo.setT(activities);
        }catch (CrmException e){
            resultVo.setMessage(e.getMessage());
        }
        return resultVo;
    }
}
