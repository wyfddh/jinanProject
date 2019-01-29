package com.tj720.dao;

import com.tj720.model.PostVideo;

import java.util.List;
import java.util.Map;

public interface PostVideosMapper {
    /*int deleteByPrimaryKey(String id);

    int insert(PostVideo record);

    int insertSelective(PostVideo record);

   PostVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostVideo record);

    int updateByPrimaryKeyWithBLOBs(PostVideo record);

    int updateByPrimaryKey(PostVideo record);*/

    Integer countPostVideoByLabelId(Map<String,Object> map);

    List<PostVideo> queryPostVideoByLabelId(Map<String, Object> map);
}