package org.example.util;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    /**
    * Description: 使用递归方法建菜单
    * date: 2023/3/21 13:43
    * @author: HongXu Li
    * @since JDK 1.8
    */
    public static List<SysMenu> builderTree(List<SysMenu> sysMenuList) {
        // 创建list结合 ， 用于最终数据
        List<SysMenu> tree =new ArrayList<>();
        // 遍历菜单数据
        for(SysMenu item : sysMenuList){
            // 递归入口
            // parentid =0 是入口
            if(item.getParentId().longValue() == 0){
                tree.add(getChildren(item,sysMenuList));
            }
        }
        return tree;
    }

    public static SysMenu getChildren(SysMenu sysMenu,
                                      List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<SysMenu>());
        // 遍历所有菜单数据 ， 判断id和parentid对应关系
        for(SysMenu item : sysMenuList){
            if(sysMenu.getId().longValue() == item.getParentId().longValue() ){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<SysMenu>());
                }
                sysMenu.getChildren().add(getChildren(item,sysMenuList));
            }
        }
        return sysMenu;
    }
}
