package org.example.service.Impl;

import com.atguigu.model.system.SysUserRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.service.SysUserRoleService;
import org.example.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}
