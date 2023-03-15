package org.example.controller;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.result.Result;
import org.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@Api(tags = "用户管理")
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "用户条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        SysUserQueryVo sysUserQueryVo){
        // 构造分页
        Page<SysUser> pageParam = new Page<>(page,limit);
        // 初始化查询
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        // 获取条件值
        String name = sysUserQueryVo.getKeyword();
        String beginTime = sysUserQueryVo.getCreateTimeBegin();
        String endTime = sysUserQueryVo.getCreateTimeEnd();
        if(!StringUtils.isEmpty(name)){
            lqw.like(SysUser::getName,name);
        }if(!StringUtils.isEmpty(beginTime)){
            lqw.ge(SysUser::getCreateTime,beginTime);
        }if(!StringUtils.isEmpty(endTime)){
            lqw.le(SysUser::getCreateTime,endTime);
        }
        IPage<SysUser> pageModel = sysUserService.page(pageParam,lqw);
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

    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser user){
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "根据id删除")
    @PostMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }



}
