package com.bjpowernode.crm.test;

import com.bjpowernode.crm.base.contants.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.MD5Util;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TestCrm {

    //测试UUID
    @Test
    public void test01(){
        String s = UUID.randomUUID().toString().replace("-","");

        System.out.println(s);
    }

    //测试MD5加密
    @Test
    public void test02(){
        String md5 = MD5Util.getMD5("123");
        System.out.println(md5);
    }


    //添加
   @Test
   public void test03(){
        //因为当前没有启动web服务器，需要手动获取IOC容器
       BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
       UserService userService = (UserService) beanFactory.getBean("userServiceImpl");
       User user = new User();
       user.setId(UUIDUtil.getUUID());
       user.setLoginAct("zhangsan");
       user.setLoginPwd(MD5Util.getMD5("wangwu"));
       userService.addUser(user);
   }

    //根据主键删除
    @Test
    public void test04(){
        //因为当前没有启动web服务器，需要手动获取IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = (UserService) beanFactory.getBean("userServiceImpl");
        userService.deleteUser("3ab30e362c544439b89bdaade6e75f9b");
    }


    //根据主键更新
    @Test
    public void test05(){
        //因为当前没有启动web服务器，需要手动获取IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = (UserService) beanFactory.getBean("userServiceImpl");
        User user = new User();
        user.setId("4b3bc3b8d73d4c13ad4b5ed798143472");
        user.setLoginAct("张三");
        userService.updateUser(user);
    }

    //根据主键查询
    @Test
    public void test06(){
        //因为当前没有启动web服务器，需要手动获取IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = (UserService) beanFactory.getBean("userServiceImpl");
        User user = userService.queryById("4b3bc3b8d73d4c13ad4b5ed798143472");
        System.out.println(user);
    }

    //查询所有
    @Test
    public void test07(){
        //因为当前没有启动web服务器，需要手动获取IOC容器
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        UserService userService = (UserService) beanFactory.getBean("userServiceImpl");
        List<User> users = userService.list();
        for (User user : users) {
            System.out.println(user);

        }
    }

    //测试自定义异常
    @Test
    public void test08(){
        int a = 0;
        try{
            if(a == 0){
                throw new CrmException(CrmEnum.USER_LOGIN_ACCOUNT);
            }
        }catch (CrmException e){
            System.out.println(e.getMessage());
        }
    }

    //比较两个日期字符串的大小
    @Test
    public void test09(){
        String now = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        String expireTime = "1999-10-01 12:00:00";

        System.out.println(expireTime.compareTo(now));
    }

    //测试File方法
    @Test
    public void test10(){
        System.out.println(File.separator);
    }

    //测试init-method
    @Test
    public void test11(){
        BeanFactory beanFactory =
                new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        beanFactory.getBean("resultVo");

    }
}
