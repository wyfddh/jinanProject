package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PCEsaleInfoMapper;
import com.tj720.dto.PCEsaleInfoDto;
import com.tj720.service.PCEsaleInfoService;
import com.tj720.service.PCEsalePubUserService;
import com.tj720.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PCEsaleInfoServiceImpl implements PCEsaleInfoService {

    @Autowired
    private PCEsaleInfoMapper pcEsaleInfoMapper;
    @Autowired
    private Config config;

    @Autowired
    private PCEsalePubUserService esalePubUserService;

    @Override
    public List<PCEsaleInfoDto> getEsaleInfoList(String museumId, String type, Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(museumId)) {
            map.put("museumId", museumId);
        }
        if (StringUtils.isNotBlank(type)) {
            map.put("type", type);
        }
        int esaleShowCount = pcEsaleInfoMapper.countEsaleInfo(map);
        page.setAllRow(esaleShowCount);
        map.put("start", page.getStart());
        map.put("end", page.getSize());
        List<PCEsaleInfoDto> esaleShowDtoList = pcEsaleInfoMapper.getEsaleInfoListForPage(map);
        for (PCEsaleInfoDto pcEsaleInfoDto : esaleShowDtoList) {
            pcEsaleInfoDto.setPageUrl(config.getRootUrl() + pcEsaleInfoDto.getPageUrl());
        }
        return esaleShowDtoList;
    }

    public PCEsaleInfoDto getEsaleInfoById(String id, String userId) {
        if (null == id || id.equals("")) {
            return null;
        }
        PCEsaleInfoDto pcEsaleInfoDto = pcEsaleInfoMapper.getEsaleInfoById(id);
        pcEsaleInfoDto.setPageUrl(config.getRootUrl() + pcEsaleInfoDto.getPageUrl());
        if (null != userId && !userId.equals("")) {
            esalePubUserService.addInfomation("4", userId, pcEsaleInfoDto);
        }
        return pcEsaleInfoDto;
    }
}
