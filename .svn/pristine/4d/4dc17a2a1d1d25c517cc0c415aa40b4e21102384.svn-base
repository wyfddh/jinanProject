package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.IMuseumBaseInfoMapper;
import com.tj720.dao.IMuseumShowMapper;
import com.tj720.dto.AreaDto;
import com.tj720.dto.IndexSearchDto;
import com.tj720.dto.MuseumBaseInfoDto;
import com.tj720.service.IMuseumBaseInfoService;
import com.tj720.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IMuseumBaseInfoServiceImpl implements IMuseumBaseInfoService {

    @Autowired
    private IMuseumBaseInfoMapper museumBaseInfoMapper;
    @Autowired
    private IMuseumShowMapper museumShowMapper;
    @Autowired
    private Config config;

    @Override
    public int countOnlineMuseum() {
        return museumBaseInfoMapper.countOnline();
    }

    @Override
    public List<MuseumBaseInfoDto> getMuseumPoorList(Integer size) {
        List<MuseumBaseInfoDto> museumPoorList = museumBaseInfoMapper.getMuseumPoorList(size);
        for (MuseumBaseInfoDto museum:museumPoorList) {
            if(StringUtils.isNotBlank(museum.getPictureUrl())){
                String[] split = museum.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museum.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return museumPoorList;
    }

    @Override
    public List<MuseumBaseInfoDto> getRecommendMuseumList(String province, Integer size) {
        List<MuseumBaseInfoDto> recommendMuseumList = museumBaseInfoMapper.getRecommendMuseumList(province, size);
        for (MuseumBaseInfoDto museum:recommendMuseumList) {
            if(StringUtils.isNotBlank(museum.getPictureUrl())){
                String[] split = museum.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museum.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return recommendMuseumList;
    }

    @Override
    public int countOnlineByProvice(String province) {
        return museumBaseInfoMapper.countOnlineByProvice(province);
    }

    @Override
    public List<IndexSearchDto> getIndexSearchList(String key, String type) {
        return museumBaseInfoMapper.getIndexSearchList(key, type);
    }

    @Override
    public List<MuseumBaseInfoDto> getJctjMuseumList(String province, Integer size) {
        List<MuseumBaseInfoDto> jctjMuseumList = museumBaseInfoMapper.getJxtjMuseumList(province, size);
        for (MuseumBaseInfoDto museum:jctjMuseumList) {
            if(StringUtils.isNotBlank(museum.getPictureUrl())){
                String[] split = museum.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museum.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return jctjMuseumList;
    }

    @Override
    public List<MuseumBaseInfoDto> getRecommendMuseumPageByProviceOrCity(String province, Page page) {
        int countOnlineByProvice = museumBaseInfoMapper.countRecommendMuseumPageByProviceOrCity(province);
        page.setAllRow(countOnlineByProvice);
        List<MuseumBaseInfoDto> recommendMuseumList = museumBaseInfoMapper.getRecommendMuseumPageByProviceOrCity(province, page.getStart(), page.getSize());
        for (MuseumBaseInfoDto museum:recommendMuseumList) {
            if(StringUtils.isNotBlank(museum.getPictureUrl())){
                String[] split = museum.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museum.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return recommendMuseumList;
    }

    @Override
    public MuseumBaseInfoDto getMuseumBaseInfo(String museumId) {
        MuseumBaseInfoDto museumInfo = museumBaseInfoMapper.getMuseumInfo(museumId);
        if(StringUtils.isNotBlank(museumInfo.getPictureUrl())){
            String[] split = museumInfo.getPictureUrl().split(",");
            List<String> pics = Arrays.asList(split);
            String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
            museumInfo.setPictureUrl(config.getRootUrl() + mainPictureUrl);
        }
        if(StringUtils.isNotBlank(museumInfo.getLocalIntroduceVideo())){
            museumInfo.setLocalIntroduceVideo(config.getRootUrl() + museumInfo.getLocalIntroduceVideo());
        }
        return museumInfo;
    }

    @Override
    public List<AreaDto> getProviceData() {
        return museumBaseInfoMapper.getProviceData();
    }

    @Override
    public String getLocalIntroduce(String museumId) {
        String localIntroduceVideo = museumBaseInfoMapper.getLocalIntroduceVideo(museumId);
        if(StringUtils.isNotBlank(localIntroduceVideo)){
            return config.getRootUrl() + localIntroduceVideo;
        }
        return null;
    }

    @Override
    public String getCulturalPromote(String museumId) {
        String culturalPromote = museumBaseInfoMapper.getCulturalPromote(museumId);
        return culturalPromote;
    }

    @Override
    public String getProvinceIdByPinying(String pinyin) {
        if(StringUtils.isBlank(pinyin)){
            return null;
        }
        return museumBaseInfoMapper.getProvinceIdByPinying(pinyin);
    }
}
