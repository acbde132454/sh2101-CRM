package com.bjpowernode.crm.base.cache;

import com.bjpowernode.crm.base.bean.DictionaryType;
import com.bjpowernode.crm.base.bean.DictionaryValue;
import com.bjpowernode.crm.base.mapper.DictionaryTypeMapper;
import com.bjpowernode.crm.base.mapper.DictionaryValueMapper;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CrmCache {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ServletContext servletContext;
    
    
    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;
    
    @Autowired
    private DictionaryValueMapper dictionaryValueMapper;

    //等同于 init-method
    @PostConstruct
    public void init(){
        List<User> users = userMapper.selectAll();
        servletContext.setAttribute("users",users);

        List<DictionaryType> dictionaryTypes = dictionaryTypeMapper.selectAll();

        //采用List<DictionaryType>存储
       /* for (DictionaryType dictionaryType : dictionaryTypes) {
            //每一个类型的主键，数据字典值的外键
            String code = dictionaryType.getCode();
            DictionaryValue dictionaryValue = new DictionaryValue();
            dictionaryValue.setTypeCode(code);
            //每个数据字典类型对应的多个数据字典的值
            List<DictionaryValue> dictionaryValues = dictionaryValueMapper.select(dictionaryValue);
            dictionaryType.setDictionaryValues(dictionaryValues);
        }*/
       //采用Map<String,List<DictionValue>>,key:类型code,value:每种字典类型对应的字典值
        Map<String,List<DictionaryValue>> map = new HashMap<>();
        for (DictionaryType dictionaryType : dictionaryTypes) {
            //每一个类型的主键，数据字典值的外键
            String code = dictionaryType.getCode();
            DictionaryValue dictionaryValue = new DictionaryValue();
            dictionaryValue.setTypeCode(code);

            List<DictionaryValue> dictionaryValues = dictionaryValueMapper.select(dictionaryValue);

            map.put(code,dictionaryValues);
        }
        servletContext.setAttribute("map",map);
    }
}
