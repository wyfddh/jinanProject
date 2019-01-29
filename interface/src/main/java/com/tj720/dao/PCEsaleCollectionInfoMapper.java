package com.tj720.dao;

import com.tj720.dto.PCEsaleCollectionInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PCEsaleCollectionInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(PCEsaleCollectionInfoDto record);

    PCEsaleCollectionInfoDto selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PCEsaleCollectionInfoDto record);



    /**
     * 查询某个用户的收藏
     */
    List<PCEsaleCollectionInfoDto> queryCollectionsByUid(Map<String, Object> map);

    /**
     * 查询某个用户的收藏的条数
     */
    Integer collectionNumByUid(Map<String, Object> map);

    /**
     * 查询热门推荐
     */
    List<PCEsaleCollectionInfoDto> getRecommendList(@Param("museumId") String museumId);


    Integer countCollectionInfoList(Map<String, Object> map);

    /**
     * 查询藏品列表
     */
    List<PCEsaleCollectionInfoDto> selectCollectionInfoList(Map<String, Object> map);

    /**
     * 查询藏品详情
     */
    PCEsaleCollectionInfoDto selectCollectionInfoById(@Param("id") String id);


    void cancelCollection(Map<String, Object> map);

    /**
     * 用户收藏藏品
     * @param map
     */
    void userCollection(Map<String, Object> map);

    /**
     * 查询用户是否收藏此藏品
     * @param map
     * @return
     */
    List<String> selectUserIsCollection(Map map);

    int insertCollectionView(Map map);
}