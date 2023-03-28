package org.example.service.Impl;

import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysUserMapper;
import org.example.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public void updateStatus(Long id, Integer status) {
        // 根据userid查询用户对象
        SysUser sysUser = baseMapper.selectById(id);
        // 设置修改状态值
        sysUser.setStatus(status);
        // 调用方法进行修改
        baseMapper.updateById(sysUser);

    }

    @Override
    public SysUser getByUsername(String username) {

        return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername,username));
    }

}
