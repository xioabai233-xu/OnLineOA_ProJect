package org.example.common.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    LOGIN_ERROR(202, "认证失败"),

    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    LOGIN_AUTH(208, "未登录"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_STOP(300,"账号停用" ),
    LOGIN_MOBLE_ERROR(301,"登录错误" );

    private  Integer code;
    private  String msg;
    private ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
