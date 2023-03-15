package org.example.service.Impl;

import com.atguigu.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysUserMapper;
import org.example.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
}
