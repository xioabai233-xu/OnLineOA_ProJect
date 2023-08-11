package org.example.service.Impl;/*
 *@title ProcessTypeServiceImpl
 *@description
 *@author 14323
 *@version 1.0
 *@create 2023/8/4 13:36
 */

import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.process.ProcessType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.ProcessTypeMapper;
import org.example.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 14323
 * @ClassName ProcessTypeServiceImpl
 * @description: TODO
 * @date 2023年08月04日
 * @version: 1.0
 */
@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper,ProcessType> implements ProcessTypeService {

}
