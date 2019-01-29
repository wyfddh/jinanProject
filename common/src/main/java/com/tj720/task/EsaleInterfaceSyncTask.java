package com.tj720.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.EsaleInterfaceSyncTaskMapper;
import com.tj720.model.*;
import com.tj720.utils.HttpPostGet;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;

/**
 * 同步外部接口数据定时任务
 * @author 杜昶
 */
@Component
public class EsaleInterfaceSyncTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(EsaleInterfaceSyncTask.class);

    @Autowired
    private Config config;

    @Resource(name = "transactionManager")
    private HibernateTransactionManager transactionManager;

    @Autowired
    private EsaleInterfaceSyncTaskMapper esaleInterfaceSyncTaskMapper;

    private String getAllMunitInfoUrl = "/cepApi/CeX2OrgRestController/getAllMunitInfo";

    private String getAllRoleInfoUrl = "/cepApi/CeX2RoleRestController/getAllRoleInfo";

    private String getAllRoleUserInfoUrl = "/cepApi/CeX2RoleRestController/getAllRoleUserInfo";

    private String getAllUserInfoUrl = "/cepApi/CeX2UserRestController/getAllUserInfo";

    private String getAllDepartInfoUrl = "/cepApi/CeX2OrgRestController/getAllDepartInfo";

    private String getAllCollectInfoUrl = "/icmsApi/icmsRestController/getCulListByShareApply";

    private String logTemplete = "同步【%s】结束，同步结果【%s】，处理用时【%d】ms，同步数量【%d】条";

    /**
     * 同步所有单位列表数据
     */
    public void getAllMunitInfo() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getSyncJurisdictionPath());
        sb.append(getAllMunitInfoUrl);
        LOGGER.info("开始同步【单位信息】");
        int times = 1;
        Integer count = 0;
        Date start = new Date();
        TransactionStatus status = null;
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                // 开启手动事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                status = transactionManager.getTransaction(def); // 获得事务状态
                String res = HttpPostGet.get(sb.toString(), null, header);
                JSONObject jsonObject = JSON.parseObject(res);
                List<EsaleInterfaceMunitInfo> munitInfoList = JSONArray.parseArray(jsonObject.getString("data"), EsaleInterfaceMunitInfo.class);
                List<EsaleInterfaceMunitInfo> insertList = new ArrayList<EsaleInterfaceMunitInfo>();
                List<String> munitIdLsit = new ArrayList<String>(munitInfoList.size());
                munitInfoList.remove(0);
                for (EsaleInterfaceMunitInfo munitInfo : munitInfoList) {
                    EsaleInterfaceMunitInfo info = esaleInterfaceSyncTaskMapper.selectMunitInfoById(munitInfo.getId());
                    if (null == info) {
                        insertList.add(munitInfo);
                    } else {
                        if (!info.getMunitName().equals(munitInfo.getMunitName())) {
                            Integer row = esaleInterfaceSyncTaskMapper.updateMunitInfo(munitInfo);
                            if (null == row || row < 1) {
                                LOGGER.error("数据更新异常，本库数据{}，同步数据{}",info, munitInfo);
                                throw new RuntimeException();
                            }
                        }
                    }
                    munitIdLsit.add(munitInfo.getId());
                }
                esaleInterfaceSyncTaskMapper.deleteMunitInfoNotInIds(munitIdLsit);
                if (CollectionUtils.isNotEmpty(insertList)) {
                    count = esaleInterfaceSyncTaskMapper.insertMunitInfoList(insertList);
                }
                int size = insertList.size();
                if (count == size) {
                    transactionManager.commit(status);
                    break;
                } else {
                    throw new RuntimeException("数据存入数量异常，总计 " + size + " 条数据，本次插入 " + count + " 条数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("同步【单位信息】数据失败----当前同步次数：" + times, e);
                transactionManager.rollback(status);
            }
            times ++;
        }
        Date end = new Date();
        LOGGER.info(String.format(logTemplete, "单位信息", times < 4 ? "成功" : "失败", start.getTime() - end.getTime(), count));
    }

    /**
     * 同步所有角色信息
     */
    public void getAllRoleInfo() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getSyncJurisdictionPath());
        sb.append(getAllRoleInfoUrl);
        LOGGER.info("开始同步【角色信息】");
        int times = 1;
        Integer count = 0;
        Date start = new Date();
        TransactionStatus status = null;
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                // 开启手动事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                status = transactionManager.getTransaction(def); // 获得事务状态
                String res = HttpPostGet.get(sb.toString(), null, header);
                JSONObject jsonObject = JSON.parseObject(res);
                List<EsaleInterfaceRoleInfo> roleInfoList = JSONArray.parseArray(jsonObject.getString("data"), EsaleInterfaceRoleInfo.class);
                roleInfoList.remove(0);
                esaleInterfaceSyncTaskMapper.deleteAllRoleInfoBySync();
                if (CollectionUtils.isNotEmpty(roleInfoList)) {
                    count = esaleInterfaceSyncTaskMapper.insertRoleInfoList(roleInfoList);
                }
                int size = roleInfoList.size();
                if (count == size) {
                    transactionManager.commit(status);
                    break;
                } else {
                    throw new RuntimeException("数据存入数量异常，总计 " + size + " 条数据，本次插入 " + count + " 条数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("同步【角色信息】数据失败----当前同步次数：" + times, e);
                transactionManager.rollback(status);
            }
            times ++;
        }
        Date end = new Date();
        LOGGER.info(String.format(logTemplete, "角色信息", times < 4 ? "成功" : "失败", start.getTime() - end.getTime(), count));
    }

    /**
     * 同步所有角色权限信息
     */
    public void getAllRoleUserInfo() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getSyncJurisdictionPath());
        sb.append(getAllRoleUserInfoUrl);
        LOGGER.info("开始同步【角色权限】");
        int times = 1;
        Integer count = 0;
        Date start = new Date();
        TransactionStatus status = null;
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                // 开启手动事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                status = transactionManager.getTransaction(def); // 获得事务状态
                String res = HttpPostGet.get(sb.toString(), null, header);
                JSONObject jsonObject = JSON.parseObject(res);
                List<EsaleInterfaceRoleUserInfo> roleUserInfoList = JSONArray.parseArray(jsonObject.getString("data"), EsaleInterfaceRoleUserInfo.class);
                roleUserInfoList.remove(0);
                esaleInterfaceSyncTaskMapper.deleteAllRoleUserInfoBySync();
                if (CollectionUtils.isNotEmpty(roleUserInfoList)) {
                    count = esaleInterfaceSyncTaskMapper.insertRoleUserInfoList(roleUserInfoList);
                }
                int size = roleUserInfoList.size();
                if (count == size) {
                    transactionManager.commit(status);
                    break;
                } else {
                    throw new RuntimeException("数据存入数量异常，总计 " + size + " 条数据，本次插入 " + count + " 条数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("同步【角色权限】数据失败----当前同步次数：" + times, e);
                transactionManager.rollback(status);
            }
            times ++;
        }
        Date end = new Date();
        LOGGER.info(String.format(logTemplete, "角色权限", times < 4 ? "成功" : "失败", start.getTime() - end.getTime(), count));
    }

    /**
     * 同步所有用户信息
     */
    public void getAllUserInfo() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getSyncJurisdictionPath());
        sb.append(getAllUserInfoUrl);
        LOGGER.info("开始同步【用户信息】");
        int times = 1;
        Integer count = 0;
        Date start = new Date();
        TransactionStatus status = null;
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                // 开启手动事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                status = transactionManager.getTransaction(def); // 获得事务状态
                String res = HttpPostGet.get(sb.toString(), null, header);
                JSONObject jsonObject = JSON.parseObject(res);
                List<EsaleInterfaceUserInfo> userInfoList = JSONArray.parseArray(jsonObject.getString("data"), EsaleInterfaceUserInfo.class);
                userInfoList.remove(0);
                esaleInterfaceSyncTaskMapper.deleteAllUserInfoBySync();
                if (CollectionUtils.isNotEmpty(userInfoList)) {
                    count = esaleInterfaceSyncTaskMapper.insertUserInfoList(userInfoList);
                }
                int size = userInfoList.size();
                if (count == size) {
                    transactionManager.commit(status);
                    break;
                } else {
                    throw new RuntimeException("数据存入数量异常，总计 " + size + " 条数据，本次插入 " + count + " 条数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("同步【用户信息】数据失败----当前同步次数：" + times, e);
                transactionManager.rollback(status);
            }
            times ++;
        }
        Date end = new Date();
        LOGGER.info(String.format(logTemplete, "用户信息", times < 4 ? "成功" : "失败", start.getTime() - end.getTime(), count));
    }

    /**
     * 同步所有部门信息
     */
    public void getAllDepartInfo() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getSyncJurisdictionPath());
        sb.append(getAllDepartInfoUrl);
        LOGGER.info("开始同步【部门信息】");
        int times = 1;
        Integer count = 0;
        Date start = new Date();
        TransactionStatus status = null;
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                // 开启手动事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                status = transactionManager.getTransaction(def); // 获得事务状态
                String res = HttpPostGet.get(sb.toString(), null, header);
                JSONObject jsonObject = JSON.parseObject(res);
                List<EsaleInterfaceDepartInfo> departInfoList = JSONArray.parseArray(jsonObject.getString("data"), EsaleInterfaceDepartInfo.class);
                departInfoList.remove(0);
                esaleInterfaceSyncTaskMapper.deleteAllDepartInfoBySync();
                if (CollectionUtils.isNotEmpty(departInfoList)) {
                    count = esaleInterfaceSyncTaskMapper.insertDepartInfoList(departInfoList);
                }
                int size = departInfoList.size();
                if (count == size) {
                    transactionManager.commit(status);
                    break;
                } else {
                    throw new RuntimeException("数据存入数量异常，总计 " + size + " 条数据，本次插入 " + count + " 条数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("同步【部门信息】数据失败----当前同步次数：" + times, e);
                transactionManager.rollback(status);
            }
            times ++;
        }
        Date end = new Date();
        LOGGER.info(String.format(logTemplete, "部门信息", times < 4 ? "成功" : "失败", start.getTime() - end.getTime(), count));
    }

    /**
     * 同步所有藏品信息
     */
    public void getCulListByShareApply() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getInterfaceCollectPath());
        sb.append(getAllCollectInfoUrl);
        LOGGER.info("开始同步【藏品信息】");
        int times = 1;
        Integer count = 0;
        Date start = new Date();
        TransactionStatus status = null;
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                // 开启手动事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); // 事物隔离级别，开启新事务，这样会比较安全些。
                status = transactionManager.getTransaction(def); // 获得事务状态
                String res = HttpPostGet.get(sb.toString(), null, header, 300000);
                JSONObject jsonObject = JSON.parseObject(res);
                List<EsaleInterfaceCollect> collectList = JSONArray.parseArray(jsonObject.getString("data"), EsaleInterfaceCollect.class);
                List<EsaleInterfaceCollect> insertList = new ArrayList<EsaleInterfaceCollect>();
                List<String> collectIdList = new ArrayList<String>(collectList.size());
                for (EsaleInterfaceCollect collect : collectList) {
                    EsaleInterfaceCollect col = esaleInterfaceSyncTaskMapper.selectCollectById(collect.getId());
                    if (null == col) {
                        insertList.add(collect);
                    } else if (!collect.getDataVer().equals(col.getDataVer())) {
                        Integer row = esaleInterfaceSyncTaskMapper.updateCollectById(collect);
                        if (null == row || row < 1) {
                            LOGGER.error("数据更新异常，本库数据{}，同步数据{}",col, collect);
                            throw new RuntimeException();
                        }
                    }
                    collectIdList.add(collect.getId());
                }
                esaleInterfaceSyncTaskMapper.deleteAllCollectBySync(collectIdList);
                if (CollectionUtils.isNotEmpty(insertList)) {
                    count = esaleInterfaceSyncTaskMapper.insertCollectList(insertList);
                }
                int size = insertList.size();
                if (count == size) {
                    transactionManager.commit(status);
                    break;
                } else {
                    throw new RuntimeException("数据存入数量异常，总计 " + size + " 条数据，本次插入 " + count + " 条数据");
                }
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("同步【藏品信息】数据失败----当前同步次数：" + times, e);
                transactionManager.rollback(status);
            }
            times ++;
        }
        Date end = new Date();
        LOGGER.info(String.format(logTemplete, "藏品信息", times < 4 ? "成功" : "失败", start.getTime() - end.getTime(), count));
    }
}
