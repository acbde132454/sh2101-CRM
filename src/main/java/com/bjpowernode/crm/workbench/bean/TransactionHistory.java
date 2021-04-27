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
 * @CreateDate: 2020/11/24 11:47
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Data
@Table(name = "tbl_tran_history")
@NameStyle(Style.normal)
public class TransactionHistory {

    @Id
    private String id;
    private String stage;
    private String money;
    private String expectedDate;
    private String createTime;
    private String createBy;
    private String tranId;
}