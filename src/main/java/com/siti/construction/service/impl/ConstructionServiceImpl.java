package com.siti.construction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siti.construction.entity.BusinessConstruction;
import com.siti.construction.mapper.ConstructionMapper;
import com.siti.construction.service.IConstructionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Solarie on 2020/6/18.
 */
@Service
public class ConstructionServiceImpl extends ServiceImpl<ConstructionMapper, BusinessConstruction> implements IConstructionService {

    @Resource
    ConstructionMapper constructionMapper;

}
