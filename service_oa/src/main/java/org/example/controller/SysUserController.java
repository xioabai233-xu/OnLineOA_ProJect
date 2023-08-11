package org.example.controller;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.service.SysUserService;
import org.example.common.MD5.MD5;
import org.example.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户管理")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result<IPage<SysUser>> pageIndex(@PathVariable Long page,
                            @PathVariable Long limit,
                            SysUserQueryVo sysUserQueryVo){
        IPage<SysUser> pageModel = sysUserService.pageList(page, limit, sysUserQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){

        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("update")
    public Result update(@RequestBody SysUser user){
        sysUserService.updateById(user);
        return Result.ok();
    }
    // 密码进行加密， 使用MD5
    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user){
        String password = user.getPassword();
        String passWordMD5 = MD5.encrypt(password);
        user.setPassword(passWordMD5);
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "根据id删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }
//////////////// 状态更新

    @ApiOperation(value = "更新状态")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }





}
