package com.tj720.dao;

import com.tj720.dto.InformationManagerDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IInformationManageMapper {

    /**
     * 获取首页资讯
     * @param size
     * @return
     */
    public List<InformationManagerDto> getInformationList(@Param("size") Integer size);
}