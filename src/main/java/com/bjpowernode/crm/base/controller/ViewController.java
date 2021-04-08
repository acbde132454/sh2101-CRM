package com.bjpowernode.crm.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

//专门负责跳转视图
@Controller
public class ViewController {

    //{值}必须和@PathVariable的值保持一致
    //File.separator:在不同操作系统下都表示/的意思
    //required:参数是不是必须有值
    @RequestMapping({"/toView/{firstView}/{secondView}","/toView/{firstView}/{secondView}/{thirdView}"})
    public String toView(
            @PathVariable(value = "firstView",required = false) String firstView,
            @PathVariable(value = "secondView",required = false)String secondView,
            @PathVariable(value = "thirdView",required = false)String thirdView){
        if(thirdView == null){
            //二级目录
            return firstView + File.separator + secondView;
        }
        //workbench/main/index
        return firstView + File.separator + secondView + File.separator + thirdView;
    }

}
