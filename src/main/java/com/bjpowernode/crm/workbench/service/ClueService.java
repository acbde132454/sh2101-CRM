package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.Clue;
import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;

import java.util.List;

public interface ClueService {
    void saveClue(Clue clue);

    Clue clueDetail(String id);

    List<Activity> queryRelationActivities(String name,String id);

    List<Activity> bind(String activitIds, String id);

    List<Activity> unbind(ClueActivityRelation clueActivityRelation);
}
