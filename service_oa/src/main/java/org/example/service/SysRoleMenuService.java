package org.example.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.mapper.SysRoleMenuMapper;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<SysMenu> findListByUserId(Long userid);
}
