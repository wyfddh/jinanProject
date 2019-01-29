package com.tj720.dao;

import com.tj720.model.AssetEsaleLabel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AssetEsaleLabelMapper {
    int deleteByPrimaryKey(String id);

    int insert(AssetEsaleLabel record);

    int insertSelective(AssetEsaleLabel record);

    AssetEsaleLabel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AssetEsaleLabel record);

    int updateByPrimaryKey(AssetEsaleLabel record);
    //查询标签
    List<AssetEsaleLabel> queryAllLabel(Map<String, Object> map);

    //查询总条数
    Integer countAllLabel();

    //查询标签资料数量
    Integer queryVideoByLid(String lid);

    void updateLableVideo(Map<String, Object> map);

    List<AssetEsaleLabel> countInfo(String id);

    List<AssetEsaleLabel> getInfoListByNameList(List<String> list);

    List<AssetEsaleLabel> getInfoListByName(String name);

    int teachInsert(@Param("list") List<AssetEsaleLabel> list);

}