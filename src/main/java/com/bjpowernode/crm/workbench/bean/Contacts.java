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
 * @CreateDate: 2020/11/24 9:14
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Data
@Table(name = "tbl_contacts")
@NameStyle(Style.normal)
public class Contacts {
    @Id
    private String id;
    private String owner;
    private String source;
    private String customerId;
    private String fullname;
    private String appellation;
    private String email;
    private String mphone;
    private String job;
    private String birth;
    private String createBy;
    private String createTime;
    private String editBy;
    private String editTime;
    private String description;
    private String contactSummary;
    private String nextContactTime;
    private String address;

}