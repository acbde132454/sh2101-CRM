package com.bjpowernode.crm.workbench.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bjpowernode.crm.base.bean.ResultVo;
import com.bjpowernode.crm.workbench.bean.Contacts;
import com.bjpowernode.crm.workbench.bean.Customer;
import com.bjpowernode.crm.workbench.bean.Transaction;
import com.bjpowernode.crm.workbench.mapper.ContactsMapper;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TransactionMapper;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {


    @Autowired
    private TransactionMapper transactionMapper;


    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

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
}
