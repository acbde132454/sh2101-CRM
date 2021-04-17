package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.contants.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<Activity> list(Activity activity) {
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        //是否有按名称查询
        if(!StrUtil.isEmpty(activity.getName())){
            criteria.andLike("name","%" + activity.getName() + "%");
        }

        //按所有者 数据库表中存储的是外键

        //开始日期查询 >=开始日期   !=null !=""
        //andGreaterThanOrEqualTo:>=
        if(!StrUtil.isEmpty(activity.getStartDate())){
            criteria.andGreaterThanOrEqualTo("startDate",activity.getStartDate());
        }

        //所有者模糊查询
        /**
         *  1、先根据所有者查询用户表中满足条件的所有用户，取出主键集合
         *  2、根据1中查询出来的主键对应市场活动表中的owner,使用in关键字查询
         */
        Example example1 = new Example(User.class);
        if(!StrUtil.isEmpty(activity.getOwner())){
            example1.createCriteria().andLike("name",activity.getOwner());
            //users:查询的是根据owner的模糊查询
            List<User> users = userMapper.selectByExample(example1);
            List<String> ids = new ArrayList<>();
            for (User user : users) {
                ids.add(user.getId());
            }
            criteria.andIn("owner",ids);
        }


        //结束日期 <=结束日期
        //andLessThanOrEqualTo:<=
        if(!StrUtil.isEmpty(activity.getEndDate())){
            criteria.andLessThanOrEqualTo("endDate",activity.getEndDate());
        }

        List<Activity> activities = activityMapper.selectByExample(example);
        for (Activity activity1 : activities) {
            //用户表的主键，市场活动外键
            String owner = activity1.getOwner();
            User user = userMapper.selectByPrimaryKey(owner);
            //每个用户的姓名 管理员、张三、小红
            String name = user.getName();
            activity1.setOwner(name);
        }
        return activities;
    }

    @Override
    public List<User> queryAllOwners() {
        return userMapper.selectAll();
    }

    @Override
    public ResultVo createOrUpdate(Activity activity,User user) {
        ResultVo resultVo = new ResultVo();
        //添加市场活动
        //insertSelective:只插入非空值
        if(activity.getId() != null){
            activity.setEditBy(user.getName());
            activity.setEditTime(DateTimeUtil.getSysTime());
            //修改
            int count= activityMapper.updateByPrimaryKeySelective(activity);
            if(count == 0){
                throw new CrmException(CrmEnum.ACTIVITY_UPDATE);
            }
            resultVo.setOk(true);
            resultVo.setMessage("修改市场活动成功");
        }else{
            //主键
            activity.setId(UUIDUtil.getUUID());
            //创建时间
            activity.setCreateTime(DateTimeUtil.getSysTime());
            //创建者
            activity.setCreateBy(user.getCreateBy());
            //添加
            int count= activityMapper.insertSelective(activity);
            if(count == 0){
                throw new CrmException(CrmEnum.ACTIVITY_ADD);
            }
            resultVo.setOk(true);
            resultVo.setMessage("创建市场活动成功");
        }
        return  resultVo;
    }

    @Override
    public Activity queryById(String id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteActivity(String ids) {
        String[] split = ids.split(",");

        List<String> list = Arrays.asList(split);
        Example example = new Example(Activity.class);
        //where id in(1,2,3)==andIn 映射文件foreach标签原理
        example.createCriteria().andIn("id",list);
        int count = activityMapper.deleteByExample(example);
        if(count == 0){
            throw new CrmException(CrmEnum.ACTIVITY_DELETE);
        }
    }

    @Override
    public Activity queryDetail(String id) {
        Activity activity = activityMapper.selectByPrimaryKey(id);
        //所有者
        User user = userMapper.selectByPrimaryKey(activity.getOwner());
        activity.setOwner(user.getName());

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setActivityId(id);
        //市场活动备注
        List<ActivityRemark> activityRemarks = activityRemarkMapper.select(activityRemark);

        //查询用户信息
        for (ActivityRemark remark : activityRemarks) {
            User u = userMapper.selectByPrimaryKey(remark.getOwner());
            remark.setImg(u.getImg());
        }

        activity.setActivityRemarks(activityRemarks);
        return activity;
    }

    @Override
    public ActivityRemark saveActivityRemark(ActivityRemark activityRemark) {
        activityRemark.setId(UUIDUtil.getUUID());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        int count = activityRemarkMapper.insertSelective(activityRemark);
        if(count == 0){
            throw new CrmException(CrmEnum.ACTIVITY_DELETE);
        }
        return activityRemark;
    }
}
