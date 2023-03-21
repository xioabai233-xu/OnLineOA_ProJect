package org.example.service.Impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.SysRoleMapper;
import org.example.mapper.SysUserRoleMapper;
import org.example.service.SysRoleService;
import org.example.service.SysUserRoleService;
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
    private SysUserRoleService sysUserRoleService;

    @Override
    public Map<String, Object> findRoleByAdminId(Long userId) {
        /// 查询所有角色
        List<SysRole> allRoleList = this.list();

        /// 拥有的角色id, 根据userid查询 角色用户关系表，查询userid对应的角色表
        LambdaQueryWrapper<SysUserRole> lqw =new LambdaQueryWrapper<>();
        lqw.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> list = sysUserRoleService.list(lqw);

        // 从查询的用户id对应的list集合，获取所有角色id
        List<Long> existRoleIdList = list.stream().map(p -> p.getRoleId()).collect(Collectors.toList());

        /// 对角色进行分类
        List<SysRole> assignRoleList =new ArrayList<>();
        for(SysRole role : allRoleList){
            /// 已分配
            if(existRoleIdList.contains(role.getId())){
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
        /// 先删除 根据用户角色关系表里，根据userid删除
        LambdaQueryWrapper<SysUserRole> lqw =new LambdaQueryWrapper<>();
        lqw.eq(SysUserRole::getUserId,assignRoleVo.getUserId());
        sysUserRoleService.remove(lqw);
        /// 后添加 重新进行分配
        for(Long roleId : assignRoleVo.getRoleIdList()){
            if(StringUtils.isEmpty(roleId)){
                continue;
            }
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(assignRoleVo.getUserId());
            sysUserRoleService.save(userRole);
        }
    }
}
