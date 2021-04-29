package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Transaction;

import java.util.List;
import java.util.Map;

public interface TranService {
    ResultVo tranQuery(Transaction transaction);

    List<String> queryCustomerName(String customerName);

    Transaction toDetail(String id);

    List<Map<String,? extends Object>> queryStage(String id, Map<String,String> stage2PossibilityMap, Integer index, User user);
}
