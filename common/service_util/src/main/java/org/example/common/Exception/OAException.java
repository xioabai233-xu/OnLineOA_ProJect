package org.example.common.Exception;

import org.example.common.result.ResultCodeEnum;

public class OAException extends RuntimeException{
    private Integer code;

    private String message;

    /**
    * Description: 通过状态码和错误消息创建异常对象
    * date: 2023/3/21 15:35
    * @author: HongXu Li
    * @since JDK 1.8
    */
    public OAException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
    * Description:    接收枚举类型对象
    * date: 2023/3/21 15:38
    * @author: HongXu Li
    * @since JDK 1.8
    */

    public OAException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMsg());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMsg();
    }


}
