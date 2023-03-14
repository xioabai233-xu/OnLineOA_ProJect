package org.example.controller;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.result.Result;
import org.example.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    // 注入service
    @Autowired
    private SysRoleService sysRoleService;




/*    // 查询所有角色
    @GetMapping("findAll")
    public List<SysRole> findAll(){
        // 调用sevice方法
        List<SysRole> list = sysRoleService.list();
        return list;
    }*/

    // 查询所有角色
    @ApiOperation(value = "查询所有角色")
    @GetMapping("findAll")
    public Result<List<SysRole>> findAll(){
        // 调用sevice方法
        // 模拟异常
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    // 条件查询
    @ApiOperation(value = "条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result PageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo){
        // 1 创建Page对象，传递分页条件
        Page<SysRole> pageParam = new Page<>(page,limit);
        // 2 判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> lqw =new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            lqw.like(SysRole::getRoleName,roleName);
        }
        IPage<SysRole> pageModel = sysRoleService.page(pageParam,lqw);

        return Result.ok(pageModel);
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("get/{id}")
    public Result queryRole(@PathVariable Long id){
        SysRole byId = sysRoleService.getById(id);
        return Result.ok(byId);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody SysRole role){
        sysRoleService.updateById(role);
        return Result.ok();
    }

    @ApiOperation(value = "根据id删除")
    @DeleteMapping("remove/{id}")
    public Result deleteById(@PathVariable Long id){
        sysRoleService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("deleteList")
    public Result DeleteList(@RequestBody List<Long> idList){
        sysRoleService.removeByIds(idList);
        return Result.ok();
    }

    // 全局异常处理 特定异常处理 自定义异常处理
    //
    // 创建类 ，在类上添加注解
    // 在类的执行方法上添加注解，指定哪个异常出现会执行
    //

    @ApiOperation(value = "增加角色")
    @PutMapping("save")
    public Result addNewRole(@RequestBody @Validated SysRole role){
        sysRoleService.save(role);
        return Result.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String,Object> roleMap = sysRoleService.findRoleByAdminId(userId);
        return Result.ok(roleMap);
    }



}
