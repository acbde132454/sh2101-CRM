package com.bjpowernode.crm.base.bean;

import lombok.Data;

/**
 * 该类用于给客户端返回结果
 * 结果:
 *   告诉客户端操作是否成功
 *   可以返回消息
 *
 *   返回用户所需要的数据
 */
@Data
public class ResultVo {

    private boolean isOk;//操作是否成功
    private String message;//给客户端返回的消息

}