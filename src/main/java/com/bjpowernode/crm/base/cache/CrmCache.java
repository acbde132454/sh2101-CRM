package com.bjpowernode.crm.base.cache;

import com.bjpowernode.crm.base.bean.DictionaryType;
import com.bjpowernode.crm.base.bean.DictionaryValue;
import com.bjpowernode.crm.base.mapper.DictionaryTypeMapper;
import com.bjpowernode.crm.base.mapper.DictionaryValueMapper;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.*;

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
        //缓冲所有者信息
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

            //想得到的结果是有序的，需要进行排序
            Example example = new Example(DictionaryValue.class);
            //按指定字段排序
            example.setOrderByClause("orderNo asc");
            example.createCriteria().andEqualTo("typeCode",code);
            List<DictionaryValue> dictionaryValues = dictionaryValueMapper.selectByExample(example);
            //List<DictionaryValue> dictionaryValues = dictionaryValueMapper.select(dictionaryValue);

            map.put(code,dictionaryValues);
        }
        servletContext.setAttribute("map",map);

        //缓冲阶段和可能性的信息
        //1、读取配置文件 参数:文件名(不能加扩展名)
        ResourceBundle resourceBundle = ResourceBundle.getBundle("mybatis.Stage2Possibility");
        //2.获取属性文件中的所有key
        Enumeration<String> keys = resourceBundle.getKeys();
        //3、定义一个map来存储阶段和可能性(key,value)
        //LinkedHashMap:放入的顺序和输出的顺序一样
        //TreeMap:排序
        Map<String,String> kMap = new TreeMap<>();
        while(keys.hasMoreElements()){
            //获取每一个key
            String key = keys.nextElement();
            //根据key获取value
            String value = resourceBundle.getString(key);
            kMap.put(key,value);
        }
        servletContext.setAttribute("stage2Possibility",kMap);
    }
}
