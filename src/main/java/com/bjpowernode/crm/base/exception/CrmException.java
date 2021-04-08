package com.bjpowernode.crm.base.exception;

import com.bjpowernode.crm.base.contants.CrmEnum;
import lombok.Data;

//CRM项目自定义异常类 Throwable
@Data
public class CrmException extends RuntimeException{


    private CrmEnum crmEnum;

    public CrmException(CrmEnum crmEnum){
        //调用父类工作方法
        super(crmEnum.getMessage());
        this.crmEnum = crmEnum;
    }

}
