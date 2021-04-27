package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.contants.CrmEnum;
import com.bjpowernode.crm.base.exception.CrmException;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.*;
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

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired TransactionRemarkMapper transactionRemarkMapper;

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

    //线索转换
    @Override
    public void convert(String id,User user,String isCreateTransaction,Transaction transaction) {
        int count = 0;
        //1、根据线索的主键查询线索的信息
        Clue clue = clueMapper.selectByPrimaryKey(id);

        //当该客户不存在的时候，新建客户(按公司名称精准查询)
        Customer customer = new Customer();
        customer.setName(clue.getCompany());
        customer = customerMapper.selectOne(customer);
        if(customer == null){
            //2、先将线索中的客户信息取出来保存在客户表中
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setCreateBy(user.getName());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            //客户描述,可以在客户模块通过修改客户信息完善数据
            //customer.setDescription();
            customer.setName(clue.getCompany());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            customer.setWebsite(clue.getWebsite());

           count = customerMapper.insertSelective(customer);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
        }

        //3、将线索中联系人信息取出来保存在联系人表中
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setAddress(customer.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getName());
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());

        count  = contactsMapper.insertSelective(contacts);
        if(count == 0){
            throw new CrmException(CrmEnum.CLUE_CONVERT);
        }

        //4、线索中的备注信息取出来保存在客户备注和联系人备注中
        //保存联系人备注
        ContactsRemark contactsRemark = new ContactsRemark();
        contactsRemark.setId(UUIDUtil.getUUID());
        contactsRemark.setContactsId(contacts.getId());
        contactsRemark.setCreateBy(user.getName());
        contactsRemark.setCreateTime(DateTimeUtil.getSysTime());


        contactsRemarkMapper.insertSelective(contactsRemark);
        if(count == 0){
            throw new CrmException(CrmEnum.CLUE_CONVERT);
        }

        //保存客户备注
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setId(UUIDUtil.getUUID());
        customerRemark.setCreateBy(user.getName());
        customerRemark.setCreateTime(DateTimeUtil.getSysTime());
        customerRemark.setCustomerId(customer.getId());

        count = customerRemarkMapper.insertSelective(customerRemark);
        if(count == 0){
            throw new CrmException(CrmEnum.CLUE_CONVERT);
        }

        //将"线索和市场活动的关系"转换到"联系人和市场活动的关系中"
        /**
         * 线索和市场活动
         * 线索-->市场活动 1:n
         * 市场活动-->线索 1:n
         *
         * 联系人和市场活动
         * 联系人-->市场活动 1:n
         * 市场活动-->联系人 1:n
         */
        //根据线索的id查询当前线索已经关联的所有的市场活动
        ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
        clueActivityRelation.setClueId(id);
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationMapper.select(clueActivityRelation);
        for (ClueActivityRelation activityRelation : clueActivityRelations) {
            String activityId = activityRelation.getActivityId();
            //保存联系人和市场活动关联表数据
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(activityId);
            contactsActivityRelation.setContactsId(contacts.getId());
            count = contactsActivityRelationMapper.insertSelective(contactsActivityRelation);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
        }

        //5、如果转换过程中发生了交易，创建一条新的交易，交易信息不全，后面可以通过修改交易来完善交易信息
        if("1".equals(isCreateTransaction)){
            //发生交易
            transaction.setId(UUIDUtil.getUUID());
            transaction.setContactsId(contacts.getId());
            transaction.setCreateBy(user.getName());
            transaction.setCreateTime(DateTimeUtil.getSysTime());
            transaction.setCustomerId(customer.getId());
            transaction.setOwner(clue.getOwner());
            count = transactionMapper.insertSelective(transaction);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }

            //6、创建该条交易对应的交易历史以及备注
            //保存交易历史
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setId(UUIDUtil.getUUID());
            transactionHistory.setCreateBy(user.getName());
            transactionHistory.setCreateTime(DateTimeUtil.getSysTime());
            transactionHistory.setExpectedDate(transaction.getExpectedDate());
            transactionHistory.setMoney(transaction.getMoney());
            transactionHistory.setStage(transaction.getStage());
            transactionHistory.setTranId(transaction.getId());

            count = transactionHistoryMapper.insertSelective(transactionHistory);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }

            //保存交易备注
            TransactionRemark transactionRemark = new TransactionRemark();
            transactionRemark.setId(UUIDUtil.getUUID());
            transactionRemark.setCreateBy(user.getName());
            transactionRemark.setCreateTime(DateTimeUtil.getSysTime());
            transactionRemark.setTranId(transaction.getId());

            count = transactionRemarkMapper.insertSelective(transactionRemark);
            if(count == 0){
                throw new CrmException(CrmEnum.CLUE_CONVERT);
            }
        }



        //7、删除线索的备注信息
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setClueId(id);
        clueRemarkMapper.delete(clueRemark);

        //8、删除线索和市场活动的关联关系
        ClueActivityRelation clueActivityRelation1 = new ClueActivityRelation();
        clueActivityRelation1.setClueId(id);
        clueActivityRelationMapper.delete(clueActivityRelation1);

        //9、删除线索
        clueMapper.deleteByPrimaryKey(id);
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
