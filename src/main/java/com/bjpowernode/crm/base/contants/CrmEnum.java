package com.bjpowernode.crm.base.contants;

public enum CrmEnum {

    USER_LOGIN_ACCOUNT("001-001","用户名或密码错误"),
    USER_LOGIN_LOCKED("001-002","账号被锁定"),
    USER_LOGIN_EXPIRE("001-003","账号已失效"),
    USER_LOGIN_ALLOWED_IP("001-004","不允许登录的IP"),
    USER_UPDATE_PWD("001-005","更新密码失败"),
    ACTIVITY_ADD("002-001","添加市场活动失败"),
    ACTIVITY_UPDATE("002-002","修改市场活动失败"),
    ACTIVITY_DELETE("002-003","删除市场活动失败"),
    ACTIVITY_REMARK_ADD("002-004","添加市场活动备注失败"),
    ACTIVITY_REMARK_UPDATE("002-005","修改市场活动备注失败"),
    ACTIVITY_REMARK_DELETE("002-006","删除市场活动备注失败"),
    CLUE_ADD("003-001","添加线索失败"),
    CLUE_RELATION_ACTIVITY_BIND("003-002","线索关联市场活动失败"),
    CLUE_RELATION_ACTIVITY_UNBIND("003-003","线索关联市场活动失败"),
    CLUE_CONVERT("003-004","线索转换失败");



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
