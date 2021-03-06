package com.tj720.dao;

import com.tj720.model.EsaleLabel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface EsaleLabelMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsaleLabel record);

    int insertSelective(EsaleLabel record);

    EsaleLabel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsaleLabel record);

    int updateByPrimaryKey(EsaleLabel record);
    //查询标签
    List<EsaleLabel> queryAllLabel(Map<String,Object> map);

    //查询总条数
    Integer countAllLabel();

    //查询标签资料数量
    Integer queryVideoByLid(String lid);

    void updateLable(Map<String, Object> map);

    List<EsaleLabel> countInfo(String id);
}