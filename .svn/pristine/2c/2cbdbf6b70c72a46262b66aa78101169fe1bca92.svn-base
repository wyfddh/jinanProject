package com.tj720.dao;

import com.tj720.model.AssetEsaleLableVideo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetEsaleLableVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(AssetEsaleLableVideo record);

    int insertSelective(AssetEsaleLableVideo record);

    AssetEsaleLableVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AssetEsaleLableVideo record);

    int updateByPrimaryKey(AssetEsaleLableVideo record);

    int teachInsert(@Param("list") List<AssetEsaleLableVideo> record);

    int deleteByVideoId(@Param("videoId") String videoId);
}