package com.bjpowernode.crm.settings.bean;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Id;
import javax.persistence.Table;

//省去getter、stter方法
@Data
//tkMapper默认以实体类的类名首字母小写作为表名
@Table(name = "tbl_user")
//tkMapper默认 loginAct:login_act 让实体类的属性名和数据库表所查字段保持一致
@NameStyle(Style.normal)
public class User {
    //标注主键，如果不实用该注解标注主键，根据主键查询的时候区分不了主键了，查询的是所有记录
    @Id
    private String id;
    private String loginAct;
    private String name;
    private String loginPwd;
    private String email;
    private String expireTime;
    private String lockState;
    private String deptno;
    private String allowIps;
    private String createTime;
    private String createBy;
    private String editTime;
    private String editBy;
    private String img;//用户头像



}
