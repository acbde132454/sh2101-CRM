package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.workbench.mapper.ChartMapper;
import com.bjpowernode.crm.workbench.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartMapper chartMapper;


    // Map<String,List<Map<String,Object>>>  Map<String,List<Object>>
    @Override
    public Map<String,List<Object>> transaction() {
        List<Map<String, Object>> transaction = chartMapper.transaction();
        //柱状图
        Map<String,List<Object>> data = new HashMap<>();
        List<Object> stage = new ArrayList<>();
        List<Object> num = new ArrayList<>();
        //饼状图
        Map<String,List<Map<String,Object>>> pieMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for(Map<String, Object> map : transaction ){
            Map<String,Object> m = new HashMap<>();
            for(Map.Entry<String,Object> entry : map.entrySet()){
                //柱状图
                if(entry.getKey().equals("stage")){
                    stage.add(entry.getValue());
                    m.put("name",entry.getValue());
                }
                if(entry.getKey().equals("num")){
                    num.add(entry.getValue());
                    m.put("value",entry.getValue());
                }
            }
            list.add(m);
        }
        data.put("stage",stage);
        data.put("num",num);

        pieMap.put("pie",list);

        List<Object> list1 = new ArrayList<>();
        list1.add(pieMap);
        data.put("pie",list1);
        return data;
    }
}
