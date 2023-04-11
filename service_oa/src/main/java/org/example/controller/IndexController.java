package org.example.controller;


import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.LoginVo;
import com.atguigu.vo.system.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Result login(@RequestBody LoginVo loginVo) {
        SysUser sysUser = sysUserService.getByUsername(loginVo.getUsername());
/*        if(null == sysUser) {
            throw new OAException(201,"用户不存在");
        }
        if(!MD5.encrypt(loginVo.getPassword()).equals(loginVo.getPassword())) {
            throw new OAException(201,"密码错误");
        }
        if(sysUser.getStatus().intValue() == 0) {
            throw new OAException(201,"用户被禁用");
        }*/

        Map<String, Object> map = new HashMap<>();
        map.put("token", JwtHelper.createToken(sysUser.getId(), sysUser.getUsername()));
        return Result.ok(map);
    }

    //info
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        // 从请求头获取用户信息
        String token = request.getHeader("token");

        // 从token获取用户的id 或者用户名称
        Long userId = 2L; //JwtHelper.getUserId(token);

        // 根据用户id查询数据库 把用户信息获取出来
        SysUser sysuser = sysUserService.getById(userId);

        // 根据用户id获取用户菜单列表
        List<RouterVo> routerList = sysMenuService.findUserMenuListByUserId(sysuser.getId());

        // 根据用户id获取用户按钮列表
        List<String> permsList =  sysMenuService.findUserPermissionByUserId(sysuser.getId());


        // 6 返回相应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("routers",routerList);
        map.put("buttons",permsList);

        // 返回map
        return Result.ok(map);
    }


    //logout

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
