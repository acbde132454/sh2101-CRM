package com.bjpowernode.crm.settings.bean;

import lombok.Data;

@Data
public class Person {

    private String name;
    private int age;


    public static void main(String[] args) {
        Person person = new Person();

    }
}
