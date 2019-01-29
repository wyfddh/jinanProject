package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.ICollectionInfoMapper;
import com.tj720.dao.IMuseumShowMapper;
import com.tj720.dto.MuseumShowDto;
import com.tj720.service.ICollectionInfoService;
import com.tj720.service.IMuseumShowService;
import com.tj720.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class IMuseumShowServiceImpl implements IMuseumShowService {

    @Autowired
    private IMuseumShowMapper museumShowMapper;
    @Autowired
    private Config config;


    @Override
    public List<MuseumShowDto> getMuseumShowList(Integer size) {
        List<MuseumShowDto> museumShowList = museumShowMapper.getMuseumShowList(size);
        for(MuseumShowDto museumShow: museumShowList){
            if(StringUtils.isNotBlank(museumShow.getPictureUrl())){
                String pictureUrls = museumShow.getPictureUrl();
                List<String> pics = Arrays.asList(pictureUrls.split(","));
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museumShow.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return museumShowList;
    }

    @Override
    public List<MuseumShowDto> getMuseumShowListByProvice(String provice, Integer size) {
        List<MuseumShowDto> museumShowListByProvice = museumShowMapper.getMuseumShowListByProvice(provice, size);
        for(MuseumShowDto museumShow: museumShowListByProvice){
            if(StringUtils.isNotBlank(museumShow.getPictureUrl())){
                String pictureUrls = museumShow.getPictureUrl();
                List<String> pics = Arrays.asList(pictureUrls.split(","));
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museumShow.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return museumShowListByProvice;
    }

    @Override
    public List<MuseumShowDto> getMuseumShowListByMuseumId(String museumId, Page page) {
        int countMuseumShowListByMuseumId = museumShowMapper.countMuseumShowListByMuseumId(museumId);
        page.setAllRow(countMuseumShowListByMuseumId);
        List<MuseumShowDto> museumShowListByMuseumId = museumShowMapper.getMuseumShowListByMuseumId(museumId, page.getStart(), page.getSize());
        for(MuseumShowDto museumShow: museumShowListByMuseumId){
            if(StringUtils.isNotBlank(museumShow.getPictureUrl())){
                String pictureUrls = museumShow.getPictureUrl();
                List<String> pics = Arrays.asList(pictureUrls.split(","));
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museumShow.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return museumShowListByMuseumId;
    }

    @Override
    public List<MuseumShowDto> getMuseumShowListBySearch(String key, String type, String museumId, Page page) {
        int countMuseumShowListByMuseumId = museumShowMapper.countMuseumShowListBySearch(key, type, museumId);
        page.setAllRow(countMuseumShowListByMuseumId);
        List<MuseumShowDto> museumShowListBySearch = museumShowMapper.getMuseumShowListBySearch(key, type, museumId, page.getStart(), page.getSize());
        for(MuseumShowDto museumShow: museumShowListBySearch){
            if(StringUtils.isNotBlank(museumShow.getPictureUrl())){
                String pictureUrls = museumShow.getPictureUrl();
                List<String> pics = Arrays.asList(pictureUrls.split(","));
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                museumShow.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return museumShowListBySearch;
    }

    @Override
    public MuseumShowDto getMuseumShow(String id) {
        MuseumShowDto museumShow = museumShowMapper.getMuseumShow(id);
        if(StringUtils.isNotBlank(museumShow.getPictureUrl())){
            String pictureUrls = museumShow.getPictureUrl();
            List<String> pics = Arrays.asList(pictureUrls.split(","));
            String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
            museumShow.setPictureUrl(config.getRootUrl() + mainPictureUrl);
        }
        return museumShow;
    }
}
