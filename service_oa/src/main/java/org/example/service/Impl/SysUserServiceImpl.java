package org.example.service.Impl;

import com.atguigu.model.system.SysUser;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysUserMapper;
import org.example.mapper.SysUserRoleMapper;
import org.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        SysUser sysUser = this.getById(id);
        if(sysUser.getStatus() ==1 ){
            sysUser.setStatus(status);
        }else{
            sysUser.setStatus(0);
        }
        this.updateById(sysUser);
    }

    @Transactional
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,assignRoleVo.getUserId()));

        for(Long roleId : assignRoleVo.getRoleIdList()){
            if(StringUtils.isEmpty(roleId)){
                continue;
            }
            SysUserRole sysUserRole =new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());  /// 用户id
            sysUserRole.setRoleId(roleId); /// 角色id
            sysUserRoleMapper.insert(sysUserRole);
        }
    }
}
