package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.IInformationManageMapper;
import com.tj720.dto.InformationManagerDto;
import com.tj720.service.IInformationManageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IInformationManageServiceImpl implements IInformationManageService {

    @Autowired
    private IInformationManageMapper informationManageMapper;
    @Autowired
    private Config config;

    @Override
    public List<InformationManagerDto> getInformationList(Integer size) {
        List<InformationManagerDto> informationList = informationManageMapper.getInformationList(size);
        for(InformationManagerDto informa: informationList){
            if(StringUtils.isNotBlank(informa.getPictureUrl())){
                informa.setPictureUrl(config.getRootUrl() + informa.getPictureUrl());
            }
        }
        return informationList;
    }
}
