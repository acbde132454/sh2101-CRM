package com.bjpowernode.crm.base.contants;

public enum CrmEnum {

    USER_LOGIN_ACCOUNT("001-001","用户名或密码错误"),
    USER_LOGIN_LOCKED("001-002","账号被锁定"),
    USER_LOGIN_EXPIRE("001-003","账号已失效"),
    USER_LOGIN_ALLOWED_IP("001-004","不允许登录的IP");


    private String code;//描述消息/业务类型
    private String message;

    CrmEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}