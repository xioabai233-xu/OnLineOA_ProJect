package org.example.service.Impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysRoleMapper;
import org.example.mapper.SysUserRoleMapper;
import org.example.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl  extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService  {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Map<String, Object> findRoleByAdminId(Long userId) {
        /// 查询所有角色
        List<SysRole> allRoleList = this.list();

        /// 拥有的角色id
        List<SysUserRole> existUserRolesList = sysUserRoleMapper.selectList(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, userId).select(SysUserRole::getRoleId));
        List<Long> list = existUserRolesList.stream().map(p->p.getRoleId()).collect(Collectors.toList());
        /// 对角色进行分类
        List<SysRole> assignRoleList =new ArrayList<>();
        for(SysRole role : allRoleList){
            /// 已分配
            if(existUserRolesList.contains(role.getId())){
                assignRoleList.add(role);
            }
        }

        Map<String,Object> roleMap =new HashMap<>();
        /// 已分配角色列表
        roleMap.put("assignRoleList",assignRoleList);
        /// 全部角色列表
        roleMap.put("allRoleList",allRoleList);

        return roleMap;
    }
    /// 给用户分配角色
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        /// 先删除
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId,assignRoleVo.getUserId()));
        /// 后添加
        for(Long roleId : assignRoleVo.getRoleIdList()){
            if(StringUtils.isEmpty(roleId)){
                continue;
            }
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(assignRoleVo.getUserId());
            sysUserRoleMapper.insert(userRole);
        }
    }
}
