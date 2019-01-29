package com.tj720.dao;

import com.tj720.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EsaleInterfaceSyncTaskMapper {

    /**
     * 插入单位信息数据
     * @param munitInfoList
     * @return
     */
    Integer insertMunitInfoList(List<EsaleInterfaceMunitInfo> munitInfoList);

    /**
     * 删除所有同步的角色信息数据
     * @return
     */
    Integer deleteAllRoleInfoBySync();

    /**
     * 插入角色信息数据
     * @param roleInfoList
     * @return
     */
    Integer insertRoleInfoList(List<EsaleInterfaceRoleInfo> roleInfoList);

    /**
     * 删除所有同步的角色权限信息
     * @return
     */
    Integer deleteAllRoleUserInfoBySync();

    /**
     * 插入角色权限信息数据
     * @param roleUserInfoList
     * @return
     */
    Integer insertRoleUserInfoList(List<EsaleInterfaceRoleUserInfo> roleUserInfoList);

    /**
     * 删除所有同步的用户信息
     * @return
     */
    Integer deleteAllUserInfoBySync();

    /**
     * 插入用户信息数据
     * @param roleUserInfoList
     * @return
     */
    Integer insertUserInfoList(List<EsaleInterfaceUserInfo> roleUserInfoList);

    /**
     * 删除所有同步的部门信息
     * @return
     */
    Integer deleteAllDepartInfoBySync();

    /**
     * 插入部门信息数据
     * @param departInfoList
     * @return
     */
    Integer insertDepartInfoList(List<EsaleInterfaceDepartInfo> departInfoList);

    /**
     * 根据id查询主馆信息
     * @param id
     * @return
     */
    EsaleInterfaceMunitInfo selectMunitInfoById(String id);

    /**
     * 更新博物馆信息
     * @param munitInfo
     * @return
     */
    Integer updateMunitInfo(EsaleInterfaceMunitInfo munitInfo);

    /**
     * 删除同步数据中已删除的主馆
     * @param munitIdLsit
     * @return
     */
    Integer deleteMunitInfoNotInIds(List<String> munitIdLsit);

    /**
     * 删除所有同步的藏品信息
     * @return
     */
    Integer deleteAllCollectBySync(List<String> collectIdList);

    /**
     * 插入藏品信息列表
     * @param collectList
     * @return
     */
    Integer insertCollectList(List<EsaleInterfaceCollect> collectList);

    /**
     * 根据id查询藏品信息
     * @param id
     * @return
     */
    EsaleInterfaceCollect selectCollectById(String id);

    /**
     * 根据id更新藏品
     * @param collect
     * @return
     */
    Integer updateCollectById(EsaleInterfaceCollect collect);
}
