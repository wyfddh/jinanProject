package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.dto.PCEsalePubUserDto;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface PCEsalePubUserService {

    /**
     * 登陆
     * @param
     * @return
     */
   List<PCEsalePubUserDto> getListByPhone(String userName);

    PCEsalePubUserDto queryUserById02(String id);

    /**
     * login
     * @param model
     * @param user
     * @param request
     * @param response
     */
    void login(PCEsalePubUserDto model, PCEsalePubUserDto user, HttpServletRequest request, HttpServletResponse response);

    void updateUser(PCEsalePubUserDto user);

    PCEsalePubUserDto getListByUserPhone(String phone);

    /**
     * 修改个人资料
     * @param model
     */
    JsonResult updateUserInfo(PCEsalePubUserDto model);

    /**
     * 更换安全验证
     * @param model
     * @param
     * @return
     */
    JsonResult updateUserPhone(PCEsalePubUserDto model);

    /**
     * 用户注册功能
     */
    JsonResult userRegiste(PCEsalePubUserDto model);

    /**
     * 忘记密码
     * @param model
     * @param
     * @return
     */
    JsonResult forgetPassword(PCEsalePubUserDto model);

    /**
     * 查看用户详情
     * @param uid
     * @return
     */
    JsonResult queryUserById(String uid);

    JsonResult queryUserByIds(String uid);

    String userExist(String phone,String id);

    /**
     * 上传图片(无裁剪)
     * @param
     */
    JsonResult saveAttachment(CommonsMultipartFile file, String tableName, String tableid, String source);

    /**
     * 浏览足迹
     * @param uid
     * @param size
     * @param currentPage
     * @return
     */
    JsonResult queryFootprintBrowsing(String uid, Integer size, Integer currentPage);

    //移动端报名时生成用户
    boolean mobileUserSave(PCEsalePubUserDto model);

    //添加浏览足迹
    void addInfomation(String type,String uid,Object obj);

  /**
   * 清空浏览足迹
   * @param uid
   * @return
   */
  JsonResult delFootprintBrowsing(String uid);

  String isCanSignActivity(String id);
}
