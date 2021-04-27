package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.workbench.bean.Transaction;

import java.util.List;

public interface TranService {
    ResultVo tranQuery(Transaction transaction);

    List<String> queryCustomerName(String customerName);
}
