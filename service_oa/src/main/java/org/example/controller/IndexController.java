package org.example.controller;


import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.Exception.OAException;
import org.example.common.MD5.MD5;
import org.example.common.jwt.JwtHelper;
import org.example.common.result.Result;
import org.example.service.SysMenuService;
import org.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "后台登录管理")
@RequestMapping("admin/system/index")
@RestController
public class IndexController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;
    //login
    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public Result login(@RequestBody LoginVo loginVo){
        /*Map<String,Object> map = new HashMap<>();
        map.put("token","admin");
        return Result.ok(map);*/

        // 1 获取输入的用户名密码
        String username = sysUserService.getByUsername(loginVo.getUsername());

        // 2 根据用户名查询数据库
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysUser::getUsername,username);
        SysUser sysuser = sysUserService.getOne(lqw);

        // 3 用户信息是否存在
        if(sysuser == null){
            throw new OAException(201,"用户不存在");
        }

        // 4 判断密码
        String password = sysuser.getPassword();
        String passwordInput = loginVo.getPassword();
        if(!MD5.encrypt(passwordInput).equals(password)){
            throw new OAException(201,"密码错误");
        }

        // 5 判断用户是否被禁用
        if(sysuser.getStatus() == 0){
            throw new OAException(201,"该账户被禁用");
        }

        // 6 使用jwt根据用户id和用户名称生成token字符串
        String token = JwtHelper.createToken(sysuser.getId(), sysuser.getUsername());

        // 7 返回
        Map<String,Object> map =new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    //info
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        // 从请求头获取用户信息
        String token = request.getHeader("header");

        // 从token获取用户的id 或者用户名称
        Long userId = JwtHelper.getUserId(token);

        // 根据用户id查询数据库 把用户信息获取出来
        SysUser sysuser = sysUserService.getById(userId);

        // 根据用户id获取用户菜单列表
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(userId);

        // 根据用户id获取用户按钮列表
        List<String> permsList =  sysMenuService.findUserPermissonByUserId(userId);


        // 6 返回相应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        //TODO 返回用户可以操作菜单
        map.put("routers",routerList);
        //TODO 安徽用户可以操作的按钮
        map.put("buttons",permsList);

        return Result.ok(map);
    }


    //logout

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
