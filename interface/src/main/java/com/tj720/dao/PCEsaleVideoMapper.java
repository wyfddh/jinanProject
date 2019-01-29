package com.tj720.dao;

import com.tj720.dto.EsaleVideoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PCEsaleVideoMapper {

    List<EsaleVideoDto> selectVideoList();

    int updatePlayNum(@Param("videoId") String videoId);


}