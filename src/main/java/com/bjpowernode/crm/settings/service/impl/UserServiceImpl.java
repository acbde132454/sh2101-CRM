package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.base.contants.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void addUser(User user){
        //insertSelective:只插入非空值 邮箱不插入
        userMapper.insertSelective(user);
    }

    @Override
    public void deleteUser(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateUser(User user) {
        //updateByPrimaryKeySelective:根据主键更新非空内容
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User queryById(String id) {
        User user = new User();
       /* user.setId(id);
        //select:只能等值查询，不能模糊查询
        List<User> users = userMapper.select(user);*/
        //example查询 针对单表的复杂查询，条件可能是多个，支持模糊查询
        Example example = new Example(User.class);
        //criteria:离线对象
        Example.Criteria criteria = example.createCriteria();
        //参数1:实体类的属性名,参数2:实参
        criteria.andEqualTo("id", id);
        List<User> users = userMapper.selectByExample(example);
        return users.get(0);
    }

    @Override
    public List<User> list() {
        return userMapper.selectAll();
    }

    @Override
    public User login(User user) {
        //先获取用户登录的IP地址
        String remoteAddr = user.getAllowIps();
        user.setAllowIps(null);
        //设置加密之后的密码，因为数据中存储的是加密之后的密码
        user.setLoginPwd(MD5Util.getMD5(user.getLoginPwd()));
        List<User> users = userMapper.select(user);
        if(users.size() == 0){
            throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT);
        }
        //证明用户输入的账号和密码是正确，用户只有一个
        user = users.get(0);
        //账号锁定
        if("0".equals(user.getLockState())){
            throw new CrmException(CrmEnum.USER_LOGIN_LOCKED);
        }

        //账号失效，如果查询出来的失效时间<当前系统时间，说明账号失效了
        /*
          失效时间>系统时间 正数 未失效
          失效时间=系统时间 =0 未失效
          失效时间<系统时间 负数 失效
         */
        if(user.getExpireTime().compareTo(DateTimeUtil.getSysTime()) < 0){
            throw new CrmException(CrmEnum.USER_LOGIN_EXPIRE);
        }

        //不允许登录的ip  abcdef abc
        if(!user.getAllowIps().contains(remoteAddr)){
            throw new CrmException(CrmEnum.USER_LOGIN_ALLOWED_IP);
        }
        return user;
    }

    @Override
    public void updatePwd(User user) {
        //count:影响的记录数
        int count = userMapper.updateByPrimaryKeySelective(user);
        if(count == 0){
            throw new CrmException(CrmEnum.USER_LOGIN_EXPIRE);
        }
    }
}
