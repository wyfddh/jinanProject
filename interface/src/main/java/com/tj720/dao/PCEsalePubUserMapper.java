package com.tj720.dao;

import com.tj720.dto.PCEsalePubUserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PCEsalePubUserMapper {
    int deleteByPrimaryKey(String id);

    String isCanSignActivity(String id);
    int insert(PCEsalePubUserDto record);

    int insertSelective(PCEsalePubUserDto record);

    PCEsalePubUserDto selectByPrimaryKey(@Param("id") String id);

    int updateByPrimaryKeySelective(PCEsalePubUserDto record);

    int updateByPrimaryKeyWithBLOBs(PCEsalePubUserDto record);

    int updateByPrimaryKey(PCEsalePubUserDto record);


    /**
     * 通过用户名查询
     * @param
     * @return
     */
   List<PCEsalePubUserDto> selectUserByPhones(String phone);

    PCEsalePubUserDto selectUserByPhone(String phone);

}