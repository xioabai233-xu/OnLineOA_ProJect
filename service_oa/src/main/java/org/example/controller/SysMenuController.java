package org.example.controller;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssignMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.example.common.result.Result;
import org.example.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "菜单管理")
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> nodesList = sysMenuService.findNodes();
        return Result.ok(nodesList);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public Result save(@RequestBody @Validated SysMenu sysMenu){
        boolean isSave = sysMenuService.save(sysMenu);
        if(isSave){
            return Result.ok();
        }
        return Result.fail();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

    /**
    * Description: 查询所有菜单和角色分配的菜单
    * date: 2023/3/21 16:03
    * @author: HongXu Li
    * @since JDK 1.8
    */
    @ApiOperation(value = "查询所有菜单和角色分配的菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){  
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(list);
    }

    /**
    * Description: 角色分配菜单
    * date: 2023/3/21 16:05
    * @author: HongXu Li
    * @since JDK 1.8
    */
    @ApiOperation(value = "角色分配菜单")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuVo assignMenuVo){
        sysMenuService.doAssign(assignMenuVo);
                return Result.ok();
    }

}
