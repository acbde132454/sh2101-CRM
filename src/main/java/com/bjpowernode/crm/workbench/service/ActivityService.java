package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;

import java.util.List;

public interface ActivityService {
    List<Activity> list(Activity activity);

    List<User> queryAllOwners();

    ResultVo createOrUpdate(Activity activity, User user);

    Activity queryById(String id);

    void deleteActivity(String ids);

    Activity queryDetail(String id);

    ActivityRemark saveActivityRemark(ActivityRemark activityRemark);
}
