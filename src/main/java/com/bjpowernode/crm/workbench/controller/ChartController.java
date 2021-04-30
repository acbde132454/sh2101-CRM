package com.bjpowernode.crm.workbench.controller;

import com.bjpowernode.crm.workbench.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ChartController {

    @Autowired
    private ChartService chartService;

    @RequestMapping("/workbench/chart/transaction")
    @ResponseBody
    public Map<String,List<Object>> transaction(){
        Map<String, List<Object>> transaction = chartService.transaction();
        return transaction;
    }
}
