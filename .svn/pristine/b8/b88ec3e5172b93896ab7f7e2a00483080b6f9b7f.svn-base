package com.tj720.service.impl;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PCEsaleCollectionInfoMapper;
import com.tj720.dto.PCEsaleCollectionInfoDto;
import com.tj720.dto.picDto;
import com.tj720.service.PCEsaleConllectionService;
import com.tj720.service.PCEsalePubUserService;
import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PCEsaleCollectionServiceImpl implements PCEsaleConllectionService {
    @Autowired
    private PCEsaleCollectionInfoMapper pcCollectionInfoMapper;

    @Autowired
    private PCEsalePubUserService esalePubUserService;

    @Override
    public JsonResult getCollectionsByUid(String uid, Integer size, Integer currentPage) {
        try{
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("uid",uid);

            //符合条件总数
            Integer count = pcCollectionInfoMapper.collectionNumByUid(map);
            System.out.println("count : "+count);
            page.setAllRow(count);
            map.put("start",page.getStart());
            map.put("end",page.getSize());
            List<PCEsaleCollectionInfoDto> list = pcCollectionInfoMapper.queryCollectionsByUid(map);
/*            for (PCCollectionInfoWithBLOBsDto p:list
                 ) {
                p.setPicType("3D");
            }*/
            return new JsonResult(1,list,page);
        }catch (Exception e){
            return new JsonResult(0,"系统异常");
        }
    }

    /**
     * 取消收藏
     * @param uid
     * @param
     * @return
     */
    @Override
    public JsonResult cancelCollection(String uid, String cid) {
        try{
            Map<String,Object> map = new HashMap();
            map.put("uid",uid);
            map.put("cid",cid);


            pcCollectionInfoMapper.cancelCollection(map);

            /**
             * 该藏品数量减一
             */
            PCEsaleCollectionInfoDto collectionInfoDto = pcCollectionInfoMapper.selectByPrimaryKey(cid);
            if(collectionInfoDto!=null&&collectionInfoDto.getCollectNum()>=1){
                collectionInfoDto.setCollectNum(collectionInfoDto.getCollectNum()-1);
                pcCollectionInfoMapper.updateByPrimaryKeySelective(collectionInfoDto);
            }

            return new JsonResult(1);
        }catch (Exception e){
            return new JsonResult(0,"系统异常");
        }
    }

    /**
     * 进行收藏
     * @param uid
     * @param cid
     * @return
     */
    @Override
    public JsonResult userCollection(String uid, String cid) {
        try{
            Map<String,Object> map = new HashMap();
            map.put("uid",uid);
            map.put("cid",cid);
            map.put("type","1");
            map.put("dataState","1");
            map.put("id", IdUtils.nextId("id"));
            map.put("createDate",new Date());
            map.put("createBy",uid);
            pcCollectionInfoMapper.userCollection(map);

            /**
             * 该藏品数量加一
             */
            PCEsaleCollectionInfoDto collectionInfoDto = pcCollectionInfoMapper.selectByPrimaryKey(cid);
            System.out.println("getCollectNum ======== "+collectionInfoDto.getCollectNum());
           /* if(collectionInfoDto!=null){
                collectionInfoDto.setCollectNum(collectionInfoDto.getCollectNum()+1);
                System.out.println("getCollectNum ======== "+collectionInfoDto.getCollectNum());
                pcCollectionInfoMapper.updateByPrimaryKeySelective(collectionInfoDto);
            }*/

            return new JsonResult(1);
        }catch (Exception e){
            return new JsonResult(0,"系统异常");
        }
    }

    @Override
    public JsonResult getRecommend(String museumId) {
        try {
            List<PCEsaleCollectionInfoDto> list = pcCollectionInfoMapper.getRecommendList(museumId);
            return new JsonResult(1,list);
        } catch (Exception e) {
            return new JsonResult(1,null,"111116");
        }
    }

    @Override
    public JsonResult getCollectionInfoList(String museumId, String key, String collectionType, String collectionYear, Integer currentPage, Integer size) {

        try {
            Page page = new Page();
            page.setSize(size);
            page.setCurrentPage(currentPage);
            Map<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(museumId)) {
                map.put("museumId", museumId);
            }
            if (StringUtils.isNotBlank(key)) {
                map.put("key", key);
            }
            if (StringUtils.isNotBlank(collectionType)) {
                map.put("collectionTypeCode", collectionType);
            }
            if (StringUtils.isNotBlank(collectionYear)) {
                map.put("collectionYearCode", collectionYear);
            }

            //符合条件总数
            Integer count = pcCollectionInfoMapper.countCollectionInfoList(map);
            System.out.println("count : " + count);
            page.setAllRow(count);
            map.put("start", page.getStart());
            map.put("end", page.getSize());

            List<PCEsaleCollectionInfoDto> list = pcCollectionInfoMapper.selectCollectionInfoList(map);
            for (PCEsaleCollectionInfoDto info : list) {
                if (StringUtils.isNotBlank(info.getPictureids())) {
                    List<String> picList = Arrays.asList(info.getPictureids().split(","));
                    info.setPicList(picList);
                }
            }
            return new JsonResult(1,list,page);
        } catch (Exception e) {
            return new JsonResult(1,null,"000020");
        }
    }

    @Override
    public JsonResult getCollectionDetail(String id,String userId) {
        if(null == id && id.equals("")){
            return new JsonResult(0,null,"000020");
        }
        PCEsaleCollectionInfoDto info = pcCollectionInfoMapper.selectCollectionInfoById(id);
        if(info== null){
            return new JsonResult(0,null,"000020");
        }
        info.setClickNum(info.getClickNum()+1);
        pcCollectionInfoMapper.updateByPrimaryKeySelective(info);
        Map<String,Object> map = new HashMap<>();
        map.put("collect_id",id);
        map.put("create_by",userId);
        map.put("create_date",new Date());
        map.put("update_by",userId);
        map.put("update_date",new Date());
        pcCollectionInfoMapper.insertCollectionView(map);
        if (StringUtils.isNotBlank(info.getPictureids())) {
            List<String> picList = new ArrayList<>();
            JSONArray jsonArray=JSONArray.fromObject(info.getPictureids());
            List<picDto> picDtoList = (List<picDto>)JSONArray.toCollection(jsonArray,picDto.class);
            for(picDto pic :picDtoList){
                String url = pic.getPicUrl();
                picList.add(url);
            }
            info.setPicList(picList);
        }
        if(null != userId && !userId.equals("")){
            if(selectUserIsCollection(userId,id)){
                info.setIsCollect("1");
            }else{
                info.setIsCollect("0");
            }
            esalePubUserService.addInfomation("1",userId,info);
        }

        return new JsonResult(1,info);
    }

    public boolean selectUserIsCollection(String userId,String collectionId){
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("collectionId",collectionId);
        List<String> ids =pcCollectionInfoMapper.selectUserIsCollection(map);
        if(null !=ids && ids.size()==1){
            return true;
        }else{
            return false;
        }
    }
}
