package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Activity> list() {
        List<Activity> activities = activityMapper.selectAll();
        for (Activity activity : activities) {
            //用户表的主键，市场活动外键
            String owner = activity.getOwner();
            User user = userMapper.selectByPrimaryKey(owner);
            //每个用户的姓名 管理员、张三、小红
            String name = user.getName();
            activity.setOwner(name);
        }
        return activities;
    }
}
