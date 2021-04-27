package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.workbench.bean.Transaction;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
}
