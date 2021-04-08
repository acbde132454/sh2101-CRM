package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.bean.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    void deleteUser(String id);

    void updateUser(User user);

    User queryById(String id);

    List<User> list();

    User login(User user);
}
