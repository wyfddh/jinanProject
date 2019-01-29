package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.PCEsaleShowMapper;
import com.tj720.dto.PCEsaleShowDto;
import com.tj720.model.common.MipAttachment;
import com.tj720.service.PCEsalePubUserService;
import com.tj720.service.PCEsaleShowService;
import com.tj720.service.PictureService;
import com.tj720.utils.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PCEsaleShowServiceImpl implements PCEsaleShowService {

    @Autowired
    private PCEsaleShowMapper pcEsaleShowMapper;
    @Autowired
    private Config config;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private PCEsalePubUserService esalePubUserService;

    @Override
    public List<PCEsaleShowDto> getEsaleShowList(String museumId, String type, Page page) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(museumId)) {
            map.put("museumId", museumId);
        }
        if (StringUtils.isNotBlank(type)) {
            map.put("type", type);
        }
        int esaleShowCount = pcEsaleShowMapper.countEsaleShow(map);
        page.setAllRow(esaleShowCount);
        map.put("start", page.getStart());
        map.put("end", page.getSize());
        List<PCEsaleShowDto> esaleShowDtoList = pcEsaleShowMapper.getEsaleShowListForPage(map);
        for (PCEsaleShowDto esaleShowDto : esaleShowDtoList) {
            esaleShowDto.setPageUrl(config.getRootUrl() + esaleShowDto.getPageUrl());
            List<MipAttachment> picList = pictureService.getPicList(esaleShowDto.getPagePictureid());
            esaleShowDto.setPicList(picList);
        }
        return esaleShowDtoList;
    }

    public PCEsaleShowDto getEsaleShowById(String id, String userId) {
        if (null == id || id.equals("")) {
            return null;
        }
        PCEsaleShowDto pcEsaleShowDto = pcEsaleShowMapper.getEsaleShowById(id);
        pcEsaleShowDto.setPageUrl(config.getRootUrl() + pcEsaleShowDto.getPageUrl());
        List<MipAttachment> picList = pictureService.getPicList(pcEsaleShowDto.getShowPictureids());
        pcEsaleShowDto.setPicList(picList);
        if (StringUtils.isNotEmpty(userId)) {
            esalePubUserService.addInfomation("2", userId, pcEsaleShowDto);
        }
        return pcEsaleShowDto;
    }
}
