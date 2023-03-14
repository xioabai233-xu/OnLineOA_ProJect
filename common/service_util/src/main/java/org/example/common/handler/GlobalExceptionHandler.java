package org.example.common.handler;


import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.example.common.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * å¨å±å¼å¸¸å¤çç±»
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    // 全局异常处理，执行的方法
    @ExceptionHandler(Exception.class)
    @RequestBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }
}
