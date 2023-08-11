package org.example.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.vo.system.AssignMenuVo;
import com.atguigu.vo.system.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {
    // 查找子节点
    List<SysMenu> findNodes();

    // 根据id 删除菜单
    void removeMenuById(Long id);

    List<SysMenu>  findMenuByRoleId(Long roleId);

    void doAssign(AssignMenuVo assignMenuVo);

    List<RouterVo> findUserMenuListByUserId(Long userid);

    List<String> findUserPermissionByUserId(Long userId);

    /**
     * //
     *  获取用户权限
     * @param userId
     * @return java.util.List<java.lang.String>
     **/
    List<String> findUserPermsList(Long userId);
}
