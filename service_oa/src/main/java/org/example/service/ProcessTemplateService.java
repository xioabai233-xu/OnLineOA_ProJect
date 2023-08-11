package org.example.service;/*
 *@title ProcessTemplateService
 *@description
 *@author 14323
 *@version 1.0
 *@create 2023/8/10 13:33
 */

import com.atguigu.model.process.ProcessTemplate;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 14323
 * @ClassName ProcessTemplateService
 * @description: TODO
 * @date 2023年08月10日
 * @version: 1.0
 */
public interface ProcessTemplateService extends IService<ProcessTemplate> {
    IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam);
}
