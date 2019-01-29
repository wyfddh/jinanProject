package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.ICollectionInfoMapper;
import com.tj720.dao.IMuseumShowMapper;
import com.tj720.dto.AreaDto;
import com.tj720.dto.CollectionInfoDto;
import com.tj720.dto.ISysDictDto;
import com.tj720.dto.SelectDto;
import com.tj720.service.ICollectionInfoService;
import com.tj720.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ICollectionInfoServiceImpl implements ICollectionInfoService {

    @Autowired
    private ICollectionInfoMapper collectionInfoMapper;
    @Autowired
    private IMuseumShowMapper museumShowMapper;
    @Autowired
    private Config config;

    @Override
    public int countCollection() {
        return collectionInfoMapper.countCollection();
    }

    @Override
    public int countCollectionByProvice(String province) {
        return collectionInfoMapper.countCollectionByProvice(province);
    }

    @Override
    public List<CollectionInfoDto> getCollectionInfoListByProvice(String provice, Integer size) {
        List<CollectionInfoDto> collectionInfoListByProvice = collectionInfoMapper.getCollectionInfoListByProvice(provice, size);
        for (CollectionInfoDto collectionInfo : collectionInfoListByProvice) {
            if (StringUtils.isNotBlank(collectionInfo.getPictureUrl())) {
                String[] split = collectionInfo.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                List<Map<String, String>> pictureList = museumShowMapper.getPictureUrl(pics);
                List<String> pictureUrlList = new ArrayList<>();
                for (Map<String, String> picture: pictureList){
                    String isMain = (String)picture.get("isMain");
                    String url = (String)picture.get("url");
                    if(StringUtils.isNotBlank(isMain) && "1".equals(isMain)){
                        //将主图的大图换成小图
                        String quanUrl = config.getRootUrl() + url;
                        if(quanUrl.contains("/da/")){
                            /*String substring = quanUrl.substring(quanUrl.lastIndexOf("/") + 1, quanUrl.length());
                            String kuozhanming = substring.substring(substring.lastIndexOf(".")+1,substring.length());
                            String name = substring.substring(0, substring.lastIndexOf("."));
                            quanUrl = quanUrl.replace(substring, "THUMB/T" + name + ".JPG");
                            quanUrl = quanUrl.replaceFirst("/da/","/xiao/");*/
                        }
                        collectionInfo.setPictureUrl(quanUrl);
                    }
                    if(StringUtils.isNotBlank(url)){
                        pictureUrlList.add(config.getRootUrl() + url);
                    }
                }
                collectionInfo.setPictureList(pictureUrlList);
            }
        }
        return collectionInfoListByProvice;
    }

    @Override
    public List<CollectionInfoDto> getCollectionInfoListByMuseumId(String museumId, Page page) {
        int countCollectionInfoListByMuseumId = collectionInfoMapper.countCollectionInfoListByMuseumId(museumId);
        page.setAllRow(countCollectionInfoListByMuseumId);
        List<CollectionInfoDto> collectionInfoListByProvice = collectionInfoMapper.getCollectionInfoListByMuseumId(museumId, page.getStart(), page.getSize());
        for (CollectionInfoDto collectionInfo : collectionInfoListByProvice) {
            if (StringUtils.isNotBlank(collectionInfo.getPictureUrl())) {
                String[] split = collectionInfo.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                List<Map<String, String>> pictureList = museumShowMapper.getPictureUrl(pics);
                List<String> pictureUrlList = new ArrayList<>();
                for (Map<String, String> picture: pictureList){
                    String isMain = (String)picture.get("isMain");
                    String url = (String)picture.get("url");
                    if(StringUtils.isNotBlank(isMain) && "1".equals(isMain)){
                        //将主图的大图换成小图
                        String quanUrl = config.getRootUrl() + url;
                        if(quanUrl.contains("/da/")){
                            /*String substring = quanUrl.substring(quanUrl.lastIndexOf("/") + 1, quanUrl.length());
                            String kuozhanming = substring.substring(substring.lastIndexOf(".")+1,substring.length());
                            String name = substring.substring(0, substring.lastIndexOf("."));
                            quanUrl = quanUrl.replace(substring, "THUMB/T" + name + ".JPG");
                            quanUrl = quanUrl.replaceFirst("/da/","/xiao/");*/
                        }
                        collectionInfo.setPictureUrl(quanUrl);
                    }
                    if(StringUtils.isNotBlank(url)){
                        pictureUrlList.add(config.getRootUrl() + url);
                    }
                }
                collectionInfo.setPictureList(pictureUrlList);
            }
        }
        return collectionInfoListByProvice;
    }

    @Override
    public List<CollectionInfoDto> getCollectionInfoListBySearch(String key, String collectionType, String collectionYear, String museumId, String level, String textures,String province,String city, Page page) {
        int countCollectionInfoListBySearch = collectionInfoMapper.countCollectionInfoListBySearch(key, collectionType, collectionYear, museumId, level, textures, province, city);
        page.setAllRow(countCollectionInfoListBySearch);
        List<CollectionInfoDto> collectionInfoListBySearch = collectionInfoMapper.getCollectionInfoListBySearch(key, collectionType, collectionYear, museumId, level, textures,province, city, page.getStart(), page.getSize());
        for (CollectionInfoDto collectionInfo : collectionInfoListBySearch) {
            if (StringUtils.isNotBlank(collectionInfo.getPictureUrl())) {
                String[] split = collectionInfo.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                List<Map<String, String>> pictureList = museumShowMapper.getPictureUrl(pics);
                List<String> pictureUrlList = new ArrayList<>();
                for (Map<String, String> picture: pictureList){
                    String isMain = (String)picture.get("isMain");
                    String url = (String)picture.get("url");
                    if(StringUtils.isNotBlank(isMain) && "1".equals(isMain)){
                        //将主图的大图换成小图
                        String quanUrl = config.getRootUrl() + url;
                        if(quanUrl.contains("/da/")){
                            /*String substring = quanUrl.substring(quanUrl.lastIndexOf("/") + 1, quanUrl.length());
                            String kuozhanming = substring.substring(substring.lastIndexOf(".")+1,substring.length());
                            String name = substring.substring(0, substring.lastIndexOf("."));
                            quanUrl = quanUrl.replace(substring, "THUMB/T" + name + ".JPG");
                            quanUrl = quanUrl.replaceFirst("/da/","/xiao/");*/
                        }
                        collectionInfo.setPictureUrl(quanUrl);
                    }
                    if(StringUtils.isNotBlank(url)){
                        pictureUrlList.add(config.getRootUrl() + url);
                    }
                }
                collectionInfo.setPictureList(pictureUrlList);
            }
        }
        return collectionInfoListBySearch;
    }

    @Override
    public List<SelectDto> getCollectionTypeList() {
        return collectionInfoMapper.getCollectionTypeList();
    }

    @Override
    public List<SelectDto> getCollectionYearList() {
        return collectionInfoMapper.getCollectionYearList();
    }

    @Override
    public List<ISysDictDto> getDictData(String type) {
        return collectionInfoMapper.getDictData(type);
    }

    @Override
    public List<AreaDto> getCityDataByParent(String parent) {
        return collectionInfoMapper.getCityDataByParent(parent);
    }

    @Override
    public CollectionInfoDto getCollectionDetail(String id) {
        CollectionInfoDto collectionDetail = collectionInfoMapper.getCollectionDetail(id);
        if (StringUtils.isNotBlank(collectionDetail.getPictureUrl())) {
            String[] split = collectionDetail.getPictureUrl().split(",");
            List<String> pics = Arrays.asList(split);
            List<Map<String, String>> pictureList = museumShowMapper.getPictureUrl(pics);
            List<String> pictureUrlList = new ArrayList<>();
            for (Map<String, String> picture: pictureList){
                String isMain = (String)picture.get("isMain");
                String url = (String)picture.get("url");
                if(StringUtils.isNotBlank(isMain) && "1".equals(isMain)){
                    //将主图的大图换成小图
                    String quanUrl = config.getRootUrl() + url;
                    if(quanUrl.contains("/da/")){
                            /*String substring = quanUrl.substring(quanUrl.lastIndexOf("/") + 1, quanUrl.length());
                            String kuozhanming = substring.substring(substring.lastIndexOf(".")+1,substring.length());
                            String name = substring.substring(0, substring.lastIndexOf("."));
                            quanUrl = quanUrl.replace(substring, "THUMB/T" + name + ".JPG");
                            quanUrl = quanUrl.replaceFirst("/da/","/xiao/");*/
                    }
                    collectionDetail.setPictureUrl(quanUrl);
                }
                if(StringUtils.isNotBlank(url)){
                    pictureUrlList.add(config.getRootUrl() + url);
                }
            }
            collectionDetail.setPictureList(pictureUrlList);
        }
        return collectionInfoMapper.getCollectionDetail(id);
    }
}
