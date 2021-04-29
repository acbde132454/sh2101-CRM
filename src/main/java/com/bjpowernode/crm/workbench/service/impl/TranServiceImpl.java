package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.base.util.DateTimeUtil;
import com.bjpowernode.crm.base.util.UUIDUtil;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.workbench.bean.*;
import com.bjpowernode.crm.workbench.mapper.*;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class TranServiceImpl implements TranService {


    @Autowired
    private TransactionMapper transactionMapper;


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private TransactionRemarkMapper transactionRemarkMapper;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivityMapper activityMapper;


    @Override
    public ResultVo tranQuery(Transaction transaction) {

        Example example = new Example(Transaction.class);
        Example.Criteria criteria = example.createCriteria();

        //1、是否输入交易名称
        if(!StrUtil.isEmpty(transaction.getName())){
            criteria.andLike("name","%" + transaction.getName() + "%");
        }

        /** 2、客户名称的查询
         *
         *  2.1 把交易中的customerId中存储的客户名称取出来查询客户表
         *  2.2 把查询出来的客户表的主键存储到集合中，再根据example进行查询
         */
        if(!StrUtil.isEmpty(transaction.getCustomerId())){
            Example example1 = new Example(Customer.class);
            example1.createCriteria().andLike("name","%" + transaction.getCustomerId() + "%");
            List<Customer> customers = customerMapper.selectByExample(example1);
            List<String> cids = new ArrayList<>();
            for (Customer customer : customers) {
                cids.add(customer.getId());
            }
            criteria.andIn("customerId",cids);
        }

        //3、阶段的查询
        if(!StrUtil.isEmpty(transaction.getStage())){
            criteria.andEqualTo("stage",transaction.getStage());
        }

        //4、类型的查询
        if(!StrUtil.isEmpty(transaction.getType())){
            criteria.andEqualTo("type",transaction.getType());
        }

        //5、来源的查询
        if(!StrUtil.isEmpty(transaction.getSource())){
            criteria.andEqualTo("source",transaction.getSource());
        }

        /** 6、联系人的查询
         *
         *  6.1 把交易中的contactsIdd中存储的联系人名称取出来查询联系人表
         *  6.2 把查询出来的联系人表的主键存储到集合中，再根据example进行查询
         */
        if(!StrUtil.isEmpty(transaction.getCustomerId())){
            Example example1 = new Example(Contacts.class);
            example1.createCriteria().andLike("fullname",transaction.getContactsId());
            List<Contacts> customers = contactsMapper.selectByExample(example1);
            List<String> cids = new ArrayList<>();
            for (Contacts contacts : customers) {
                cids.add(contacts.getId());
            }
            criteria.andIn("contactsId",cids);
        }


        ResultVo resultVo = new ResultVo();
        List<Transaction> transactions = transactionMapper.selectByExample(example);

        resultVo.setT(transactions);
        return resultVo;
    }

    @Override
    public List<String> queryCustomerName(String customerName) {
        Example example = new Example(Customer.class);
        example.createCriteria().andLike("name","%" + customerName + "%");
        List<Customer> customers = customerMapper.selectByExample(example);

        List<String> names = new ArrayList<>();
        for (Customer customer : customers) {
            names.add(customer.getName());
        }
        return names;
    }

    @Override
    public Transaction toDetail(String id) {
        //查询自身的信息
        Transaction transaction = transactionMapper.selectByPrimaryKey(id);

        //处理所有者信息
        User user = userMapper.selectByPrimaryKey(transaction.getOwner());
        transaction.setOwner(user.getName());

        //公司名称
        Customer customer = customerMapper.selectByPrimaryKey(transaction.getCustomerId());
        transaction.setCustomerId(customer.getName());

        //联系人名称
        Contacts contacts = contactsMapper.selectByPrimaryKey(transaction.getContactsId());
        transaction.setContactsId(contacts.getFullname());

        //市场活动名称
        Activity activity = activityMapper.selectByPrimaryKey(transaction.getActivityId());
        transaction.setActivityId(activity.getName());

        //查询备注信息
        TransactionRemark transactionRemark = new TransactionRemark();
        transactionRemark.setTranId(id);
        List<TransactionRemark> transactionRemarks = transactionRemarkMapper.select(transactionRemark);
        transaction.setTransactionRemarks(transactionRemarks);

        //交易历史信息
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTranId(id);
        List<TransactionHistory> transactionHistories = transactionHistoryMapper.select(transactionHistory);
        transaction.setTransactionHistories(transactionHistories);
        return transaction;
    }

    //异步查询当前交易对应的图标内容
    //? extends Object:泛型上限 至多Object或者是Object子类 ? super Transaction：泛型下限
    @Override
    public List<Map<String,? extends Object>> queryStage(String id,Map<String,String> stage2PossibilityMap,Integer index1,User user) {
        //获取到当前交易所处的阶段
        Transaction transaction = transactionMapper.selectByPrimaryKey(id);
        //当前交易所处的阶段 current:当前的意思
        String currentStage = transaction.getStage();
        //当前的可能性 0
        String currentPossibility = transaction.getPossibility();

        //因为待会需要遍历所有阶段和可能性，并且获取索引位置，而map中遍历没有索引，把map转换称List集合
        //先把map转换称Set集合
        Set<Map.Entry<String, String>> entries = stage2PossibilityMap.entrySet();
        //List构造方法可以接收一个Set集合
        List<Map.Entry<String, String>> stagList = new ArrayList<>(entries);

        //先找出当前交易所处阶段在所有阶段中的索引位置
        //index1:点击之后当前交易对应阶段的索引位置
        TransactionHistory transactionHistory = new TransactionHistory();
        int pointer = 0;
        if(index1 != null){
            pointer = index1;
            //点击之后把当前点击交易对应阶段的内容更新一下
            currentStage = stagList.get(index1).getKey();
            currentPossibility = stagList.get(index1).getValue();

            transaction.setStage(currentStage);
            transaction.setPossibility(currentPossibility);
            transaction.setId(id);
            //更新数据库状态
            transactionMapper.updateByPrimaryKeySelective(transaction);

            //添加一条交易历史记录
            transactionHistory.setId(UUIDUtil.getUUID());
            transactionHistory.setTranId(id);
            transactionHistory.setStage(currentStage);
            transactionHistory.setMoney(transaction.getMoney());
            transactionHistory.setExpectedDate(transaction.getExpectedDate());
            transactionHistory.setCreateBy(user.getName());
            transactionHistory.setCreateTime(DateTimeUtil.getSysTime());
            transactionHistory.setPossibility(currentPossibility);
            //把交易历史插入到数据中
            transactionHistoryMapper.insertSelective(transactionHistory);
        }else{
            for(int i = 0; i < stagList.size(); i++){
                String stage = stagList.get(i).getKey();
                if(currentStage.equals(stage)){
                    //这里就是当前交易阶段在所有阶段中的索引位置
                    pointer = i;
                    //结束本层循环 continue:跳过本次循环
                    break;
                }
            }
        }

        //定义一个List<Map<String,String>>数据结构保存当前交易的所有阶段图标的表示
        List<Map<String,? extends Object>> mapList = new ArrayList<>();

        //判断不同交易对应的交易阶段图标到底是哪一种情况
        //交易失败了 确定前面七个都是黑圈，后两个是X，谁是红X,谁是黑X不能确定
        if("0".equals(currentPossibility)){
            //解决后面两个x谁是红X，谁是黑X
            for(int i = 0; i < stagList.size(); i++){
                Map<String,String> map = new HashMap<>();
                //取出每个阶段
                String stage = stagList.get(i).getKey();
                String possibility = stagList.get(i).getValue();
                if("0".equals(possibility)){
                    if(currentStage.equals(stage)){
                        //当前阶段的图标是红X
                        System.out.println("红X");
                        map.put("type","红叉");
                        map.put("text",stage);
                        map.put("possibility",possibility);
                        //给每个阶段添加索引
                        map.put("index",i + "");
                    }else{
                        //当前阶段就是黑X
                        System.out.println("黑X");
                        map.put("type","黑叉");
                        map.put("text",stage);
                        map.put("index",i + "");
                    }
                }else{
                    System.out.println("黑圈");
                    map.put("type","黑圈");
                    map.put("text",stage);
                    map.put("possibility",possibility);
                    map.put("index",i + "");
                }
                mapList.add(map);
            }

        }else{
            //交易进行中  确定肯定有绿圈，肯定有锚点，黑圈有没有不一定，有两个黑X能确定
            //获取所有阶段中第一个交易可能性为0的阶段的索引位置
            int index = 0;
            for(int i = 0; i < stagList.size(); i++){
                //每一个阶段对应的可能性
                String possibility = stagList.get(i).getValue();
                if("0".equals(possibility)){
                    //这里就是当前交易阶段在所有阶段中的索引位置
                    index = i;
                    //结束本层循环 continue:跳过本次循环
                    break;
                }
            }

            //遍历所有阶段图标，因为总共9个阶段，需要九个图标
            for(int i = 0; i < stagList.size(); i++){
                String stage = stagList.get(i).getKey();
                String possibility = stagList.get(i).getValue();
                Map<String,String> map = new HashMap<>();
                if(i < pointer){
                    //都是绿圈
                    System.out.println("绿圈");
                    map.put("type","绿圈");
                    map.put("text",stage);
                    map.put("possibility",possibility);
                    map.put("index",i + "");
                }else if(i == pointer){
                    //是锚点
                    System.out.println("锚点");
                    map.put("type","锚点");
                    map.put("text",stage);
                    map.put("possibility",possibility);
                    map.put("index",i + "");
                }else if(i > pointer && i < index){
                    //黑圈
                    System.out.println("黑圈");
                    map.put("type","黑圈");
                    map.put("text",stage);
                    map.put("possibility",possibility);
                    map.put("index",i + "");
                }else{
                    //黑X
                    System.out.println("黑X");
                    map.put("type","黑叉");
                    map.put("text",stage);
                    map.put("possibility",possibility);
                    map.put("index",i + "");
                }
                mapList.add(map);
            }

        }
        Map<String,TransactionHistory> map = new HashMap<>();
        map.put("transactionHistory",transactionHistory);
        mapList.add(map);
        return mapList;
    }
}
