package org.example.service.Impl;

import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.RouterVo;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.service.SysMenuService;
import org.example.mapper.SysUserMapper;
import org.example.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

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

    @Override
    public IPage<SysUser> pageList(Long pageIndex, Long limit, SysUserQueryVo sysUserQueryVo) {
        // 构造分页
        Page<SysUser> pageParam = new Page<>(pageIndex,limit);
        LambdaQueryWrapper<SysUser> lqw = new LambdaQueryWrapper<>();
        // 获取条件值
        String name = sysUserQueryVo.getKeyword();
        String begin = sysUserQueryVo.getCreateTimeBegin();
        String end = sysUserQueryVo.getCreateTimeEnd();

        if(!StringUtils.isEmpty(name)){
            lqw.like(SysUser::getName,name);
        }if(!StringUtils.isEmpty(begin)){
            lqw.ge(SysUser::getCreateTime,begin);
        }if(!StringUtils.isEmpty(end)){
            lqw.le(SysUser::getCreateTime,end);
        }

        IPage<SysUser> page = this.page(pageParam, lqw);
        return page;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = this.getByUsername(username);

        //根据用户id获取菜单权限值
        List<RouterVo> routerVoList = sysMenuService.findUserMenuListByUserId(sysUser.getId());
        //根据用户id获取用户按钮权限
        List<String> permsList = sysMenuService.findUserPermsList(sysUser.getId());

        result.put("name", sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //当前权限控制使用不到，我们暂时忽略
        result.put("roles",  new HashSet<>());
        result.put("buttons", permsList);
        result.put("routers", routerVoList);
        return result;
    }

}
