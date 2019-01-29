package com.tj720.dao;

import com.tj720.model.EsaleCollectionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EsaleCollectionInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleCollectionInfo record);

    EsaleCollectionInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleCollectionInfo record);


    /**
     * 查询某个用户的收藏品数量
     */
    Integer countCollectionByUid(String uid);

    /**
     * 查询某个用户的收藏
     */
    List<EsaleCollectionInfo> queryCollectionsByUid(Map<String, Object> map);

    /**
     * 查询某个用户的收藏的条数
     */
    Integer collectionNumByUid(Map<String, Object> map);

    Integer countCollectionInfoList(Map<String, Object> map);

    List<EsaleCollectionInfo> getCollectionInfoList(Map<String, Object> map);

    int selectHotCount();

    List<Map<String,Object>> getCollectionListByType(Map<String, Object> map);

}