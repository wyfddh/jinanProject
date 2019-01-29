package com.tj720.dao;

import com.tj720.model.IntroduceManager.LocalIntroduce;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LocalIntroduceMapper {

    int insert(LocalIntroduce record);

    int updateByPrimaryKeySelective(LocalIntroduce record);

    int updateByPrimaryKeyWithBLOBs(LocalIntroduce record);

    Integer countIntroduceList(Map<String, Object> map);

    List<LocalIntroduce> selectIntroduceList(Map<String, Object> map);

    int countPageRecommend();
}