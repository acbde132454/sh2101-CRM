package com.bjpowernode.crm.workbench.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: crm
 * @Package: com.bjpowernode.crm.workbench.bean
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/11/23 16:02
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Data
@Table(name = "tbl_customer")
@NameStyle(Style.normal)
public class Customer {

    @Id
    private String id;
    private String owner;
    private String name;
    private String website;
    private String phone;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String contactSummary;
    private String nextContactTime;
    private String description;
    private String address;

}