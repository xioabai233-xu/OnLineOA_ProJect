package org.example.service.Impl;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.vo.system.AssignMenuVo;
import com.atguigu.vo.system.MetaVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.Exception.OAException;
import org.example.mapper.SysMenuMapper;
import org.example.service.SysMenuService;
import org.example.service.SysRoleMenuService;
import org.example.util.MenuHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
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
        // 超级管理员admin账号为：1
        List<SysMenu> sysMenuList = null;
        if(userid.longValue() == 1){
            sysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus,1).orderByAsc(SysMenu::getSortValue));

        }else{
            sysMenuList = sysRoleMenuService.findListByUserId(userid);
        }
        // 构建树形数据
        List<SysMenu> sysMenuTreeList = MenuHelper.builderTree(sysMenuList);

        List<RouterVo> sysRouterList = this.builderMenu(sysMenuTreeList);

        return sysRouterList;
    }

    public static List<RouterVo> builderMenu(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for(SysMenu menu : menus){
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(),menu.getIcon()));
            // 下一层数据部分
            List<SysMenu> children = menu.getChildren();
            if(menu.getType().intValue() ==1){
                // 下载出来下面的隐藏路由
                List<SysMenu> MenuHiddenList = children.stream().filter(p -> !StringUtils.isEmpty(p.getComponent()))
                        .collect(Collectors.toList());
                for(SysMenu item : MenuHiddenList){
                    RouterVo hiddenRouter = new RouterVo();
                    // true 隐藏路由
                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(item));
                    hiddenRouter.setComponent(menu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(item.getName(),item.getIcon()));
                    routers.add(hiddenRouter);
                }
            } else {
                if (!CollectionUtils.isEmpty(children)) {
                    if(children.size() > 0) {
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(builderMenu(children));
                }
            }
        }
        // TODO
        return null;
    }
    public static String getRouterPath(SysMenu menu) {
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue() != 0){
            routerPath = menu.getPath();
        }
        return routerPath;
    }
    @Override
    public List<String> findUserPermissonByUserId(Long userId) {
        // 判断是不是管理员 ， 如果是管理员 ， 查询所有按钮列表
        List<SysMenu> sysMenuList = null;
        if(userId.longValue() == 1){
            // 查询所有菜单列表
            LambdaQueryWrapper<SysMenu> lqw = new LambdaQueryWrapper<>();
            lqw.eq(SysMenu::getStatus,1);
            sysMenuList = baseMapper.selectList(lqw);
        }else{
            // 如果不是管理员
            sysMenuList = this.dind(userId);
        }

        // TODO
         return null;
    }
}
