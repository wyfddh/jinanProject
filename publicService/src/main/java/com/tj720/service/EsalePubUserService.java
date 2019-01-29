package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.EsalePubUser;
import com.tj720.model.EsalePubUserVo;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface EsalePubUserService {
    /**
     * 查询公众用户列表
     * @param key
     * @param phone
     * @param isYoung
     * @param orderBy
     * @param size
     * @param currentPage
     * @return
     */
    JSONObject getEsalePubUserList(String key, String phone, String isYoung, String orderBy, Integer size, Integer currentPage);

    /**
     * 查看用户个人信息
     * @param id 用户id
     * @return
     */
    EsalePubUser queryUserById(String id);

    /**
     * 统计信息
     * @param id 用户id
     * @return
     */
    JSONObject countInfomation(String id);

    /**
     * 查询某个用户的活动记录
     */
    JSONObject getActivityCountById(Integer size, Integer currentPage, String uid);

    /**
     * 编辑用户
     */
    JsonResult updateUser(EsalePubUser esalePubUser);


    /**
     * 冻结用户
     * @param uid
     * @return
     */
    JsonResult freezeUser(String uid);

    /**
     * 移除黑名单
     * @param uid
     * @return
     */
    JsonResult moveOutUser(String uid);

    /**
     * 黑名单列表查询
     * @param key
     * @param phone
     * @param isYoung
     * @param orderBy
     * @param size
     * @param currentPage
     * @return
     */
    JSONObject getEsaleUserBlacklist(String key, String phone, String isYoung, String orderBy, Integer size, Integer currentPage);


    /**
     * 批量移除
     * @param uids
     * @return
     */
    JsonResult moveOutUsers(String[] uids);

    /**
     * 批量冻结
     * @param uid
     * @return
     */
    JsonResult freezeUsers(String[] uid);

    /**
     * 登陆
     * @param
     * @return
     */
    List<EsalePubUser> getListByName(String userName);

    /**
     * login
     * @param model
     * @param user
     * @param request
     * @param response
     */
    void login(EsalePubUser model, EsalePubUser user, HttpServletRequest request, HttpServletResponse response);

    /**
     * 公众用户数据导出
     * @param vo
     * @return
     */
    Workbook exportUserData(EsalePubUserVo vo);

    /**
     * 公众用户下载模板
     * @return
     */
    Workbook generateUserTemplate();

    /**
     * 黑名单数据导出
     * @param vo
     * @return
     */
    Workbook exportBlacklistData(EsalePubUserVo vo);

    /**
     * 黑名单下载模板
     * @return
     */
    Workbook generateBlicklistTemplate();
}
