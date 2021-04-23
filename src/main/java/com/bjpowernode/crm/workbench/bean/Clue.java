package com.bjpowernode.crm.workbench.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * @ProjectName: crm
 * @Package: com.bjpowernode.crm.workbench.bean
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/11/21 14:27
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 * 线索表有3部分信息:
 * 1、线索表自身的信息
 * 2、联系人信息
 * 3、公司(客户)信息
 */
@Data
@Table(name = "tbl_clue")
@NameStyle(Style.normal)
public class Clue {

    @Id
    private String id;//主键
    private String fullname;//联系人名字:不是客户 CRM的客户指的是公司
    private String appellation;//称呼
    private String owner;//所有者
    private String company;//公司名称
    private String job;//联系人岗位
    private String email;//联系人邮箱
    private String phone;//公司座机
    private String website;//公司网址
    private String mphone;//联系人手机号 MobilePhone
    private String state;//线索状态
    private String source;//线索来源
    private String createBy;//创建者
    private String createTime;//创建时间
    private String editBy;
    private String editTime;
    private String description;
    private String contactSummary;//联系纪要
    private String nextContactTime;//下次联系时间
    private String address;//公司地址


    //线索备注
    private List<ClueRemark> clueRemarks;

    //市场活动
    private List<Activity> activities;

}