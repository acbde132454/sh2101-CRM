package com.bjpowernode.crm.base.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

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
@Table(name = "tbl_dic_type")
@NameStyle(Style.normal)
public class DictionaryType {

    @Id
    private String code;
    private String name;
    private String description;

    //数据字典的值
   /* private List<DictionaryValue> dictionaryValues;*/

}