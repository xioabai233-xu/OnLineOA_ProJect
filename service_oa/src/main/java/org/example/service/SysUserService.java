package org.example.service;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface SysUserService extends IService<SysUser> {
    /**
    * Description: 更改用户状态：1为正常，0为停用
    * date: 2023/3/15 8:32
    * @author: HongXu Li 
    * @since JDK 1.8
    */
    void updateStatus(Long id , Integer status);

    SysUser getByUsername(String username);
    IPage<SysUser> pageList(Long pageIndex, Long limit, SysUserQueryVo sysUserQueryVo);

    /**
     * //根据用户名获取用户信息
     *
     * @param username
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    Map<String, Object> getUserInfo(String username);
}
