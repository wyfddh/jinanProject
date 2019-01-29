package com.tj720.dao;

import com.tj720.model.EsaleLocalTask;
import com.tj720.model.EsaleSysUserMenu;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface EsaleWorkBenchDataMapper {
    int countActivity();

    int countCollection();

    int countData();

    List<EsaleLocalTask> selectNotDo();

    List<EsaleLocalTask> selectAlready();

    List<EsaleLocalTask> selectEnd();

    List<EsaleSysUserMenu> selectMenuByUser(@Param("userId") String userId);

    int deleteUserMenu(@Param("userId") String userId);

    int insertUserMenu(List<EsaleSysUserMenu> menus);

    /**
     * 查询待办
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getUndoTask(HashMap<String,Object> condition);

    /**
     * 查询已办
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getDoneTask(HashMap<String,Object> condition);

    /**
     * 查询已办结
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getFinishTask(HashMap<String,Object> condition);

}