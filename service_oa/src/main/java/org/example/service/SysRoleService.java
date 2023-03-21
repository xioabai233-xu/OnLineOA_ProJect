package org.example.service;

import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface SysRoleService extends IService<SysRole> {
    /**
     * Description: 根据用户获取角色数据
     * date: 2023/3/14 16:24
     * @author: HongXu Li
     * @since JDK 1.8findRoleByAdminId
     */
    Map<String,Object> findRoleByAdminId (Long userId);

    /**
     * Description: 分配角色
     * date: 2023/3/14 16:25
     * @author: HongXu Li
     * @since JDK 1.8
     */
    void doAssign(AssignRoleVo assignRoleVo);







}
