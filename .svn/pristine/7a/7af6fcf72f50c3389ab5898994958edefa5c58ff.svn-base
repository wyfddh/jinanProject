package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PCEsaleDigitalShowMapper;
import com.tj720.dto.PCEsaleDigitalShowDto;
import com.tj720.service.PCEsaleDigitalShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PCEsaleDigitalShowServiceImpl implements PCEsaleDigitalShowService {

    @Autowired
    private PCEsaleDigitalShowMapper pcEsaleDigitalShowMapper;
    @Autowired
    private Config config;

    @Override
    public List<PCEsaleDigitalShowDto> getEsaleDigitalShowList(String museumId) {
        List<PCEsaleDigitalShowDto> esaleDigitalShowDtoList = pcEsaleDigitalShowMapper.getEsaleDigitalShowList(museumId);
        for (PCEsaleDigitalShowDto esaleDigitalShowDto : esaleDigitalShowDtoList) {
            esaleDigitalShowDto.setPageUrl(config.getRootUrl() + esaleDigitalShowDto.getPageUrl());
        }
        return esaleDigitalShowDtoList;
    }

    @Override
    public JsonResult digitalBrowseNum(String id) {
        JsonResult jsonResult = null;
        try {
            PCEsaleDigitalShowDto dto = pcEsaleDigitalShowMapper.selectById(id);
            Long browseNum = dto.getBrowseNum();
            long i = browseNum + 1;
            dto.setBrowseNum(i);
            pcEsaleDigitalShowMapper.updateById(dto);
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }
}
