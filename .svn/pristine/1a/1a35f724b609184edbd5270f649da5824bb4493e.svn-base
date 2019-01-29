package com.tj720.dao;

import com.tj720.model.EsalePubUser;
import com.tj720.model.EsalePubUserVo;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface EsalePubUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(EsalePubUser record);

    int insertSelective(EsalePubUser record);

    EsalePubUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EsalePubUser record);

    int updateByPrimaryKeyWithBLOBs(EsalePubUser record);

    int updateByPrimaryKey(EsalePubUser record);

    /**
     * 公众用户管理
     */
    List<EsalePubUserVo> getEsalePubUserList(Map<String, Object> map);
    /**
     * 查询总条数
     */
    int countEsaleUserList(Map<String, Object> map);


    /**
     * 查询条数
     * @param map
     * @return
     */
    List<Integer> countEsaleUserBlacklist(Map<String, Object> map);

    /**
     * 查询黑名单列表
     * @param map
     * @return
     */
    List<EsalePubUserVo> getEsaleUserBlacklist(Map<String, Object> map);


    /**
     * 通过用户名查询
     * @param
     * @return
     */
    List<EsalePubUser> selectUserByName(String userName);

    /**
     * 数据导出的查询 不需要分页
     * @param
     * @return
     */
    EsalePubUserVo getEsalePubUsers(String id);

    /**
     * 数据导出的黑名单查询
     * @param id
     * @return
     */
    EsalePubUserVo getEsaleUserBlacks(String id);
}