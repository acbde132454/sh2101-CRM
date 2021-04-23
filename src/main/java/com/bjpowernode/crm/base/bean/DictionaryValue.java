package com.bjpowernode.crm.base.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Comparator;

/**
 * @ProjectName: crm
 * @Package: com.bjpowernode.crm.base.bean
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/11/21 9:24
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2020
 */
@Data
@Table(name = "tbl_dic_value")
@NameStyle(Style.normal)
public class DictionaryValue {

    @Id
    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeCode;
}