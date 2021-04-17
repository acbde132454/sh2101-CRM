package com.bjpowernode.crm.workbench.bean;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_activity_remark")
@NameStyle(Style.normal)
public class ActivityRemark {

    @Id
    private String id;
    private String noteContent;//备注内容
    private String createTime;//创建时间
    private String createBy;//创建者
    private String editTime;//编辑时间
    private String editBy;//编辑者
    private String editFlag;//是否编辑 1:已编辑 0:未编辑
    private String activityId;//市场活动外键

    private String owner;//用户外键

    private String img;//用户头像

}
