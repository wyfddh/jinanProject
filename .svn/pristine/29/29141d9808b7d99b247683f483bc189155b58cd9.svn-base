package com.tj720.service.impl;

import com.tj720.controller.springbeans.Config;
import com.tj720.dao.ICollectionInfoMapper;
import com.tj720.dao.ICreativeProductMapper;
import com.tj720.dao.IMuseumShowMapper;
import com.tj720.dto.CreativeProductDto;
import com.tj720.service.ICollectionInfoService;
import com.tj720.service.ICreativeProductService;
import com.tj720.utils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ICreativeProductServiceImpl implements ICreativeProductService {

    @Autowired
    private ICreativeProductMapper creativeProductMapper;
    @Autowired
    private IMuseumShowMapper museumShowMapper;
    @Autowired
    private Config config;

    @Override
    public List<CreativeProductDto> getRecommendCreativeProductList(Integer size) {
        List<CreativeProductDto> creativeProductList = creativeProductMapper.getRecommendCreativeProductList(size);
        for(CreativeProductDto product: creativeProductList){
            if(StringUtils.isNotBlank(product.getPictureUrl())){
                String[] split = product.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                product.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return creativeProductList;
    }

    @Override
    public List<CreativeProductDto> getCreativeProductList(Page page) {
        int countCreativeProductList = creativeProductMapper.countCreativeProductList();
        page.setAllRow(countCreativeProductList);
        List<CreativeProductDto> creativeProductList = creativeProductMapper.getCreativeProductList(page.getStart(), page.getSize());
        for(CreativeProductDto product: creativeProductList){
            if(StringUtils.isNotBlank(product.getPictureUrl())){
                String[] split = product.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                product.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return creativeProductList;
    }

    @Override
    public CreativeProductDto getCreativeProduct(String id) {
        CreativeProductDto creativeProduct = creativeProductMapper.getCreativeProduct(id);
        if(creativeProduct != null && StringUtils.isNotBlank(creativeProduct.getPictureUrl())){
            String[] split = creativeProduct.getPictureUrl().split(",");
            List<String> pics = Arrays.asList(split);
            String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
            creativeProduct.setPictureUrl(config.getRootUrl() + mainPictureUrl);
        }
        return creativeProduct;
    }

    @Override
    public List<CreativeProductDto> getRelevantCreativeProduct(String currentId, String currentMuseumId, String type, Integer size) {
        List<CreativeProductDto> relevantCreativeProduct = creativeProductMapper.getRelevantCreativeProduct(currentId, currentMuseumId, type, size);
        for(CreativeProductDto product: relevantCreativeProduct){
            if(StringUtils.isNotBlank(product.getPictureUrl())){
                String[] split = product.getPictureUrl().split(",");
                List<String> pics = Arrays.asList(split);
                String mainPictureUrl = museumShowMapper.getMainPictureUrl(pics);
                product.setPictureUrl(config.getRootUrl() + mainPictureUrl);
            }
        }
        return relevantCreativeProduct;
    }
}
