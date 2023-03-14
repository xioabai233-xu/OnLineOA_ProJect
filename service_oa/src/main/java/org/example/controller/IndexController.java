package org.example.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台登录管理")
@RequestMapping("admin/system/index")
@RestController
public class IndexController {

    //login
    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public Result login(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin");
        return Result.ok(map);
    }

    //info
    @GetMapping("info")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
    }


    //logout

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
