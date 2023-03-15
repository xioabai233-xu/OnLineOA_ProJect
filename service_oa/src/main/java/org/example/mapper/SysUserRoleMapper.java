package org.example.mapper;

import com.atguigu.model.system.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
}
