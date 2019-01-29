package com.tj720.dao;

import com.tj720.dto.PCEsaleDigitalShowDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PCEsaleDigitalShowMapper {

    /**
     * 查询数字展厅列表
     * @param museumId：博物馆ID
     * @return
     */
    List<PCEsaleDigitalShowDto> getEsaleDigitalShowList(@Param("museumId") String museumId);


    PCEsaleDigitalShowDto selectById(String id);

    void updateById(PCEsaleDigitalShowDto dto);
}