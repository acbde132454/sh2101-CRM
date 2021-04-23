package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.contants.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;
import com.bjpowernode.crm.workbench.bean.ClueRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.mapper.ClueActivityRelationMapper;
import com.bjpowernode.crm.workbench.mapper.ClueMapper;
import com.bjpowernode.crm.workbench.mapper.ClueRemarkMapper;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public void saveClue(Clue clue) {
        clue.setId(UUIDUtil.getUUID());
        int count = clueMapper.insertSelective(clue);
        if(count == 0){
           throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT);
        }

    }

    @Override
    public Clue clueDetail(String id) {
        Clue clue = clueMapper.selectByPrimaryKey(id);
        //设置所有者，关联用户表查询
        User user = userMapper.selectByPrimaryKey(clue.getOwner());
        clue.setOwner(user.getName());

        //线索备注
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setClueId(id);
        List<ClueRemark> clueRemarks = clueRemarkMapper.select(clueRemark);
        clue.setClueRemarks(clueRemarks);

        //关联市场活动
        //先从中间表中查询出当前线索关联的市场活动主键
        List<Activity> activities = new ArrayList<>();
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations = 
                clueActivityRelationMapper.select(clueActivityRelation);
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            String activityId = activityRelation.getActivityId();

            //查询每个关联的市场活动
            Activity activity =
                    activityMapper.selectByPrimaryKey(activityId);
            user = userMapper.selectByPrimaryKey(activity.getOwner());
            activity.setOwner(user.getName());
            activities.add(activity);
        }
        clue.setActivities(activities);
        return clue;
    }

    @Override
    public List<Activity> queryRelationActivities(String name,String id) {
        //根据当前线索id查询出来线索已经关联的市场活动
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations =
                clueActivityRelationMapper.select(clueActivityRelation);

        //定义一个集合，用于存储当前线索已经关联的市场活动
        List<String> ids = new ArrayList<>();
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            String activityId = activityRelation.getActivityId();
            ids.add(activityId);
        }
        Example example = new Example(Activity.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StrUtil.isEmpty(name)){
            //名字模糊查询，还要排除已经关联的市场活动
            criteria.andLike("name","%" + name + "%").andNotIn("id",ids);
        }else{
            criteria.andNotIn("id",ids);
        }

        List<Activity> activities = activityMapper.selectByExample(example);
        for (Activity activity : activities) {
            User user = userMapper.selectByPrimaryKey(activity.getOwner());
            activity.setOwner(user.getName());
        }
        return activities;
    }

    @Override
    public List<Activity> bind(String activitIds, String id) {

        //将activitIds转换成数组
        String[] ids = activitIds.split(",");

        for (String activityId : ids) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();

            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(id);
            clueActivityRelation.setActivityId(activityId);
            int count = clueActivityRelationMapper.insertSelective(clueActivityRelation);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_RELATION_ACTIVITY_BIND);
            }
        }

        //再次查询当前线索已经关联的所有市场活动
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<Activity> activities = queryBindActivity(clueActivityRelation);
        return activities;
    }

    @Override
    public List<Activity> unbind(ClueActivityRelation clueActivityRelation) {

        int count = clueActivityRelationMapper.delete(clueActivityRelation);
        if(count == 0){
            throw new CrmException(CrmEnum.CLUE_RELATION_ACTIVITY_UNBIND);
        }

        //再次查询当前线索已经关联的所有市场活动
        clueActivityRelation.setClueId(clueActivityRelation.getClueId());
        List<Activity> activities = queryBindActivity(clueActivityRelation);
        return activities;
    }

    //抽取查询当前线索已经关联的所有市场活动

    public List<Activity> queryBindActivity(ClueActivityRelation clueActivityRelation){
        clueActivityRelation.setActivityId(null);
        List<Activity> activities = new ArrayList<>();
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            Activity activity = activityMapper.selectByPrimaryKey(activityRelation.getActivityId());
            User user = userMapper.selectByPrimaryKey(activity.getOwner());
            activity.setOwner(user.getName());
            activities.add(activity);
        }
        return activities;
    }
}
