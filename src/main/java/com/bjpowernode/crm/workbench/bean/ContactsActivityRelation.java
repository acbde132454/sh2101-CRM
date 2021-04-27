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
 * @CreateDate: 2020/11/24 10:14
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Data
@Table(name = "tbl_contacts_activity_relation")
@NameStyle(Style.normal)
public class ContactsActivityRelation {

    @Id
    private String id;
    private String contactsId;
    private String activityId;

}