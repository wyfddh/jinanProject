package com.tj720.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.dao.EsaleWorkBenchDataMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.EsaleLocalTask;
import com.tj720.model.EsaleRemoteTask;
import com.tj720.model.EsaleSysUserMenu;
import com.tj720.model.common.wf.SysUser;
import com.tj720.service.EsaleWorkBenchService;
import com.tj720.utils.HttpPostGet;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsaleWorkBenchDataServiceImpl implements EsaleWorkBenchService {
    @Autowired
    private EsaleWorkBenchDataMapper esaleWorkBenchDataMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private EsaleSysMenuServiceImpl esaleSysMenuService;

    @Autowired
    private Config config;

    //待办
    private String getHandleTask = "/cepApi/CeX2ProcessRestController/getHandleTask";
    //已办
    private String getEndHandleTaskPagination = "/cepApi/CeX2ProcessRestController/getEndHandleTaskPagination";

    @Override
    public JsonResult globalStatistics() {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "登录异常");
            }
            Map<String, Integer> statistics = new HashMap<>();
            int activityCount = esaleWorkBenchDataMapper.countActivity();
            statistics.put("activityCount", activityCount);
            int collectionCount = esaleWorkBenchDataMapper.countCollection();
            statistics.put("collectionCount", collectionCount);
            int dataCount = esaleWorkBenchDataMapper.countData();
            statistics.put("dataCount", dataCount);
            return new JsonResult(1, statistics);
        } catch (Exception e) {
            return new JsonResult(0, "数据错误");
        }
    }

    @Override
    public JsonResult getTasks() {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "登录异常");
            }
            Map<String, List<HashMap<String,Object>>> result = new HashMap<>();
            SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
            HashMap<String,Object> condition = new HashMap<>();
            condition.put("currentUserId",userId);
            //待办
            List<EsaleRemoteTask> notDo = new ArrayList<>();
//            result.put("notDo", notDo);
            getRemoteTask(sysUser, getHandleTask, notDo);
//            List<EsaleLocalTask> localTasks = esaleWorkBenchDataMapper.selectNotDo();
            List<HashMap<String,Object>> undoTask = esaleWorkBenchDataMapper.getUndoTask(condition);
            for (EsaleRemoteTask esaleRemoteTask : notDo) {
                HashMap<String,Object> tempTask = new HashMap<String,Object>();
                tempTask.put("taskType","1");
                tempTask.put("actionType","0");//外部任务类型统一设置为0
                tempTask.put("actionName",esaleRemoteTask.getTASKNAME());
                tempTask.put("actionTime",esaleRemoteTask.getARRIVETIME());
                tempTask.put("processInstId",esaleRemoteTask.getPROCESSKEY());
                tempTask.put("partyId",esaleRemoteTask.getBUSINESSKEY());
                tempTask.put("url",esaleRemoteTask.getMODELANDVIEW());
                undoTask.add(tempTask);
            }
            result.put("undoTask", undoTask);

            //已办
            List<EsaleRemoteTask> already = new ArrayList<>();
            getRemoteTask(sysUser, getEndHandleTaskPagination, already);
            List<HashMap<String,Object>> doneTask = esaleWorkBenchDataMapper.getDoneTask(condition);
            for (EsaleRemoteTask esaleRemoteTask : already) {
                HashMap<String,Object> tempTask = new HashMap<String,Object>();
                tempTask.put("taskType","1");
                tempTask.put("actionType","0");//外部任务类型统一设置为0
                tempTask.put("actionName",esaleRemoteTask.getTASKNAME());
                tempTask.put("actionTime",esaleRemoteTask.getARRIVETIME());
                tempTask.put("processInstId",esaleRemoteTask.getPROCESSKEY());
                tempTask.put("partyId",esaleRemoteTask.getBUSINESSKEY());
                tempTask.put("url",esaleRemoteTask.getMODELANDVIEW());
                doneTask.add(tempTask);
            }

            result.put("doneTask", doneTask);
            //办结
            List<EsaleRemoteTask> end = new ArrayList<>();
            getRemoteTask(sysUser, getEndHandleTaskPagination, end);
//            localTasks = esaleWorkBenchDataMapper.selectEnd();
            List<HashMap<String,Object>> finishTask = esaleWorkBenchDataMapper.getFinishTask(condition);
            for (EsaleRemoteTask esaleRemoteTask : end) {
                HashMap<String,Object> tempTask = new HashMap<String,Object>();
                tempTask.put("taskType","1");
                tempTask.put("actionType","0");//外部任务类型统一设置为0
                tempTask.put("actionName",esaleRemoteTask.getTASKNAME());
                tempTask.put("actionTime",esaleRemoteTask.getARRIVETIME());
                tempTask.put("processInstId",esaleRemoteTask.getPROCESSKEY());
                tempTask.put("partyId",esaleRemoteTask.getBUSINESSKEY());
                tempTask.put("url",esaleRemoteTask.getMODELANDVIEW());
                finishTask.add(tempTask);
            }
            result.put("finishTask", finishTask);
            return new JsonResult(1, result);
        } catch (Exception e) {
            return new JsonResult(0, "数据错误");
        }
    }

    public void getRemoteTask(SysUser user, String url, List<EsaleRemoteTask> paramList) {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        StringBuilder sb = new StringBuilder(config.getSyncJurisdictionPath());
        sb.append(url);
        if (null != user && null != user.getUserName() && !user.getUserName().equals("")) {
            sb.append("?USERCODE=" + user.getUserName());
        }
        int times = 1;
        List<EsaleRemoteTask> taskList = new ArrayList<>();
        // 失败重试 2 次，最多执行 3 次
        while (times < 4) {
            try {
                String res = HttpPostGet.get(sb.toString(), null, header, 300000);
                res = "[" + res + "]";
                taskList = JSONArray.parseArray(res, EsaleRemoteTask.class);
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
            times++;
        }
        for (int i = 0; i < taskList.size(); i++) {
            if (i != 0) {
                paramList.add(taskList.get(i));
            }
        }
    }

    @Override
    public JsonResult getMenu() {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "登录异常");
            }
            List<EsaleSysUserMenu> menuList = new ArrayList<>();
            menuList = esaleWorkBenchDataMapper.selectMenuByUser(userId);
            return new JsonResult(1, menuList);
        } catch (Exception e) {
            return new JsonResult(0, "数据错误");
        }
    }

    @Override
    public JsonResult saveMenu(String menuIds) {
        try {
            String userId = Tools.getUserId();
            if (StringUtils.isBlank(userId)) {
                return new JsonResult(0, "登录异常");
            }
            esaleWorkBenchDataMapper.deleteUserMenu(userId);
            List<EsaleSysUserMenu> menuList = new ArrayList<>();
            if (StringUtils.isNotBlank(userId)) {
                String[] menuId = menuIds.split(",");
                for (String s : menuId) {
                    EsaleSysUserMenu info = new EsaleSysUserMenu(IdUtils.nextId("EsaleSysUserMenu"), userId, s);
                    menuList.add(info);
                }
                esaleWorkBenchDataMapper.insertUserMenu(menuList);
            }
            return new JsonResult(1);
        } catch (Exception e) {
            return new JsonResult(0, "数据错误");
        }
    }

}
