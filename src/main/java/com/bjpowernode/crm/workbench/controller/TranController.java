package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Transaction;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class TranController {

    @Autowired
    private TranService tranService;

    //交易列表页面的复杂查询
    @RequestMapping("/workbench/tran/tranQuery")
    @ResponseBody
    public ResultVo tranQuery(Transaction transaction){
        ResultVo resultVo = tranService.tranQuery(transaction);
        return resultVo;
    }

    //模糊查询客户名称
    @RequestMapping("/workbench/transaction/queryCustomerName")
    @ResponseBody
    public List<String> queryCustomerName(String customerName){
        List<String> customerNames = tranService.queryCustomerName(customerName);
        return customerNames;
    }

    //模糊查询客户名称
    @RequestMapping("/workbench/transaction/queryPossibility")
    @ResponseBody
    public String queryPossibility(String stage, HttpSession session){
        //获取缓冲数据中的阶段和可能性的数据
        Map<String,String> stage2PossibilityMap =
                (Map<String, String>) session.getServletContext().getAttribute("stage2Possibility");
        return stage2PossibilityMap.get(stage);
    }

    //跳转到交易详情页
    @RequestMapping("/workbench/transaction/toDetail")
    public String toDetail(String id, Model model){
        Transaction transaction = tranService.toDetail(id);
        model.addAttribute("transaction",transaction);
        return "/workbench/transaction/detail";
    }

    //异步查询当前交易对应的阶段图标内容
    //index:"" null

    /**
     * index
     * null:名字不一致 传不传值都是null
     * "":名字一致但是没值
     * @param id
     * @param session
     * @param index
     * @return
     */
    @RequestMapping("/workbench/transaction/queryStage")
    @ResponseBody
    public List<Map<String,? extends Object>> queryStage(String id,HttpSession session,Integer index){
        User user = (User) session.getAttribute("user");
        //获取缓冲数据中的阶段和可能性的数据
        Map<String,String> stage2PossibilityMap =
                (Map<String, String>) session.getServletContext().getAttribute("stage2Possibility");
        List<Map<String,? extends Object>> stageList = tranService.queryStage(id,stage2PossibilityMap,index,user);
        return stageList;
    }
}
