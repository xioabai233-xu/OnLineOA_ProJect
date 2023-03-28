package org.example.service;

import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;


public interface SysUserService extends IService<SysUser> {
    /**
    * Description: 更改用户状态：1为正常，0为停用
    * date: 2023/3/15 8:32
    * @author: HongXu Li 
    * @since JDK 1.8
    */
    void updateStatus(Long id , Integer status);

    SysUser getByUsername(String username);
}
