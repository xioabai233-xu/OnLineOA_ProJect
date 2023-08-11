package org.example.service.Impl;/*
 *@title ProcessTemplateServiceImpl
 *@description
 *@author 14323
 *@version 1.0
 *@create 2023/8/10 13:34
 */

import com.atguigu.model.process.ProcessTemplate;
import com.atguigu.model.process.ProcessType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.ProcessTemplateMapper;
import org.example.service.ProcessTemplateService;
import org.example.service.ProcessTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 14323
 * @ClassName ProcessTemplateServiceImpl
 * @description: TODO
 * @date 2023年08月10日
 * @version: 1.0
 */
@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {
    @Autowired
    private ProcessTemplateMapper processTemplateMapper;

    @Autowired
    private ProcessTypeService processTypeService;

    @Override
    public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
        Page<ProcessTemplate> page = processTemplateMapper.selectPage(pageParam, new LambdaQueryWrapper<ProcessTemplate>().orderByDesc(ProcessTemplate::getId));
        List<ProcessTemplate> ProcessTemplateRecords = page.getRecords();
        List<Long> templateIdList = ProcessTemplateRecords.stream().map(ProcessTemplate::getProcessTypeId).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(templateIdList)){
            Map<Long, ProcessType> processTypeToProcessTypeMap = processTypeService.list(new LambdaQueryWrapper<ProcessType>().in(ProcessType::getId, templateIdList)).stream().collect(Collectors.toMap(ProcessType::getId, ProcessType -> ProcessType));
            for (ProcessTemplate processtemplate :
                    ProcessTemplateRecords) {
                ProcessType processType = processTypeToProcessTypeMap.get(processtemplate.getProcessTypeId());
                if(processType == null){
                    continue;
                }
                processtemplate.setProcessTypeName(processType.getName());
            }
        }
        return page;
    }
}
