package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.ILocalIntroduceMapper;
import com.tj720.dto.LocalIntroduceDto;
import com.tj720.service.ILocalIntroduceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class ILocalIntroduceServiceImpl implements ILocalIntroduceService {

    @Autowired
    private ILocalIntroduceMapper localIntroduceMapper;
    @Autowired
    private Config config;


    @Override
    public List<LocalIntroduceDto> getLocalIntroduceList(Integer size) {
        List<LocalIntroduceDto> localIntroduceList = localIntroduceMapper.getLocalIntroduceList(size);
        for (LocalIntroduceDto localIntroduce:localIntroduceList) {
            if(StringUtils.isNotBlank(localIntroduce.getVideoUrl())){
                localIntroduce.setVideoUrl(config.getRootUrl() + localIntroduce.getVideoUrl());
            }
        }
        return localIntroduceList;
    }
}
