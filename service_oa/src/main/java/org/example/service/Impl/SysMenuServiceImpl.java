package org.example.service.Impl;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.vo.system.AssignMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.Exception.OAException;
import org.example.common.handler.GlobalExceptionHandler;
import org.example.common.result.Result;
import org.example.mapper.SysMenuMapper;
import org.example.service.SysMenuService;
import org.example.service.SysRoleMenuService;
import org.example.util.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public List<SysMenu> findNodes(){

        // 1 查询所有菜单数据
        List<SysMenu> sysMenuList = baseMapper.selectList(null);
        // 2 构建树形结构
        List<SysMenu> list = MenuHelper.builderTree(sysMenuList);

        return list;

    }

    @Override
    public void removeMenuById(Long id) {
        // 判断当前菜单是否有下一层菜单
        LambdaQueryWrapper<SysMenu> lqw =new LambdaQueryWrapper<>();
        lqw.eq(SysMenu::getParentId,id);
        Integer count = baseMapper.selectCount(lqw);
        if(count > 0){
            throw new OAException(201,"菜单不能删除");
        }
        baseMapper.deleteById(id);
    }

    @Override
    public List<SysMenu> findMenuByRoleId(Long roleId) {
        // 1 先查询所有菜单 - 1、可用 0、不可用
        List<SysMenu> sysMenuList = baseMapper.selectList(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus,1));
        // 根据角色id查询 （roleId） 查询角色菜单关系表 角色id对应的菜单id
        List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        // 根据获取的菜单id最终获取到菜单对象
        List<Long> menuIdList = roleMenuList.stream().map(p -> p.getMenuId()).collect(Collectors.toList());
        // 拿着菜单id跟所有id进行比较 ， 如果相同则封装
        // 使用所有id判断是否包含菜单id
        sysMenuList.forEach(p->{
            if(menuIdList.contains(p.getId())){
                p.setSelect(true);
            }else{
                p.setSelect(false);
            }
        });
        // 返回规定格式对象
        List<SysMenu> list = MenuHelper.builderTree(sysMenuList);
        return list;

    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
        // 根据角色id 删除菜单角色表 分配数据
        sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,assignMenuVo.getRoleId()));

        // 从参数里面获取角色 重新分配菜单id列表
        // 进行遍历，把每个id数据添加菜单角色表
        List<Long> menuIdList = assignMenuVo.getMenuIdList();
        menuIdList.stream().forEach(p -> {
            if(!StringUtils.isEmpty(p)){
                SysRoleMenu sysRoleMenu =new SysRoleMenu();
                sysRoleMenu.setMenuId(p);
                sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
                sysRoleMenuService.save(sysRoleMenu);
            }
        });

    }

    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userid) {




        return null;
    }

    @Override
    public List<String> findUserPermissonByUserId(Long userId) {
        return null;
    }
}
